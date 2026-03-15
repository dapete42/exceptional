package net.dapete.exceptional;

/**
 * General utility class for Exceptional!
 */
final class ExUtils {

    // Utility class with private constructor
    private ExUtils() {
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
