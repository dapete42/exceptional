package net.dapete.exceptional;

import java.util.HashSet;

/**
 * General utility class for Exceptional!
 */
public final class ExUtils {

    // Utility class with private constructor
    private ExUtils() {
    }

    /**
     * Create a {@link HashSet} containing only the given value.
     *
     * @param value value.
     * @param <T>   type of the value.
     * @return {@link HashSet} containing only the given value.
     */
    public static <T> @SuppressWarnings("NonApiType") HashSet<T> hashSetOf(T value) {
        final var hashSet = new HashSet<T>();
        hashSet.add(value);
        return hashSet;
    }

    /**
     * Create a {@link HashSet} containing only the given values.
     *
     * @param value1 first value.
     * @param value2 second value.
     * @param <T>    type of the value.
     * @return {@code HashSet} containing only the given values.
     */
    public static <T> @SuppressWarnings("NonApiType") HashSet<T> hashSetOf(T value1, T value2) {
        final var hashSet = new HashSet<T>();
        hashSet.add(value1);
        hashSet.add(value2);
        return hashSet;
    }

    /**
     * Create a {@link HashSet} containing only the given values.
     *
     * @param value1 first value.
     * @param value2 second value.
     * @param value3 third value.
     * @param <T>    type of the value.
     * @return {@code HashSet} containing only the given values.
     */
    public static <T> @SuppressWarnings("NonApiType") HashSet<T> hashSetOf(T value1, T value2, T value3) {
        final var hashSet = new HashSet<T>();
        hashSet.add(value1);
        hashSet.add(value2);
        hashSet.add(value3);
        return hashSet;
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
