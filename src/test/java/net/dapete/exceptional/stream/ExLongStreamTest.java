package net.dapete.exceptional.stream;

import net.dapete.exceptional.ExException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ExLongStreamTest {

    @Test
    void map_exception() {
        final var thrown = assertThrows(ExException.class,
                () -> ExLongStream.of(1, 2, 3)
                        .exceptionalMap(t -> {
                            throw new IOException("Test");
                        })
                        .boxed()
                        .toList());

        assertInstanceOf(IOException.class, thrown.getCause());
        assertEquals("Test", thrown.getCause().getMessage());
    }

    @Test
    void map_noExceptionBeforeEndOperation() {
        @SuppressWarnings("unused") final var ignore = ExLongStream.of(1, 2, 3)
                .exceptionalMap(t -> {
                    throw new IOException("Test");
                });
    }

}
