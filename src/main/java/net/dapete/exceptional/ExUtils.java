package net.dapete.exceptional;

import net.dapete.exceptional.function.ExRunnable;
import net.dapete.exceptional.function.ExSupplier;
import org.jspecify.annotations.NullMarked;

/**
 * General utility class for Exceptional!
 */
@NullMarked
public final class ExUtils {

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

    /**
     * Executes an {@link ExSupplier}, calling its {@code get()} method and returning the result.
     * <p>
     * If a checked exception is thrown, an {@link ExException}, which is a runtime exception, will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause()}  cause}.
     *
     * @param supplier an {@code ExSupplier} to execute.
     * @param <T>      the type of results supplied by {@code supplier}.
     * @param <E>      the type of exception thrown by the {@code ExSupplier}.
     * @return the result of {@code supplier.get()}.
     * @throws ExException if {@code runnable.run()} threw a checked exception.
     */
    public static <T, E extends Exception> T wrap(ExSupplier<T, E> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            throw toRuntimeException(e);
        }
    }

    /**
     * Executes an {@link ExRunnable}, calling its {@code run()} method.
     * <p>
     * If a checked exception is thrown, an {@link ExException}, which is a runtime exception, will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param runnable an {@code ExRunnable} to execute.
     * @param <E>      the type of exception thrown by the {@code ExRunnable}.
     * @throws ExException if {@code runnable.run()} threw a checked exception.
     */
    public static <E extends Exception> void wrap(ExRunnable<E> runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            throw toRuntimeException(e);
        }
    }

}
