package net.dapete.exceptional.function;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

public class PackageTest {

    @ParameterizedTest
    @ArgumentsSource(AllExceptionalFunctionalInterfacesArgumentsProvider.class)
    void wrapForAllExceptionalFunctionalInterfaces(Class<?> clazz) throws Exception {

        // implements Wrappable
        assertTrue(Arrays.asList(clazz.getInterfaces()).contains(Wrappable.class));

        // method exists (would throw a NoSuchMethodException otherwise)
        final var method = clazz.getMethod("wrap");

        // return type matches expected type
        final var expectedReturnType = AllExceptionalFunctionalInterfacesArgumentsProvider.getExpectedReturnType(clazz);
        assertEquals(expectedReturnType, method.getReturnType());

        // call works and returns a non-null object
        final var mockedInstance = mock(clazz, RETURNS_DEEP_STUBS);
        final var result = method.invoke(mockedInstance);
        assertNotNull(result);

    }

}
