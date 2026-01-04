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
import java.lang.reflect.ParameterizedType;
import java.nio.file.Files;
import java.nio.file.Path;
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

    static @NonNull List<? extends Class<?>> getFunctionalInterfacesClasses() {
        return Stream.of("net.dapete.exceptional.function", "net.dapete.exceptional.stream")
                .flatMap(AllExceptionalFunctionalInterfaces::getFunctionalInterfacesClasses)
                .toList();
    }

    private static @NonNull Stream<? extends Class<?>> getFunctionalInterfacesClasses(String packageName) {
        final var packagePath = packageName.replace('.', '/');
        try {
            final var filesNames = filesNamesInDirectory("src/main/java/" + packagePath);
            return ExceptionalException.unwrap(ClassNotFoundException.class, () ->
                    ExceptionalStream.of(filesNames)
                            .filter(fileName -> fileName.startsWith("Exceptional"))
                            .filter(fileName -> fileName.endsWith(".java"))
                            .map(fileName -> fileName.replaceFirst("\\.java$", ""))
                            .exceptionalMap(className -> Class.forName(packageName + '.' + className))
                            .filter(Class::isInterface)
                            .filter(Wrappable.class::isAssignableFrom)
                            .toList()
                            .stream()
            );
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> filesNamesInDirectory(String directoryName) throws IOException {
        try (final var fileStream = Files.find(Path.of(directoryName), 1, (p, a) -> true)) {
            return fileStream
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .toList();
        }
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
