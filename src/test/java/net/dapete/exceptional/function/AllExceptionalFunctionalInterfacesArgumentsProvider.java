package net.dapete.exceptional.function;

import net.dapete.exceptional.stream.ExceptionalDoubleMapMultiConsumer;
import net.dapete.exceptional.stream.ExceptionalIntMapMultiConsumer;
import net.dapete.exceptional.stream.ExceptionalLongMapMultiConsumer;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.ParameterDeclarations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@NullMarked
public class AllExceptionalFunctionalInterfacesArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ParameterDeclarations parameters, ExtensionContext context) throws IOException {
        try (var fileStream = Files.find(Path.of("src/main/java/net/dapete/exceptional/function"), 1, (path, attributes) -> true)) {
            return Stream.concat(fileStream
                                    .map(Path::getFileName)
                                    .map(Path::toString)
                                    .filter(fileName -> fileName.startsWith("Exceptional"))
                                    .filter(fileName -> fileName.endsWith(".java"))
                                    .map(fileName -> fileName.replace(".java", ""))
                                    .map(className -> {
                                        try {
                                            return Class.forName("net.dapete.exceptional.function." + className);
                                        } catch (ClassNotFoundException e) {
                                            return null;
                                        }
                                    })
                                    .filter(Objects::nonNull),
                            Stream.of(ExceptionalDoubleMapMultiConsumer.class,
                                    ExceptionalIntMapMultiConsumer.class,
                                    ExceptionalLongMapMultiConsumer.class)
                    )
                    .map(Arguments::of)
                    .toList()
                    .stream();
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
