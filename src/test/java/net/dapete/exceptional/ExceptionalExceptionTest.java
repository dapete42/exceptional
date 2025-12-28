package net.dapete.exceptional;

import net.dapete.exceptional.stream.ExceptionalIntStream;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

class ExceptionalExceptionTest {

    // TODO this is example code, not a test
    @Test
    void unwrap() {

        /*
         * How to catch the actual exceptions that were thrown, pattern 1a:
         * Have the ExceptionalException rethrow its cause and catch that.
         */

        try {
            ExceptionalIntStream.of(1, 2, 3)
                    .exceptionalForEach(i -> {
                        throw new IOException("Test");
                    });
        } catch (ExceptionalException e) {
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
            ExceptionalIntStream.of(1, 2, 3)
                    .exceptionalForEach(i -> {
                        throw new IOException("Test");
                    });
        } catch (ExceptionalException e) {
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
            ExceptionalIntStream.of(1, 2, 3)
                    .exceptionalForEach(i -> {
                        throw new IOException("Test");
                    });
        } catch (ExceptionalException e) {
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
            ExceptionalIntStream.of(1, 2, 3)
                    .exceptionalForEach(i -> {
                        throw new IOException("Test");
                    });
        } catch (ExceptionalException e) {
            try {
                e.unwrap(NoSuchAlgorithmException.class, NoSuchFieldException.class, IOException.class);
            } catch (IOException | NoSuchAlgorithmException | NoSuchFieldException ee) {
                System.out.println("An exception was thrown");
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
                            .exceptionalForEach(i -> {
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
            ExceptionalException.unwrap(IOException.class, () ->
                    ExceptionalIntStream.of(1, 2, 3)
                            .exceptionalForEach(i -> {
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
            @SuppressWarnings("unused") final var ignore = ExceptionalException.unwrap(() ->
                    ExceptionalIntStream.of(1, 2, 3)
                            .exceptionalMap(i -> {
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
            @SuppressWarnings("unused") final var ignore = ExceptionalException.unwrap(IOException.class, () ->
                    ExceptionalIntStream.of(1, 2, 3)
                            .exceptionalMap(i -> {
                                throw new IOException("Test");
                            })
                            .toArray()
            );
        } catch (IOException e) {
            System.out.println("IOException was thrown");
        }

    }

}
