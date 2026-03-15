package net.dapete.exceptional;

import net.dapete.exceptional.function.ExRunnable;
import net.dapete.exceptional.function.ExSupplier;
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

}
