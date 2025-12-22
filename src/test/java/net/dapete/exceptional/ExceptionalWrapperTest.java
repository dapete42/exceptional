package net.dapete.exceptional;

import net.dapete.exceptional.function.AllExceptionalFunctionalInterfacesArgumentsProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionalWrapperTest {

    @ParameterizedTest
    @ArgumentsSource(AllExceptionalFunctionalInterfacesArgumentsProvider.class)
    void checkIsWrapImplementedForAllExceptionalFunctionalInterfaces(Class<?> clazz) throws NoSuchMethodException {
        assertNotNull(ExceptionalWrapper.class.getMethod("wrap", clazz));
    }

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

}