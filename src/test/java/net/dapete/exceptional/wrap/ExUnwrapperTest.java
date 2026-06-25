package net.dapete.exceptional.wrap;

import net.dapete.exceptional.stream.ExIntStream;
import net.dapete.exceptional.stream.ExStream;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

public class ExUnwrapperTest {

    @Test
    void unwrap_Runnable_nested() throws IOException {
        ExUnwrapper.of(IOException.class)
                .unwrap(() -> {
                    try {
                        ExUnwrapper.of(TimeoutException.class)
                                .unwrap(() -> ExStream.of(1)
                                        .forEach(IOException.class, x -> {
                                        })
                                );
                    } catch (TimeoutException e) {
                        throw new RuntimeException(e);
                    }
                });

        assertFalse(ExUnwrapper.isUnwrapActive());
    }

    @Test
    void unwrap_Runnable_nestedThrowingException() {
        assertThrows(IOException.class, () ->
                ExUnwrapper.of(IOException.class)
                        .unwrap(() -> {
                            try {
                                ExUnwrapper.of(TimeoutException.class)
                                        .unwrap(() -> ExStream.of(1)
                                                .forEach(IOException.class, x -> {
                                                    throw new IOException();
                                                })
                                        );
                            } catch (TimeoutException e) {
                                throw new RuntimeException(e);
                            }
                        })
        );

        assertFalse(ExUnwrapper.isUnwrapActive());
    }

    @Test
    void unwrap_Supplier_nested() throws IOException {
        @SuppressWarnings("unused") final var ignore = ExUnwrapper.of(IOException.class)
                .unwrap(() -> {
                    try {
                        return ExUnwrapper.of(TimeoutException.class)
                                .unwrap(() -> ExStream.of(1)
                                        .map(IOException.class, x -> x)
                                        .findFirst()
                                );
                    } catch (TimeoutException e) {
                        throw new RuntimeException(e);
                    }
                });

        assertFalse(ExUnwrapper.isUnwrapActive());
    }

    @Test
    void unwrap_Supplier_nestedThrowingException() {
        assertThrows(IOException.class, () -> {
            @SuppressWarnings("unused") final var ignore = ExUnwrapper.of(IOException.class)
                    .unwrap(() -> {
                        try {
                            return ExUnwrapper.of(TimeoutException.class)
                                    .unwrap(() -> ExStream.of(1)
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

        assertFalse(ExUnwrapper.isUnwrapActive());
    }

    @Test
    void verifyUnwrapActive_notActive() {
        final var thrown = assertThrows(IllegalArgumentException.class, () ->
                ExUnwrapper.verifyUnwrapActive(IOException.class)
        );

        assertEquals("Exception java.io.IOException is not allowed here, must be included in ExUnwrapper.of(...) invocation", thrown.getMessage());
    }

    @Test
    void verifyUnwrapActive_active() throws IOException {
        ExUnwrapper.of(IOException.class)
                .unwrap(() -> ExUnwrapper.verifyUnwrapActive(IOException.class));
    }

    @Test
    void verifyUnwrapActive_activeForOtherClass() {
        final var thrown = assertThrows(IllegalArgumentException.class, () ->
                ExUnwrapper.of(IOException.class)
                        .unwrap(() -> ExUnwrapper.verifyUnwrapActive(MissingResourceException.class))
        );

        assertEquals("Exception java.util.MissingResourceException is not allowed here, must be included in ExUnwrapper.of(...) invocation", thrown.getMessage());
    }

    @Test
    void verifyUnwrapActive_activeForSuperclass() throws IOException {
        ExUnwrapper.of(IOException.class)
                .unwrap(() -> ExUnwrapper.verifyUnwrapActive(FileNotFoundException.class));
    }

    @Test
    void verifyUnwrapActive_activeForSubclass() {
        final var thrown = assertThrows(IllegalArgumentException.class, () ->
                ExUnwrapper.of(FileNotFoundException.class)
                        .unwrap(() -> ExUnwrapper.verifyUnwrapActive(IOException.class))
        );

        assertEquals("Exception java.io.IOException is not allowed here, must be included in ExUnwrapper.of(...) invocation", thrown.getMessage());
    }

    // TODO this is example code, not a test
    @Test
    void unwrap_class_runnable() {

        /*
         * How to catch the actual exceptions that were thrown, pattern 2b:
         * Like pattern 2a, but unwrap(...) throws only a specific type of exception.
         */

        try {
            ExUnwrapper.of(IOException.class)
                    .unwrap(() -> ExIntStream.of(1, 2, 3)
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
    void unwrap_class_supplier() {

        // the same as in unwrap_class_runnable, just with a return value

        try {
            @SuppressWarnings("unused") final var ignore = ExUnwrapper.of(IOException.class)
                    .unwrap(() -> ExIntStream.of(1, 2, 3)
                            .map(IOException.class, i -> {
                                throw new IOException("Test");
                            })
                            .toArray()
                    );
        } catch (IOException e) {
            System.out.println("IOException was thrown");
        }

    }

    @Test
    void verifyExceptionAllowed_notActive() {
        ExUnwrapper.verifyExceptionAllowed(IOException.class);
    }

    @Test
    void verifyExceptionAllowed_active() throws IOException {
        ExUnwrapper.of(IOException.class)
                .unwrap(() -> ExUnwrapper.verifyExceptionAllowed(IOException.class));
    }

    @Test
    void verifyExceptionAllowed_activeForOtherClass() {
        final var thrown = assertThrows(IllegalArgumentException.class, () ->
                ExUnwrapper.of(IOException.class)
                        .unwrap(() -> ExUnwrapper.verifyExceptionAllowed(MissingResourceException.class))
        );

        assertEquals("Exception java.util.MissingResourceException is not allowed here, must be included in ExUnwrapper.of(...) invocation", thrown.getMessage());
    }

    @Test
    void verifyExceptionAllowed_activeForSuperclass() throws IOException {
        ExUnwrapper.of(IOException.class)
                .unwrap(() -> ExUnwrapper.verifyExceptionAllowed(FileNotFoundException.class));
    }

    @Test
    void verifyExceptionAllowed_activeForSubclass() {
        final var thrown = assertThrows(IllegalArgumentException.class, () ->
                ExUnwrapper.of(FileNotFoundException.class)
                        .unwrap(() ->
                                ExUnwrapper.verifyExceptionAllowed(IOException.class)
                        )
        );

        assertEquals("Exception java.io.IOException is not allowed here, must be included in ExUnwrapper.of(...) invocation", thrown.getMessage());
    }

}
