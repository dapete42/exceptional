package net.dapete.exceptional.test;

import net.dapete.exceptional.function.Wrappable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Answers.CALLS_REAL_METHODS;
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

        // call the method on a mock calling real methods
        final var exceptionalInterfaceMock = mock(clazz, CALLS_REAL_METHODS);
        final var functionalInterfaceResult = wrapMethod.invoke(exceptionalInterfaceMock);
        assertInstanceOf(returnType, functionalInterfaceResult);

    }

}
