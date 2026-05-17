package net.dapete.exceptional.stream;

import net.dapete.exceptional.ExException;
import net.dapete.exceptional.ExWrap;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;

import static org.junit.jupiter.api.Assertions.*;

class ExStreamTest {

    @Test
    void unwrapScope() {
        assertThrows(FileNotFoundException.class, () ->
                ExWrap.unwrap(FileNotFoundException.class, FileSystemException.class, () ->
                        ExStream.of(1, 2, 3)
                                .map(FileSystemException.class, i -> i + 1)
                                .map(FileNotFoundException.class, i -> {
                                    throw new FileNotFoundException();
                                })
                                .findFirst()
                )
        );
    }

    @Test
    void unwrapScopeWrongException() {
        final var thrown = assertThrows(IllegalArgumentException.class, () ->
                ExWrap.unwrap(FileNotFoundException.class, () ->
                        ExStream.of(1, 2, 3)
                                .map(FileSystemException.class, i -> i + 1)
                                .map(FileNotFoundException.class, i -> {
                                    throw new FileNotFoundException();
                                })
                                .findFirst()
                )
        );

        assertEquals("Exception java.nio.file.FileSystemException is not allowed here, must be included in ExWrap.unwrap(...) invocation", thrown.getMessage());
    }

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
