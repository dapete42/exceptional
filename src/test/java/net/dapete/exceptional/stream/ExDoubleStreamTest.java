package net.dapete.exceptional.stream;

import net.dapete.exceptional.ExException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ExDoubleStreamTest {

    @Test
    void exceptionalMap_exception() {
        final var thrown = assertThrows(ExException.class,
                () -> ExDoubleStream.of(1, 2, 3)
                        .exMap(t -> {
                            throw new IOException("Test");
                        })
                        .boxed()
                        .toList());

        assertInstanceOf(IOException.class, thrown.getCause());
        assertEquals("Test", thrown.getCause().getMessage());
    }

    @Test
    void exceptionalMap_noExceptionBeforeEndOperation() {
        @SuppressWarnings("unused") final var ignore = ExDoubleStream.of(1, 2, 3)
                .exMap(t -> {
                    throw new IOException("Test");
                });
    }

}
