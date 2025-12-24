package net.dapete.exceptional;

import net.dapete.exceptional.stream.ExceptionalIntStream;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ExceptionalExceptionTest {

    // TODO this is example code, not a test
    @Test
    void throwCause() {

        /*
         * How to catch the actual exceptions that were thrown, pattern 1:
         * Have the ExceptionalException rethrow its cause and catch that.
         */

        try {
            ExceptionalIntStream.of(1, 2, 3)
                    .wrapExceptions(IOException.class)
                    .forEach(i -> {
                        throw new IOException("Test");
                    });
        } catch (ExceptionalException e) {
            try {
                e.throwCause();
            } catch (IOException ee) {
                System.out.printf("IOException was thrown: %s%n", ee);
            } catch (Exception ee) {
                System.out.printf("Exception was thrown: %s%n", ee);
            }
        }

    }

    // TODO this is example code, not a test
    @Test
    void unwrap_runnable() {

        /*
         * How to catch the actual exceptions that were thrown, pattern 2a:
         * Implement unwrap(...) and catch the cause of the ExceptionalException.
         */

        try {
            ExceptionalException.unwrap(() ->
                    ExceptionalIntStream.of(1, 2, 3)
                            .wrapExceptions(IOException.class)
                            .forEach(i -> {
                                throw new IOException("Test");
                            })
            );
        } catch (IOException e) {
            System.out.printf("IOException was thrown: %s%n", e);
        } catch (Exception e) {
            System.out.printf("Exception was thrown: %s%n", e);
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
            ExceptionalException.unwrap(IOException.class, () ->
                    ExceptionalIntStream.of(1, 2, 3)
                            .wrapExceptions(IOException.class)
                            .forEach(i -> {
                                throw new IOException("Test");
                            })
            );
        } catch (IOException e) {
            System.out.printf("Exception was thrown: %s%n", e);
        }

    }

    // TODO this is example code, not a test
    @Test
    void unwrap_supplier() {

        // the same as in unwrap_runnable, just with a return value

        try {
            var ignore = ExceptionalException.unwrap(() ->
                    ExceptionalIntStream.of(1, 2, 3)
                            .wrapExceptions(IOException.class)
                            .map(i -> {
                                throw new IOException("Test");
                            })
                            .toArray()
            );
        } catch (IOException e) {
            System.out.printf("IOException was thrown: %s%n", e);
        } catch (Exception e) {
            System.out.printf("Exception was thrown: %s%n", e);
        }

    }

    // TODO this is example code, not a test
    @Test
    void unwrap_class_supplier() {

        // the same as in unwrap_class_runnable, just with a return value

        try {
            var ignore = ExceptionalException.unwrap(IOException.class, () ->
                    ExceptionalIntStream.of(1, 2, 3)
                            .wrapExceptions(IOException.class)
                            .map(i -> {
                                throw new IOException("Test");
                            })
                            .toArray()
            );
        } catch (IOException e) {
            System.out.printf("Exception was thrown: %s%n", e);
        }

    }

}
