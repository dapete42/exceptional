package net.dapete.exceptional.stream;

import net.dapete.exceptional.ExceptionalException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionalStreamTest {

    @Test
    void map_exception() {
        final var thrown = assertThrows(ExceptionalException.class,
                () -> ExceptionalStream.of(1, 2, 3)
                        .exceptionalMap(t -> {
                            throw new IOException("Test");
                        })
                        .toList());

        assertInstanceOf(IOException.class, thrown.getCause());
        assertEquals("Test", thrown.getCause().getMessage());
    }

    @Test
    void map_noExceptionBeforeEndOperation() {
        @SuppressWarnings("unused") final var ignore = ExceptionalStream.of(1, 2, 3)
                .exceptionalMap(t -> {
                    throw new IOException("Test");
                });
    }

}
