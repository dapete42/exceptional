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

    /**
     * Executes am {@link ExceptionalSupplier}, calling its {@code get()} method and returning the result.
     * <p>
     * If a checked exception is thrown, a {@link ExceptionalException}, which is a runtime exception, will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     *
     * @param supplier an {@code ExceptionalSupplier} to execute.
     * @return the result of {@code supplier.get()}.
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
     * If a checked exception is thrown, a {@link ExceptionalException}, which is a runtime exception, will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     *
     * @param runnable an {@code ExceptionalRunnable} to execute.
     */
    public static <E extends Exception> void wrapAndRun(ExceptionalRunnable<E> runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            throw toRuntimeException(e);
        }
    }

}
