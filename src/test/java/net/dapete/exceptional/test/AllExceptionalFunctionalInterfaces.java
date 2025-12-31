package net.dapete.exceptional.test;

import net.dapete.exceptional.function.Wrappable;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.ParameterDeclarations;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class AllExceptionalFunctionalInterfaces implements ArgumentsProvider {

    @Override
    @NullMarked
    public Stream<? extends Arguments> provideArguments(ParameterDeclarations parameters, ExtensionContext context) {
        return getFunctionalInterfacesClasses().stream()
                .map(Arguments::of);
    }

    static @NonNull List<Class<?>> getFunctionalInterfacesClasses() {
        return new Reflections("net.dapete.exceptional.function", "net.dapete.exceptional.stream")
                .get(Scanners.SubTypes.of(Wrappable.class).asClass())
                .stream()
                .filter(Class::isInterface)
                .toList();
    }

    public static Class<?> getExpectedReturnType(Class<?> exceptionalClass) {
        // the exceptional interfaces implement Wrappable<W>, extract Class<W> to get which type their wrap() method should return
        final var parameterizedType = Arrays.stream(exceptionalClass.getGenericInterfaces())
                .filter(t -> t instanceof ParameterizedType)
                .map(ParameterizedType.class::cast)
                .filter(pt -> pt.getRawType() == Wrappable.class)
                .findFirst()
                .orElseThrow();
        final var typeArgument = parameterizedType.getActualTypeArguments()[0];
        if (typeArgument instanceof ParameterizedType pt) {
            return (Class<?>) pt.getRawType();
        } else {
            return (Class<?>) typeArgument;
        }
    }

}
