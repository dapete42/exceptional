package net.dapete.exceptional.internal;

import net.dapete.exceptional.ExException;

import java.util.HashSet;
import java.util.Set;

/**
 * General utility class for Exceptional!
 */
public final class ExUtils {

    // Utility class with private constructor
    private ExUtils() {
    }

    /**
     * Create a set containing the given elements. This supports equal values, which {@link Set#of(Object...)} does not.
     *
     * @param element1 the first element.
     * @param element2 the second element.
     * @param element3 the third element.
     * @param <T>      the type of the element.
     * @return a set containing the given elements.
     */
    public static <T> Set<T> setOf3(T element1, T element2, T element3) {
        final Set<T> exceptionClasses = new HashSet<>();
        exceptionClasses.add(element1);
        exceptionClasses.add(element2);
        exceptionClasses.add(element3);
        return exceptionClasses;
    }

    /**
     * If the {@code exception} is an instance of {@code exceptionClass}, throw it.
     *
     * @param exceptionClass the class of the exception.
     * @param exception      the exception.
     * @param <E>            the type of the exception.
     * @throws E if the {@code exception} is an instance of {@code exceptionClass}.
     */
    public static <E extends Exception> void throwIfInstance(Class<E> exceptionClass, Exception exception) throws E {
        if (exceptionClass.isInstance(exception)) {
            throw exceptionClass.cast(exception);
        }
    }

    /**
     * If {@code exception} is not already a runtime exception, wrap it in an {@link ExException}.
     *
     * @param exception exception
     * @return {@code exception} or {@code exception} wrapped in an {@link ExException}
     */
    public static RuntimeException toRuntimeException(Exception exception) {
        if (exception instanceof RuntimeException runtimeException) {
            return runtimeException;
        } else {
            return new ExException(exception);
        }
    }

}
