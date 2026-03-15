package net.dapete.exceptional;

import net.dapete.exceptional.stream.ExIntStream;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serial;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExExceptionTest {

    @Test
    void constructor() {

        final var cause = new Exception();

        final var result = new ExException(cause);

        assertEquals(cause, result.getCause());

    }

    @Test
    void constructor_invalidType() {

        final var cause = new RuntimeException();

        assertThrows(IllegalArgumentException.class, () -> new ExException(cause));

    }

    @Test
    void getCause_invalidType() throws IllegalAccessException, NoSuchFieldException {

        final var exception = new ExException(new Exception());
        final var cause = new Throwable() {
            @Serial
            private static final long serialVersionUID = 1L;
        };
        // setting the cause to the wrong type is only possible with reflection
        final var causeField = Throwable.class.getDeclaredField("cause");
        causeField.setAccessible(true);
        causeField.set(exception, cause);

        final var result = exception.getCause();

        // Throwable has been wrapped in an Exception
        assertEquals(cause, result.getCause());

    }

    // TODO this is example code, not a test
    @Test
    void unwrap() {

        /*
         * How to catch the actual exceptions that were thrown, pattern 1a:
         * Have the ExException rethrow its cause and catch that.
         */

        try {
            ExIntStream.of(1, 2, 3)
                    .exForEach(i -> {
                        throw new IOException("Test");
                    });
        } catch (ExException e) {
            try {
                e.unwrap();
            } catch (IOException ee) {
                System.out.println("IOException was thrown");
            } catch (Exception ee) {
                System.out.println("Exception was thrown");
            }
        }

    }

    // TODO this is example code, not a test
    @Test
    void unwrap_class() {

        /*
         * How to catch the actual exceptions that were thrown, pattern 1b:
         * Like pattern 1a, but unwrap(...) throws only a specific type of exception.
         */

        try {
            ExIntStream.of(1, 2, 3)
                    .exForEach(i -> {
                        throw new IOException("Test");
                    });
        } catch (ExException e) {
            try {
                e.unwrap(IOException.class);
            } catch (IOException ee) {
                System.out.println("IOException was thrown");
            }
        }

    }

    // TODO this is example code, not a test
    @Test
    void unwrap_class2() {

        /*
         * How to catch the actual exceptions that were thrown, pattern 1b:
         * Like pattern 1a, but unwrap(...) throws only a specific type of exception.
         */

        try {
            ExIntStream.of(1, 2, 3)
                    .exForEach(i -> {
                        throw new IOException("Test");
                    });
        } catch (ExException e) {
            try {
                e.unwrap(NoSuchAlgorithmException.class, IOException.class);
            } catch (IOException | NoSuchAlgorithmException ee) {
                System.out.println("An exception was thrown");
            }
        }

    }

    // TODO this is example code, not a test
    @Test
    void unwrap_class3() {

        /*
         * How to catch the actual exceptions that were thrown, pattern 1b:
         * Like pattern 1a, but unwrap(...) throws only a specific type of exception.
         */

        try {
            ExIntStream.of(1, 2, 3)
                    .exForEach(i -> {
                        throw new IOException("Test");
                    });
        } catch (ExException e) {
            try {
                e.unwrap(NoSuchAlgorithmException.class, NoSuchFieldException.class, IOException.class);
            } catch (IOException | NoSuchAlgorithmException | NoSuchFieldException ee) {
                System.out.println("An exception was thrown");
            }
        }

    }

    @Test
    void throwIfInstance_dontThrow() throws FileNotFoundException {

        final IOException exception = new IOException();

        ExUtils.throwIfInstance(FileNotFoundException.class, exception);

    }

    @Test
    void throwIfInstance_throw() {

        final IOException exception = new IOException();

        assertThrows(IOException.class, () -> ExUtils.throwIfInstance(IOException.class, exception));

    }

}
