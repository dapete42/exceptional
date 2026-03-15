package net.dapete.exceptional;

import net.dapete.exceptional.function.ExRunnable;
import net.dapete.exceptional.function.ExSupplier;
import net.dapete.exceptional.stream.ExIntStream;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class ExUtilsTest {

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

    @Test
    void toRuntimeException_unchanged() {

        final RuntimeException runtimeException = new RuntimeException();

        final var result = ExUtils.toRuntimeException(runtimeException);

        assertSame(runtimeException, result);

    }

    @Test
    void toRuntimeException_wrapped() {

        final IOException exception = new IOException();

        final var result = ExUtils.toRuntimeException(exception);

        assertInstanceOf(ExException.class, result);
        assertSame(exception, result.getCause());

    }

    @Test
    void wrap_Supplier() {

        final ExSupplier<String, IOException> supplier = () -> "test";

        final var result = ExUtils.wrap(supplier);

        assertEquals("test", result);

    }

    @Test
    void wrap_Supplier_exception() {

        final ExSupplier<String, IOException> supplier = () -> {
            throw new IOException("test");
        };

        final var thrown = assertThrows(ExException.class, () -> ExUtils.wrap(supplier));

        assertEquals("test", thrown.getCause().getMessage());

    }

    @Test
    void wrap_Runnable() {

        final var testString = new AtomicReference<String>();
        final ExRunnable<IOException> runnable = () -> testString.set("test");

        ExUtils.wrap(runnable);

        assertEquals("test", testString.get());

    }

    @Test
    void wrap_Runnable_exception() {

        final ExRunnable<IOException> runnable = () -> {
            throw new IOException("test");
        };

        final var thrown = assertThrows(ExException.class, () -> ExUtils.wrap(runnable));

        assertEquals("test", thrown.getCause().getMessage());

    }


    // TODO this is example code, not a test
    @Test
    void unwrap_runnable() {

        /*
         * How to catch the actual exceptions that were thrown, pattern 2a:
         * Implement unwrap(...) and catch the cause of the ExException.
         */

        try {
            ExUtils.unwrap(() ->
                    ExIntStream.of(1, 2, 3)
                            .exForEach(i -> {
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
            ExUtils.unwrap(IOException.class, () ->
                    ExIntStream.of(1, 2, 3)
                            .exForEach(i -> {
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
            @SuppressWarnings("unused") final var ignore = ExUtils.unwrap(() ->
                    ExIntStream.of(1, 2, 3)
                            .exMap(i -> {
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
            @SuppressWarnings("unused") final var ignore = ExUtils.unwrap(IOException.class, () ->
                    ExIntStream.of(1, 2, 3)
                            .exMap(i -> {
                                throw new IOException("Test");
                            })
                            .toArray()
            );
        } catch (IOException e) {
            System.out.println("IOException was thrown");
        }

    }

}
