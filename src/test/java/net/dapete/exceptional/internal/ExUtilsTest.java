package net.dapete.exceptional.internal;

import net.dapete.exceptional.ExException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ExUtilsTest {

    @ParameterizedTest
    @CsvSource(textBlock = """
            0, 0, 0, 1
            0, 0, 1, 2
            0, 0, 2, 2
            0, 1, 0, 2
            0, 1, 1, 2
            0, 1, 2, 3
            1, 0, 0, 2
            1, 0, 1, 2
            1, 0, 2, 3
            1, 1, 0, 2
            1, 1, 1, 1
            1, 1, 2, 2
            2, 0, 0, 2
            2, 0, 1, 3
            2, 0, 2, 2
            2, 1, 0, 3
            2, 1, 1, 2
            2, 1, 2, 2
            """)
    void setOf3(String value1, String value2, String value3, int expectedSize) {
        final var set = ExUtils.setOf3(value1, value2, value3);
        assertEquals(expectedSize, set.size());
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
