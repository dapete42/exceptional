package net.dapete.exceptional.wrap;

import net.dapete.exceptional.stream.ExIntStream;
import net.dapete.exceptional.stream.ExStream;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        assertFalse(ExUnwrap.isUnwrapActive());
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

        assertFalse(ExUnwrap.isUnwrapActive());
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

        assertFalse(ExUnwrap.isUnwrapActive());
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

        assertFalse(ExUnwrap.isUnwrapActive());
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


}
