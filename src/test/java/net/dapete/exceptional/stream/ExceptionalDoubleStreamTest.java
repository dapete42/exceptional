package net.dapete.exceptional.stream;

import net.dapete.exceptional.ExceptionalException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionalDoubleStreamTest {

    @Test
    void map_exception() {
        final var thrown = assertThrows(ExceptionalException.class,
                () -> ExceptionalDoubleStream.of(1, 2, 3)
                        .wrapExceptions(IOException.class)
                        .map(t -> {
                            throw new IOException("Test");
                        })
                        .boxed()
                        .toList());

        assertInstanceOf(IOException.class, thrown.getCause());
        assertEquals("Test", thrown.getCause().getMessage());
    }

    @Test
    void map_noExceptionBeforeEndOperation() {
        ExceptionalDoubleStream.of(1, 2, 3)
                .wrapExceptions(IOException.class)
                .map(t -> {
                    throw new IOException("Test");
                });
    }

}
