package net.dapete.exceptional.test;

import net.dapete.exceptional.ExceptionalException;
import net.dapete.exceptional.function.Wrappable;
import net.dapete.exceptional.stream.ExceptionalStream;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.ParameterDeclarations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class AllExceptionalFunctionalInterfaces implements ArgumentsProvider {

    @Override
    @NullMarked
    public Stream<? extends Arguments> provideArguments(ParameterDeclarations parameters, ExtensionContext context) {
        return getFunctionalInterfacesClasses().stream()
                .map(Arguments::of);
    }

    static @NonNull List<Class<?>> getFunctionalInterfacesClasses() {
        try (var fileStreamFunction = Files.find(Path.of("src/main/java/net/dapete/exceptional/function"), 1, (path, attributes) -> true);
             var fileStreamStream = Files.find(Path.of("src/main/java/net/dapete/exceptional/stream"), 1, (path, attributes) -> true)) {
            return ExceptionalException.unwrap(ClassNotFoundException.class, () ->
                    ExceptionalStream.of(Stream.concat(fileStreamFunction, fileStreamStream))
                            .map(Path::getFileName)
                            .map(Path::toString)
                            .filter(fileName -> fileName.startsWith("Exceptional"))
                            .filter(fileName -> fileName.endsWith(".java"))
                            .map(fileName -> fileName.replace(".java", ""))
                            .exceptionalMap(className -> {
                                try {
                                    return Class.forName("net.dapete.exceptional.function." + className);
                                } catch (ClassNotFoundException e) {
                                    return Class.forName("net.dapete.exceptional.stream." + className);
                                }
                            })
                            .filter(Objects::nonNull)
                            .filter(clazz -> Arrays.asList(clazz.getInterfaces()).contains(Wrappable.class))
                            .toList()
            );
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Class<?> getExpectedReturnType(Class<?> exceptionalClass) throws ClassNotFoundException {
        return switch (exceptionalClass.getSimpleName()) {
            case "ExceptionalRunnable" -> Runnable.class;
            case "ExceptionalDoubleMapMultiConsumer" -> DoubleStream.DoubleMapMultiConsumer.class;
            case "ExceptionalIntMapMultiConsumer" -> IntStream.IntMapMultiConsumer.class;
            case "ExceptionalLongMapMultiConsumer" -> LongStream.LongMapMultiConsumer.class;
            default -> {
                final var expectedClassName = "java.util.function." + exceptionalClass.getSimpleName().replace("Exceptional", "");
                yield Class.forName(expectedClassName);
            }
        };
    }

}
