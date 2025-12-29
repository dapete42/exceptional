package net.dapete.exceptional.test;

import net.dapete.exceptional.function.Wrappable;
import net.dapete.exceptional.stream.ExceptionalStream;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Answers;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.RETURNS_DEFAULTS;
import static org.mockito.Mockito.mock;

public class AllExceptionalFunctionalInterfacesTest {

    @Test
    void numberOfExceptionalFunctionalInterfaces() {
        assertEquals(47, AllExceptionalFunctionalInterfacesArgumentsProvider.getFunctionalInterfacesClasses().size());
    }

    @ParameterizedTest
    @ArgumentsSource(AllExceptionalFunctionalInterfacesArgumentsProvider.class)
    void wrap(Class<?> clazz) throws Exception {

        // implements Wrappable
        assertTrue(Arrays.asList(clazz.getInterfaces()).contains(Wrappable.class));

        // method wrap() exists (would throw a NoSuchMethodException otherwise) and has no parameters
        final var wrapMethod = clazz.getMethod("wrap");
        assertEquals(0, wrapMethod.getParameterCount());

        // return type matches expected type
        final var returnType = AllExceptionalFunctionalInterfacesArgumentsProvider.getExpectedReturnType(clazz);
        assertEquals(returnType, wrapMethod.getReturnType());

        // call the method on a mock calling real methods
        final var exceptionalInterfaceMock = mock(clazz, Answers.CALLS_REAL_METHODS);
        final var functionalInterfaceResult = wrapMethod.invoke(exceptionalInterfaceMock);
        assertInstanceOf(returnType, functionalInterfaceResult);

        // call functionalInterfaceResult.<theFunctionalInterfaceMethod> (run, get, apply, etc.)
        final var functionalInterfaceMethod = findFunctionalInterfaceMethod(returnType);
        final var mockedParameters = mockParameters(functionalInterfaceMethod);
        functionalInterfaceMethod.invoke(functionalInterfaceResult, mockedParameters);

    }

    private static @NonNull Method findFunctionalInterfaceMethod(Class<?> clazz) {
        // a functional interface can only have one non-default method
        return Stream.of(clazz.getMethods())
                .filter(m -> !m.isDefault())
                .findAny()
                .orElseThrow();
    }

    private static Object @NonNull [] mockParameters(Method method) {
        // mock all parameters (or use defaults for primitives)
        return ExceptionalStream.of(method.getParameters())
                .exceptionalMap(p -> {
                    if (p.getType().isPrimitive()) {
                        return switch (p.getType().getName()) {
                            case "boolean" -> false;
                            case "byte" -> (byte) 0;
                            case "char" -> (char) 0;
                            case "double" -> 0d;
                            case "float" -> 0f;
                            case "int" -> 0;
                            case "long" -> 0L;
                            default -> throw new RuntimeException(p.getType().toString());
                        };
                    } else {
                        return mock(p.getType(), RETURNS_DEFAULTS);
                    }
                })
                .toArray();
    }

}
