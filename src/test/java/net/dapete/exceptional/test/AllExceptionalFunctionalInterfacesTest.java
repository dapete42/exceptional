package net.dapete.exceptional.test;

import net.dapete.exceptional.ExceptionalException;
import net.dapete.exceptional.function.Wrappable;
import net.dapete.exceptional.stream.ExceptionalStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.internal.stubbing.answers.CallsRealMethods;
import org.mockito.invocation.InvocationOnMock;

import java.io.Serial;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Answers.RETURNS_DEFAULTS;
import static org.mockito.Mockito.mock;

public class AllExceptionalFunctionalInterfacesTest {

    @Test
    void numberOfExceptionalFunctionalInterfaces() {
        assertEquals(47, AllExceptionalFunctionalInterfaces.getFunctionalInterfacesClasses().size());
    }

    @ParameterizedTest
    @ArgumentsSource(AllExceptionalFunctionalInterfaces.class)
    void wrap(Class<?> clazz) throws Exception {

        // implements Wrappable
        assertTrue(Arrays.asList(clazz.getInterfaces()).contains(Wrappable.class));

        // method wrap() exists (would throw a NoSuchMethodException otherwise) and has no parameters
        final var wrapMethod = clazz.getMethod("wrap");
        assertEquals(0, wrapMethod.getParameterCount());

        // return type matches expected type
        final var returnType = AllExceptionalFunctionalInterfaces.getExpectedReturnType(clazz);
        assertEquals(returnType, wrapMethod.getReturnType());

        // switch: throw exception for the functional interface method in class?
        final var exceptionalInterfaceMethod = findFunctionalInterfaceMethod(clazz);
        final var throwException = new AtomicBoolean(false);

        // this mock can either call real methods or throw an exception when the functional interface method (run, get, apply, etc.) is called
        final var exceptionalInterfaceMock = mock(clazz, new CallsRealMethods() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                if (throwException.get() && invocation.getMethod().equals(exceptionalInterfaceMethod)) {
                    throw new Exception("test");
                }
                return super.answer(invocation);
            }
        });

        // call the method on a mock calling real methods
        final var functionalInterfaceResult = wrapMethod.invoke(exceptionalInterfaceMock);
        assertInstanceOf(returnType, functionalInterfaceResult);

        // invoke functionalInterfaceResult.<functionalInterfaceMethod> (run, get, apply, etc.)
        final var functionalInterfaceMethod = findFunctionalInterfaceMethod(returnType);
        final var mockedParameters = mockParameters(functionalInterfaceMethod);
        functionalInterfaceMethod.invoke(functionalInterfaceResult, mockedParameters);

        // invoke it again, this time throwing an exception in the returned lambda
        throwException.set(true);
        // when using reflection, the actual exception is wrapped in an InvocationTargetException
        final var thrown = assertThrows(InvocationTargetException.class, () -> functionalInterfaceMethod.invoke(functionalInterfaceResult, mockedParameters));
        final var exceptionalException = assertInstanceOf(ExceptionalException.class, thrown.getCause());
        assertEquals("test", exceptionalException.getCause().getMessage());

    }

    private static Method findFunctionalInterfaceMethod(Class<?> clazz) {
        // a functional interface can only have one non-default method
        return Stream.of(clazz.getMethods())
                .filter(m -> AllExceptionalFunctionalInterfaces.FUNCTIONAL_INTERFACE_METHOD_NAMES.contains(m.getName()))
                .findFirst()
                .orElseThrow();
    }

    private static Object[] mockParameters(Method method) {
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
