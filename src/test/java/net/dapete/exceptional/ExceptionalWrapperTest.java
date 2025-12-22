package net.dapete.exceptional;

import net.dapete.exceptional.function.AllExceptionalFunctionalInterfacesArgumentsProvider;
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
        final var expectedReturnType = switch (clazz.getSimpleName()) {
            case "ExceptionalRunnable" -> Runnable.class;
            default -> {
                final var expectedClassName = "java.util.function." + clazz.getSimpleName().replace("Exceptional", "");
                yield Class.forName(expectedClassName);
            }
        };
        assertEquals(expectedReturnType, method.getReturnType());

        // call works and returns a non-null object
        final var mockedParameter = mock(clazz, RETURNS_DEEP_STUBS);
        final var result = method.invoke(ExceptionalWrapper.class, mockedParameter);
        assertNotNull(result);

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