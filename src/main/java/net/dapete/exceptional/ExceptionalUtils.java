package net.dapete.exceptional;

import net.dapete.exceptional.function.ExceptionalRunnable;
import net.dapete.exceptional.function.ExceptionalSupplier;
import org.jspecify.annotations.NullMarked;

/**
 * General utility class for Exceptional!
 */
@NullMarked
public final class ExceptionalUtils {

    // Utility class with private constructor
    private ExceptionalUtils() {
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

    /**
     * Executes an {@link ExceptionalSupplier}, calling its {@code get()} method and returning the result.
     * <p>
     * If a checked exception is thrown, an {@link ExceptionalException}, which is a runtime exception, will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause()}  cause}.
     *
     * @param supplier an {@code ExceptionalSupplier} to execute.
     * @param <T>      the type of results supplied by {@code supplier}.
     * @param <E>      the type of exception thrown by the {@code ExceptionalSupplier}.
     * @return the result of {@code supplier.get()}.
     * @throws ExceptionalException if {@code runnable.run()} threw a checked exception.
     */
    public static <T, E extends Exception> T wrapAndGet(ExceptionalSupplier<T, E> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            throw toRuntimeException(e);
        }
    }

    /**
     * Executes an {@link ExceptionalRunnable}, calling its {@code run()} method.
     * <p>
     * If a checked exception is thrown, an {@link ExceptionalException}, which is a runtime exception, will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param runnable an {@code ExceptionalRunnable} to execute.
     * @param <E>      the type of exception thrown by the {@code ExceptionalRunnable}.
     * @throws ExceptionalException if {@code runnable.run()} threw a checked exception.
     */
    public static <E extends Exception> void wrapAndRun(ExceptionalRunnable<E> runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            throw toRuntimeException(e);
        }
    }

}
