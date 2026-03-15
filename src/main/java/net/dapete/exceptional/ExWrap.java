package net.dapete.exceptional;

import net.dapete.exceptional.function.*;

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
            throw ExUtils.toRuntimeException(e);
        }
    }

    /**
     * Executes an {@link ExBooleanSupplier}, calling its {@code getAsBoolean()} method and returning the result.
     * <p>
     * If a checked exception is thrown, an {@link ExException}, which is a runtime exception, will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause()}  cause}.
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
     * This will have the original exception as its {@link ExException#getCause()}  cause}.
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
     * This will have the original exception as its {@link ExException#getCause()}  cause}.
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
     * This will have the original exception as its {@link ExException#getCause()}  cause}.
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
     * Unwraps any {@code ExException} thrown when executing {@link Runnable#run() runnable.run()} and throws its {@link ExException#getCause() cause} instead,
     * if it is an instance of {@code exceptionClass}.
     *
     * @param exceptionClass the class of the cause possible of the {@code ExException}.
     * @param runnable       a runnable that may throw an {@code ExException}.
     * @param <E>            the type of the cause of the {@code ExException}.
     * @throws E           the cause of the {@code ExException}, if {@code runnable} throws one and its cause is an instance of
     *                     {@code exceptionClass}.
     * @throws ExException the {@code ExException}, if one was thrown and its cause is not an instance of {@code exceptionClass}.
     */
    public static <E extends Exception> void unwrap(Class<E> exceptionClass, Runnable runnable) throws E {
        try {
            runnable.run();
        } catch (ExException e) {
            e.unwrap(exceptionClass);
            // the compiler doesn't know that unwrap always throws an exception
            throw e;
        }
    }

    /**
     * Unwraps any {@code ExException} thrown when executing {@link Runnable#run() runnable.run()} and throws its {@link ExException#getCause() cause} instead,
     * if it is an instance of {@code exceptionClass1} or {@code exceptionClass2}.
     *
     * @param exceptionClass1 the class of the first possible cause of the {@code ExException}.
     * @param exceptionClass2 the class of the second possible cause of the {@code ExException}.
     * @param runnable        a runnable that may throw an {@code ExException}.
     * @param <E1>            the type of the first possible cause of the {@code ExException}.
     * @param <E2>            the type of the second possible cause of the {@code ExException}.
     * @throws E1          the cause of the {@code ExException}, if {@code runnable} throws one and its cause is an instance of
     *                     {@code exceptionClass1}.
     * @throws E2          the cause of the {@code ExException}, if {@code runnable} throws one and its cause is an instance of
     *                     {@code exceptionClass2}.
     * @throws ExException the {@code ExException}, if one was thrown and its cause is not an instance of {@code exceptionClass1} or
     *                     {@code exceptionClass2}.
     */
    public static <E1 extends Exception, E2 extends Exception> void unwrap(
            Class<E1> exceptionClass1, Class<E2> exceptionClass2, Runnable runnable) throws E1, E2 {
        try {
            runnable.run();
        } catch (ExException e) {
            e.unwrap(exceptionClass1, exceptionClass2);
            // the compiler doesn't know that unwrap always throws an exception
            throw e;
        }
    }

    /**
     * Unwraps any {@code ExException} thrown when executing {@link Runnable#run() runnable.run()} and throws its {@link ExException#getCause() cause} instead,
     * if it is an instance of {@code exceptionClass1}, {@code exceptionClass2} or {@code exceptionClass3}.
     *
     * @param exceptionClass1 the class of the first possible cause of the {@code ExException}.
     * @param exceptionClass2 the class of the second possible cause of the {@code ExException}.
     * @param exceptionClass3 the class of the third possible cause of the {@code ExException}.
     * @param runnable        a runnable that may throw an {@code ExException}.
     * @param <E1>            the type of the first possible cause of the {@code ExException}.
     * @param <E2>            the type of the second possible cause of the {@code ExException}.
     * @param <E3>            the type of the third possible cause of the {@code ExException}.
     * @throws E1          the cause of the {@code ExException}, if {@code runnable} throws one and its cause is an instance of {@code exceptionClass1}.
     * @throws E2          the cause of the {@code ExException}, if {@code runnable} throws one and its cause is an instance of {@code exceptionClass2}.
     * @throws E3          the cause of the {@code ExException}, if {@code runnable} throws one and its cause is an instance of {@code exceptionClass3}.
     * @throws ExException the {@code ExException}, if one was thrown and its cause is not an instance of {@code exceptionClass1},
     *                     {@code exceptionClass2} or {@code exceptionClass3}.
     */
    public static <E1 extends Exception, E2 extends Exception, E3 extends Exception> void unwrap(
            Class<E1> exceptionClass1, Class<E2> exceptionClass2, Class<E3> exceptionClass3, Runnable runnable) throws E1, E2, E3 {
        try {
            runnable.run();
        } catch (ExException e) {
            e.unwrap(exceptionClass1, exceptionClass2, exceptionClass3);
            // the compiler doesn't know that unwrap always throws an exception
            throw e;
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

    /**
     * Unwraps any {@code ExException} thrown when executing {@link Supplier#get() supplier.get()} and throws its {@link ExException#getCause() cause} instead,
     * if it is an instance of {@code exceptionClass}.
     *
     * @param exceptionClass the class of the cause of the {@code ExException}.
     * @param supplier       a supplier that may throw an {@code ExException}.
     * @param <T>            the return type of {@code supplier.get()}.
     * @param <E>            the type of the cause of the {@code ExException}.
     * @return the result of {@code supplier.get()}.
     * @throws E           the cause of the {@code ExException}, if {@code supplier} throws one and its cause is an instance of {@code exceptionClass}.
     * @throws ExException the {@code ExException}, if one was thrown and its cause is not an instance of {@code exceptionClass}.
     */
    public static <T, E extends Exception> T unwrap(Class<E> exceptionClass, Supplier<T> supplier) throws E {
        try {
            return supplier.get();
        } catch (ExException e) {
            e.unwrap(exceptionClass);
            // the compiler doesn't know that unwrap always throws an exception
            throw e;
        }
    }

    /**
     * Unwraps any {@code ExException} thrown when executing {@link Supplier#get() supplier.get()} and throws its {@link ExException#getCause() cause} instead,
     * if it is an instance of {@code exceptionClass1} or {@code exceptionClass2}.
     *
     * @param exceptionClass1 the class of the first possible cause of the {@code ExException}.
     * @param exceptionClass2 the class of the second possible cause of the {@code ExException}.
     * @param supplier        a supplier that may throw an {@code ExException}.
     * @param <T>             the return type of {@code supplier.get()}.
     * @param <E1>            the type of the first possible cause of the {@code ExException}.
     * @param <E2>            the type of the second possible cause of the {@code ExException}.
     * @return the result of {@code supplier.get()}.
     * @throws E1          the cause of the {@code ExException}, if {@code supplier} throws one and its cause is an instance of
     *                     {@code exceptionClass1}.
     * @throws E2          the cause of the {@code ExException}, if {@code supplier} throws one and its cause is an instance of
     *                     {@code exceptionClass2}.
     * @throws ExException the {@code ExException}, if one was thrown and its cause is not an instance of {@code exceptionClass1} or
     *                     {@code exceptionClass2}.
     */
    public static <T, E1 extends Exception, E2 extends Exception> T unwrap(
            Class<E1> exceptionClass1, Class<E2> exceptionClass2, Supplier<T> supplier) throws E1, E2 {
        try {
            return supplier.get();
        } catch (ExException e) {
            e.unwrap(exceptionClass1, exceptionClass2);
            // the compiler doesn't know that unwrap always throws an exception
            throw e;
        }
    }

    /**
     * Unwraps any {@code ExException} thrown when executing {@link Supplier#get() supplier.get()} and throws its {@link ExException#getCause() cause} instead,
     * if it is an instance of {@code exceptionClass1}, {@code exceptionClass2} or {@code exceptionClass3}.
     *
     * @param exceptionClass1 the class of the first possible cause of the {@code ExException}.
     * @param exceptionClass2 the class of the second possible cause of the {@code ExException}.
     * @param exceptionClass3 the class of the third possible cause of the {@code ExException}.
     * @param supplier        a supplier that may throw an {@code ExException}.
     * @param <T>             the return type of {@code supplier.get()}.
     * @param <E1>            the type of the first possible cause of the {@code ExException}.
     * @param <E2>            the type of the second possible cause of the {@code ExException}.
     * @param <E3>            the type of the third possible cause of the {@code ExException}.
     * @return the result of {@code supplier.get()}.
     * @throws E1          the cause of the {@code ExException}, if {@code supplier} throws one and its cause is an instance of
     *                     {@code exceptionClass1}.
     * @throws E2          the cause of the {@code ExException}, if {@code supplier} throws one and its cause is an instance of
     *                     {@code exceptionClass2}.
     * @throws E3          the cause of the {@code ExException}, if {@code supplier} throws one and its cause is an instance of
     *                     {@code exceptionClass3}.
     * @throws ExException the {@code ExException}, if one was thrown and its cause is not an instance of {@code exceptionClass1},
     *                     {@code exceptionClass2} or {@code exceptionClass3}.
     */
    public static <T, E1 extends Exception, E2 extends Exception, E3 extends Exception> T unwrap(
            Class<E1> exceptionClass1, Class<E2> exceptionClass2, Class<E3> exceptionClass3, Supplier<T> supplier) throws E1, E2, E3 {
        try {
            return supplier.get();
        } catch (ExException e) {
            e.unwrap(exceptionClass1, exceptionClass2, exceptionClass3);
            // the compiler doesn't know that unwrap always throws an exception
            throw e;
        }
    }

}
