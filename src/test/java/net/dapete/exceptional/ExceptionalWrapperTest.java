package net.dapete.exceptional;

import net.dapete.exceptional.function.AllExceptionalFunctionalInterfacesArgumentsProvider;
import net.dapete.exceptional.function.ExceptionalConsumer;
import net.dapete.exceptional.function.ExceptionalFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

class ExceptionalWrapperTest {

    @ParameterizedTest
    @ArgumentsSource(AllExceptionalFunctionalInterfacesArgumentsProvider.class)
    void wrapForAllExceptionalFunctionalInterfaces(Class<?> clazz) throws Exception {

        // method exists (would throw a NoSuchMethodException otherwise)
        final var method = ExceptionalWrapper.class.getMethod("wrap", clazz);

        // return type matches expected type
        final var expectedReturnType = AllExceptionalFunctionalInterfacesArgumentsProvider.getExpectedReturnType(clazz);
        assertEquals(expectedReturnType, method.getReturnType());

        // call works and returns a non-null object
        final var mockedParameter = mock(clazz, RETURNS_DEEP_STUBS);
        final var result = method.invoke(ExceptionalWrapper.class, mockedParameter);
        assertNotNull(result);

    }

    @Test
    void wrap_consumer() {
        final var wrappedConsumer = ExceptionalWrapper.wrap((ExceptionalConsumer<?, ?>) t -> {
            throw new Exception("Test");
        });

        final var thrown = assertThrowsExactly(WrappedExceptionalException.class,
                () -> wrappedConsumer.accept(null));

        assertInstanceOf(Exception.class, thrown.getCause());
        assertEquals("Test", thrown.getCause().getMessage());
    }

    @Test
    void wrap_function() {
        final var wrappedFunction = ExceptionalWrapper.wrap((ExceptionalFunction<?, ?, ?>) t -> {
            throw new Exception("Test");
        });

        final var thrown = assertThrowsExactly(WrappedExceptionalException.class,
                () -> wrappedFunction.apply(null));

        assertInstanceOf(Exception.class, thrown.getCause());
        assertEquals("Test", thrown.getCause().getMessage());
    }

}