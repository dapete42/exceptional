package net.dapete.exceptional.stream;

import net.dapete.exceptional.ExException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ExStreamTest {

    @Test
    void map_exception() {
        final var thrown = assertThrows(ExException.class,
                () -> ExStream.of(1, 2, 3)
                        .map(IOException.class, t -> {
                            throw new IOException("Test");
                        })
                        .toList());

        assertInstanceOf(IOException.class, thrown.getCause());
        assertEquals("Test", thrown.getCause().getMessage());
    }

    @Test
    void map_noExceptionBeforeEndOperation() {
        @SuppressWarnings("unused") final var ignore = ExStream.of(1, 2, 3)
                .map(IOException.class, t -> {
                    throw new IOException("Test");
                });
    }

}
