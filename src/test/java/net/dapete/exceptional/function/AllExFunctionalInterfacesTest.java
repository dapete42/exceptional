package net.dapete.exceptional.function;

import net.dapete.exceptional.wrap.ExUnwrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Answers.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class AllExFunctionalInterfacesTest {

    @Test
    void numberOfExceptionalFunctionalInterfaces() {
        assertEquals(47, AllExFunctionalInterfaces.getFunctionalInterfacesClasses().size());
    }

    @ParameterizedTest
    @ArgumentsSource(AllExFunctionalInterfaces.class)
    void wrap(Class<?> clazz) throws Exception {

        // implements Wrappable
        assertTrue(Arrays.asList(clazz.getInterfaces()).contains(Wrappable.class));

        // method wrap() exists (would throw a NoSuchMethodException otherwise) and has no parameters
        final var wrapMethod1 = clazz.getMethod("wrap");
        assertEquals(0, wrapMethod1.getParameterCount());

        // method(wrap(Class)) exists and has a parameter of type Class
        final var wrapMethod2 = clazz.getMethod("wrap", Class.class);
        assertEquals(1, wrapMethod2.getParameterCount());
        assertEquals(Class.class, wrapMethod2.getParameterTypes()[0]);

        // return types match expected type
        final var returnType = AllExFunctionalInterfaces.getExpectedReturnType(clazz);
        assertEquals(returnType, wrapMethod1.getReturnType());
        // return type for wrapMethod2 cannot be checked (it's a generic implementation)

        // call the methods on a mock calling real methods
        final var interfaceMock = mock(clazz, CALLS_REAL_METHODS);

        final var result1 = wrapMethod1.invoke(interfaceMock);
        assertInstanceOf(returnType, result1);

        try (var exUnwrapMock = Mockito.mockStatic(ExUnwrapper.class)) {
            final var result2 = wrapMethod2.invoke(interfaceMock, IOException.class);
            assertInstanceOf(returnType, result2);
            final ArgumentCaptor<Class<? extends Exception>> captor = ArgumentCaptor.captor();
            exUnwrapMock.verify(() -> ExUnwrapper.verifyExceptionAllowed(captor.capture()));
            assertEquals(IOException.class, captor.getValue());
        }

    }

}
