package net.dapete.exceptional;

import net.dapete.exceptional.function.ExceptionalRunnable;
import net.dapete.exceptional.function.ExceptionalSupplier;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionalUtilsTest {

    @Test
    void throwIfInstance_dontThrow() throws FileNotFoundException {

        final IOException exception = new IOException();

        ExceptionalUtils.throwIfInstance(FileNotFoundException.class, exception);

    }

    @Test
    void throwIfInstance_throw() {

        final IOException exception = new IOException();

        assertThrows(IOException.class, () -> ExceptionalUtils.throwIfInstance(IOException.class, exception));

    }

    @Test
    void toRuntimeException_unchanged() {

        final RuntimeException runtimeException = new RuntimeException();

        final var result = ExceptionalUtils.toRuntimeException(runtimeException);

        assertSame(runtimeException, result);

    }

    @Test
    void toRuntimeException_wrapped() {

        final IOException exception = new IOException();

        final var result = ExceptionalUtils.toRuntimeException(exception);

        assertInstanceOf(ExceptionalException.class, result);
        assertSame(exception, result.getCause());

    }

    @Test
    void wrapAndGet() {

        final ExceptionalSupplier<String, IOException> supplier = () -> "test";

        final var result = ExceptionalUtils.wrapAndGet(supplier);

        assertEquals("test", result);

    }

    @Test
    void wrapAndGet_exception() {

        final ExceptionalSupplier<String, IOException> supplier = () -> {
            throw new IOException("test");
        };

        final var thrown = assertThrows(ExceptionalException.class, () -> ExceptionalUtils.wrapAndGet(supplier));

        assertEquals("test", thrown.getCause().getMessage());

    }

    @Test
    void wrapAndRun() {

        final var testString = new AtomicReference<String>();
        final ExceptionalRunnable<IOException> runnable = () -> testString.set("test");

        ExceptionalUtils.wrapAndRun(runnable);

        assertEquals("test", testString.get());

    }

    @Test
    void wrapAnRun_exception() {

        final ExceptionalRunnable<IOException> runnable = () -> {
            throw new IOException("test");
        };

        final var thrown = assertThrows(ExceptionalException.class, () -> ExceptionalUtils.wrapAndRun(runnable));

        assertEquals("test", thrown.getCause().getMessage());

    }

}