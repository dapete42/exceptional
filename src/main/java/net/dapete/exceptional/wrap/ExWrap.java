package net.dapete.exceptional.wrap;

import net.dapete.exceptional.ExException;
import net.dapete.exceptional.function.*;
import net.dapete.exceptional.internal.ExUtils;

import java.util.function.Supplier;

/**
 * Wrapping utility class for Exceptional!
 */
public final class ExWrap {

    // Utility class with private constructor
    private ExWrap() {
    }

    /**
     * Executes an {@link ExSupplier}, calling its {@code get()} method and returning the result.
     * <p>
     * If a checked exception is thrown, an {@link ExException}, which is a runtime exception, will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
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
            throw ExUtils.toRuntimeException(e);
        }
    }

    /**
     * Executes an {@link ExBooleanSupplier}, calling its {@code getAsBoolean()} method and returning the result.
     * <p>
     * If a checked exception is thrown, an {@link ExException}, which is a runtime exception, will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param supplier an {@code ExBooleanSupplier} to execute.
     * @param <E>      the type of exception thrown by the {@code ExBooleanSupplier}.
     * @return the result of {@code supplier.get()}.
     * @throws ExException if {@code runnable.run()} threw a checked exception.
     */
    public static <E extends Exception> boolean wrap(ExBooleanSupplier<E> supplier) {
        try {
            return supplier.getAsBoolean();
        } catch (Exception e) {
            throw ExUtils.toRuntimeException(e);
        }
    }

    /**
     * Executes an {@link ExDoubleSupplier}, calling its {@code getAsDouble()} method and returning the result.
     * <p>
     * If a checked exception is thrown, an {@link ExException}, which is a runtime exception, will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param supplier an {@code ExDoubleSupplier} to execute.
     * @param <E>      the type of exception thrown by the {@code ExDoubleSupplier}.
     * @return the result of {@code supplier.get()}.
     * @throws ExException if {@code runnable.run()} threw a checked exception.
     */
    public static <E extends Exception> double wrap(ExDoubleSupplier<E> supplier) {
        try {
            return supplier.getAsDouble();
        } catch (Exception e) {
            throw ExUtils.toRuntimeException(e);
        }
    }

    /**
     * Executes an {@link ExIntSupplier}, calling its {@code getAsInt()} method and returning the result.
     * <p>
     * If a checked exception is thrown, an {@link ExException}, which is a runtime exception, will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param supplier an {@code ExIntSupplier} to execute.
     * @param <E>      the type of exception thrown by the {@code ExIntSupplier}.
     * @return the result of {@code supplier.get()}.
     * @throws ExException if {@code runnable.run()} threw a checked exception.
     */
    public static <E extends Exception> int wrap(ExIntSupplier<E> supplier) {
        try {
            return supplier.getAsInt();
        } catch (Exception e) {
            throw ExUtils.toRuntimeException(e);
        }
    }

    /**
     * Executes an {@link ExLongSupplier}, calling its {@code getAsLong()} method and returning the result.
     * <p>
     * If a checked exception is thrown, an {@link ExException}, which is a runtime exception, will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param supplier an {@code ExLongSupplier} to execute.
     * @param <E>      the type of exception thrown by the {@code ExLongSupplier}.
     * @return the result of {@code supplier.get()}.
     * @throws ExException if {@code runnable.run()} threw a checked exception.
     */
    public static <E extends Exception> long wrap(ExLongSupplier<E> supplier) {
        try {
            return supplier.getAsLong();
        } catch (Exception e) {
            throw ExUtils.toRuntimeException(e);
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
            throw ExUtils.toRuntimeException(e);
        }
    }

    /**
     * Unwraps any {@code ExException} thrown when executing {@link Runnable#run() runnable.run()} and throws its {@link ExException#getCause() cause} instead.
     *
     * @param runnable a runnable that may throw an {@code ExException}.
     * @throws Exception the cause of the {@code ExException}, if {@code runnable} throws one.
     */
    public static void unwrap(Runnable runnable) throws Exception {
        try {
            runnable.run();
        } catch (ExException e) {
            throw e.getCause();
        }
    }

    /**
     * Unwraps any {@code ExException} thrown when executing {@link Supplier#get() supplier.get()} and throws its {@link ExException#getCause() cause} instead.
     *
     * @param supplier a supplier that may throw an {@code ExException}.
     * @param <T>      the return type of {@code supplier.get()}.
     * @return the result of {@code supplier.get()}.
     * @throws Exception the cause of the {@code ExceptionalExceptionExceptionalException}, if {@code runnable} throws one.
     */
    public static <T> T unwrap(Supplier<T> supplier) throws Exception {
        try {
            return supplier.get();
        } catch (ExException e) {
            throw e.getCause();
        }
    }

}
