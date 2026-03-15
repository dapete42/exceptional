package net.dapete.exceptional;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

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

}
