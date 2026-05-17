package net.dapete.exceptional;

import net.dapete.exceptional.function.ExRunnable;
import net.dapete.exceptional.function.ExSupplier;
import net.dapete.exceptional.stream.ExIntStream;
import net.dapete.exceptional.stream.ExStream;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class ExWrapTest {

    @Test
    void wrap_Supplier() {

        final ExSupplier<String, IOException> supplier = () -> "test";

        final var result = ExWrap.wrap(supplier);

        assertEquals("test", result);

    }

    @Test
    void wrap_Supplier_exception() {

        final ExSupplier<String, IOException> supplier = () -> {
            throw new IOException("test");
        };

        final var thrown = assertThrows(ExException.class, () -> ExWrap.wrap(supplier));

        assertEquals("test", thrown.getCause().getMessage());

    }

    @Test
    void wrap_Runnable() {

        final var testString = new AtomicReference<String>();
        final ExRunnable<IOException> runnable = () -> testString.set("test");

        ExWrap.wrap(runnable);

        assertEquals("test", testString.get());

    }

    @Test
    void wrap_Runnable_exception() {

        final ExRunnable<IOException> runnable = () -> {
            throw new IOException("test");
        };

        final var thrown = assertThrows(ExException.class, () -> ExWrap.wrap(runnable));

        assertEquals("test", thrown.getCause().getMessage());

    }

    @Test
    void unwrap_Runnable_nested() throws IOException {
        ExWrap.unwrap(IOException.class, () -> {
            try {
                ExWrap.unwrap(TimeoutException.class, () ->
                        ExStream.of(1)
                                .forEach(IOException.class, x -> {
                                })
                );
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }
        });

        assertFalse(ExWrap.isUnwrapActive());
    }

    @Test
    void unwrap_Runnable_nestedThrowingException() {
        assertThrows(IOException.class, () ->
                ExWrap.unwrap(IOException.class, () -> {
                    try {
                        ExWrap.unwrap(TimeoutException.class, () ->
                                ExStream.of(1)
                                        .forEach(IOException.class, x -> {
                                            throw new IOException();
                                        })
                        );
                    } catch (TimeoutException e) {
                        throw new RuntimeException(e);
                    }
                })
        );

        assertFalse(ExWrap.isUnwrapActive());
    }

    @Test
    void unwrap_Supplier_nested() throws IOException {
        @SuppressWarnings("unused") final var ignore = ExWrap.unwrap(IOException.class, () -> {
            try {
                return ExWrap.unwrap(TimeoutException.class, () ->
                        ExStream.of(1)
                                .map(IOException.class, x -> x)
                                .findFirst()
                );
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }
        });

        assertFalse(ExWrap.isUnwrapActive());
    }

    @Test
    void unwrap_Supplier_nestedThrowingException() {
        assertThrows(IOException.class, () -> {
            @SuppressWarnings("unused") final var ignore = ExWrap.unwrap(IOException.class, () -> {
                try {
                    return ExWrap.unwrap(TimeoutException.class, () ->
                            ExStream.of(1)
                                    .map(IOException.class, x -> {
                                        throw new IOException();
                                    })
                                    .findFirst()
                    );
                } catch (TimeoutException e) {
                    throw new RuntimeException(e);
                }
            });
        });

        assertFalse(ExWrap.isUnwrapActive());
    }

    @Test
    void verifyUnwrapActive_notActive() {
        final var thrown = assertThrows(IllegalArgumentException.class, () ->
                ExWrap.verifyUnwrapActive(IOException.class)
        );

        assertEquals("Exception java.io.IOException is not allowed here, must be included in ExWrap.unwrap(...) invocation", thrown.getMessage());
    }

    @Test
    void verifyUnwrapActive_active() throws IOException {
        ExWrap.unwrap(IOException.class, () ->
                ExWrap.verifyUnwrapActive(IOException.class)
        );
    }

    @Test
    void verifyUnwrapActive_activeForOtherClass() {
        final var thrown = assertThrows(IllegalArgumentException.class, () ->
                ExWrap.unwrap(IOException.class, () ->
                        ExWrap.verifyUnwrapActive(MissingResourceException.class)
                )
        );

        assertEquals("Exception java.util.MissingResourceException is not allowed here, must be included in ExWrap.unwrap(...) invocation", thrown.getMessage());
    }

    @Test
    void verifyUnwrapActive_activeForSuperclass() throws IOException {
        ExWrap.unwrap(IOException.class, () ->
                ExWrap.verifyUnwrapActive(FileNotFoundException.class)
        );
    }

    @Test
    void verifyUnwrapActive_activeForSubclass() {
        final var thrown = assertThrows(IllegalArgumentException.class, () ->
                ExWrap.unwrap(FileNotFoundException.class, () ->
                        ExWrap.verifyUnwrapActive(IOException.class)
                )
        );

        assertEquals("Exception java.io.IOException is not allowed here, must be included in ExWrap.unwrap(...) invocation", thrown.getMessage());
    }

    // TODO this is example code, not a test
    @Test
    void unwrap_runnable() {

        /*
         * How to catch the actual exceptions that were thrown, pattern 2a:
         * Implement unwrap(...) and catch the cause of the ExException.
         */

        try {
            ExWrap.unwrap(() ->
                    ExIntStream.of(1, 2, 3)
                            .forEach(IOException.class, i -> {
                                throw new IOException("Test");
                            })
            );
        } catch (IOException e) {
            System.out.println("IOException was thrown");
        } catch (Exception e) {
            System.out.println("Exception was thrown");
        }

    }

    // TODO this is example code, not a test
    @Test
    void unwrap_class_runnable() {

        /*
         * How to catch the actual exceptions that were thrown, pattern 2b:
         * Like pattern 2a, but unwrap(...) throws only a specific type of exception.
         */

        try {
            ExWrap.unwrap(IOException.class, () ->
                    ExIntStream.of(1, 2, 3)
                            .forEach(IOException.class, i -> {
                                throw new IOException("Test");
                            })
            );
        } catch (IOException e) {
            System.out.println("IOException was thrown");
        }

    }

    // TODO this is example code, not a test
    @Test
    void unwrap_supplier() {

        // the same as in unwrap_runnable, just with a return value

        try {
            @SuppressWarnings("unused") final var ignore = ExWrap.unwrap(() ->
                    ExIntStream.of(1, 2, 3)
                            .map(IOException.class, i -> {
                                throw new IOException("Test");
                            })
                            .toArray()
            );
        } catch (IOException e) {
            System.out.println("IOException was thrown");
        } catch (Exception e) {
            System.out.println("Exception was thrown");
        }

    }

    // TODO this is example code, not a test
    @Test
    void unwrap_class_supplier() {

        // the same as in unwrap_class_runnable, just with a return value

        try {
            @SuppressWarnings("unused") final var ignore = ExWrap.unwrap(IOException.class, () ->
                    ExIntStream.of(1, 2, 3)
                            .map(IOException.class, i -> {
                                throw new IOException("Test");
                            })
                            .toArray()
            );
        } catch (IOException e) {
            System.out.println("IOException was thrown");
        }

    }

}
