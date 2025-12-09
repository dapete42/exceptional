package net.dapete.exceptional;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionalWrapperTest {

    @Test
    void wrapConsumer() {
        final var wrappedConsumer = ExceptionalWrapper.wrapConsumer(t -> {
            throw new Exception("Test");
        });

        final var thrown = assertThrowsExactly(WrappedExceptionalException.class,
                () -> wrappedConsumer.accept(null));

        assertInstanceOf(Exception.class, thrown.getCause());
        assertEquals("Test", thrown.getCause().getMessage());
    }

    @Test
    void wrapFunction() {
        final var wrappedFunction = ExceptionalWrapper.wrapFunction(t -> {
            throw new Exception("Test");
        });

        final var thrown = assertThrowsExactly(WrappedExceptionalException.class,
                () -> wrappedFunction.apply(null));

        assertInstanceOf(Exception.class, thrown.getCause());
        assertEquals("Test", thrown.getCause().getMessage());
    }

    @Test
    void test() {
        final var thrown = assertThrows(Exception.class,
                () -> Stream.of(1, 2, 3)
                        .map(ExceptionalWrapper.wrapFunction(t -> {
                            throw new Exception("Test");
                        }))
                        .toList());

        assertInstanceOf(Exception.class, thrown.getCause());
        assertEquals("Test", thrown.getCause().getMessage());
    }

}