package net.dapete.exceptional;

/**
 * General utility class for Exceptional!
 */
public final class ExceptionalUtils {

    // Utility class with private constructor
    private ExceptionalUtils() {
    }

    /**
     * If the {@code exception} is an instance of {@code exceptionClass}, throw it.
     *
     * @param exceptionClass the possible class of the exception
     * @param exception      the exception
     * @param <E>            the possible type of the exception
     * @throws E if the {@code exception} is an instance of {@code exceptionClass}.
     */
    public static <E extends Exception> void throwIfInstance(Class<E> exceptionClass, Exception exception) throws E {
        if (exceptionClass.isInstance(exception)) {
            throw exceptionClass.cast(exception);
        }
    }

    /**
     * If {@code exception} is not already a runtime exception, wrap it in an {@link ExceptionalException}.
     *
     * @param exception exception
     * @return {@code exception} or {@code exception} wrapped in an {@link ExceptionalException}
     */
    public static RuntimeException toRuntimeException(Exception exception) {
        if (exception instanceof RuntimeException runtimeException) {
            return runtimeException;
        } else {
            return new ExceptionalException(exception);
        }
    }

}
