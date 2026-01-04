package net.dapete.exceptional;

import org.jspecify.annotations.NonNull;

import java.io.Serial;
import java.util.function.Supplier;

/**
 * Exception that wraps exceptions returned by the functional interfaces in the {@link net.dapete.exceptional.function} package.
 */
public final class ExceptionalException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Create a new instance. The {@code cause} must not be a {@link RuntimeException}.
     *
     * @param cause the cause of this exception. Must not be a {@code RuntimeException}.
     * @throws IllegalArgumentException if {@code cause} is a {@code RuntimeException}.
     */
    ExceptionalException(@NonNull Exception cause) {
        super(cause);
        if (cause instanceof RuntimeException) {
            throw new IllegalArgumentException("The cause of an ExceptionalException must not be a RuntimeException");
        }
    }

    /**
     * Override the default {@link Throwable#getCause()} to return an {@code Exception}, not just a {@code Throwable}.
     * The cause of an {@code ExceptionalException} is guaranteed to be an {@code Exception}.
     *
     * @return the cause of this exception.
     */
    @Override
    public synchronized Exception getCause() {
        if (super.getCause() instanceof Exception exception) {
            return exception;
        } else {
            // this should never happen, this is a last resort
            return new Exception(super.getCause());
        }
    }

    /**
     * Unwraps this exception, throwing its {@link #getCause() cause}.
     *
     * @throws Exception the cause of this exception.
     */
    @SuppressWarnings("DoNotCallSuggester")
    public void unwrap() throws Exception {
        throw getCause();
    }

    /**
     * Unwraps this exception, throwing its {@link #getCause() cause} if it is an instance of {@code exceptionClass}.
     * Otherwise, throws this exception.
     *
     * @param exceptionClass the class of the possible cause of this exception
     * @param <E>            the type of the first possible cause of this exception
     * @throws E                    the cause of this exception, if it is an instance of {@code exceptionClass}.
     * @throws ExceptionalException if the cause of this exception is not an instance of {@code exceptionClass}.
     */
    public <E extends Exception> void unwrap(@NonNull Class<E> exceptionClass) throws E {
        final Exception cause = getCause();
        ExceptionalUtils.throwIfInstance(exceptionClass, cause);
        throw this;
    }

    /**
     * Unwraps this exception, throwing its {@link #getCause() cause} if it is an instance of {@code exceptionClass1} or {@code exceptionClass2}.
     * Otherwise, throws this exception.
     *
     * @param exceptionClass1 the class of the first possible cause of this exception
     * @param exceptionClass2 the class of the second possible cause of this exception
     * @param <E1>            the type of the first possible cause of this exception
     * @param <E2>            the type of the second possible cause of this exception
     * @throws E1                   the cause of this exception, if it is an instance of {@code exceptionClass1}.
     * @throws E2                   the cause of this exception, if it is an instance of {@code exceptionClass2}.
     * @throws ExceptionalException if the cause of this exception is not an instance of {@code exceptionClass1} or {@code exceptionClass2}.
     */
    public <E1 extends Exception, E2 extends Exception> void unwrap(@NonNull Class<E1> exceptionClass1, @NonNull Class<E2> exceptionClass2) throws E1, E2 {
        final Exception cause = getCause();
        ExceptionalUtils.throwIfInstance(exceptionClass1, cause);
        ExceptionalUtils.throwIfInstance(exceptionClass2, cause);
        throw this;
    }

    /**
     * Unwraps this exception, throwing its {@link #getCause() cause} if it is an instance of {@code exceptionClass1}, {@code exceptionClass2} or
     * {@code exceptionClass3}.
     * Otherwise, throws this exception.
     *
     * @param exceptionClass1 the class of the first possible cause of this exception
     * @param exceptionClass2 the class of the second possible cause of this exception
     * @param exceptionClass3 the class of the third possible cause of this exception
     * @param <E1>            the type of the first possible cause of this exception
     * @param <E2>            the type of the second possible cause of this exception
     * @param <E3>            the type of the third possible cause of this exception
     * @throws E1                   the cause of this exception, if it is an instance of {@code exceptionClass1}.
     * @throws E2                   the cause of this exception, if it is an instance of {@code exceptionClass2}.
     * @throws E3                   the cause of this exception, if it is an instance of {@code exceptionClass3}.
     * @throws ExceptionalException if the cause of this exception is not an instance of {@code exceptionClass1}, {@code exceptionClass2} or
     *                              {@code exceptionClass3}.
     */
    public <E1 extends Exception, E2 extends Exception, E3 extends Exception> void unwrap(
            @NonNull Class<E1> exceptionClass1, @NonNull Class<E2> exceptionClass2, @NonNull Class<E3> exceptionClass3) throws E1, E2, E3 {
        final Exception cause = getCause();
        ExceptionalUtils.throwIfInstance(exceptionClass1, cause);
        ExceptionalUtils.throwIfInstance(exceptionClass2, cause);
        ExceptionalUtils.throwIfInstance(exceptionClass3, cause);
        throw this;
    }

    /**
     * Unwraps any {@code ExceptionalException} thrown when executing {@link Runnable#run() runnable.run()} and throws its {@link #getCause() cause} instead.
     *
     * @param runnable a runnable that may throw an {@code ExceptionalException}.
     * @throws Exception the cause of the {@code ExceptionalException}, if {@code runnable} throws one.
     */
    public static void unwrap(@NonNull Runnable runnable) throws Exception {
        try {
            runnable.run();
        } catch (ExceptionalException e) {
            throw e.getCause();
        }
    }

    /**
     * Unwraps any {@code ExceptionalException} thrown when executing {@link Runnable#run() runnable.run()} and throws its {@link #getCause() cause} instead,
     * if it is an instance of {@code exceptionClass}.
     *
     * @param exceptionClass the class of the cause possible of the {@code ExceptionalException}.
     * @param runnable       a runnable that may throw an {@code ExceptionalException}.
     * @param <E>            the type of the cause of the {@code ExceptionalException}.
     * @throws E                    the cause of the {@code ExceptionalException}, if {@code runnable} throws one and its cause is an instance of
     *                              {@code exceptionClass}.
     * @throws ExceptionalException the {@code ExceptionalException}, if one was thrown and its cause is not an instance of {@code exceptionClass}.
     */
    public static <E extends Exception> void unwrap(@NonNull Class<E> exceptionClass, @NonNull Runnable runnable) throws E {
        try {
            runnable.run();
        } catch (ExceptionalException e) {
            e.unwrap(exceptionClass);
            // the compiler doesn't know that unwrap always throws an exception
            throw e;
        }
    }

    /**
     * Unwraps any {@code ExceptionalException} thrown when executing {@link Runnable#run() runnable.run()} and throws its {@link #getCause() cause} instead,
     * if it is an instance of {@code exceptionClass1} or {@code exceptionClass2}.
     *
     * @param exceptionClass1 the class of the first possible cause of the {@code ExceptionalException}.
     * @param exceptionClass2 the class of the second possible cause of the {@code ExceptionalException}.
     * @param runnable        a runnable that may throw an {@code ExceptionalException}.
     * @param <E1>            the type of the first possible cause of the {@code ExceptionalException}.
     * @param <E2>            the type of the second possible cause of the {@code ExceptionalException}.
     * @throws E1                   the cause of the {@code ExceptionalException}, if {@code runnable} throws one and its cause is an instance of
     *                              {@code exceptionClass1}.
     * @throws E2                   the cause of the {@code ExceptionalException}, if {@code runnable} throws one and its cause is an instance of
     *                              {@code exceptionClass2}.
     * @throws ExceptionalException the {@code ExceptionalException}, if one was thrown and its cause is not an instance of {@code exceptionClass1} or
     *                              {@code exceptionClass2}.
     */
    public static <E1 extends Exception, E2 extends Exception> void unwrap(
            @NonNull Class<E1> exceptionClass1, @NonNull Class<E2> exceptionClass2, @NonNull Runnable runnable) throws E1, E2 {
        try {
            runnable.run();
        } catch (ExceptionalException e) {
            e.unwrap(exceptionClass1, exceptionClass2);
            // the compiler doesn't know that unwrap always throws an exception
            throw e;
        }
    }

    /**
     * Unwraps any {@code ExceptionalException} thrown when executing {@link Runnable#run() runnable.run()} and throws its {@link #getCause() cause} instead,
     * if it is an instance of {@code exceptionClass1}, {@code exceptionClass2} or {@code exceptionClass3}.
     *
     * @param exceptionClass1 the class of the first possible cause of the {@code ExceptionalException}.
     * @param exceptionClass2 the class of the second possible cause of the {@code ExceptionalException}.
     * @param exceptionClass3 the class of the third possible cause of the {@code ExceptionalException}.
     * @param runnable        a runnable that may throw an {@code ExceptionalException}.
     * @param <E1>            the type of the first possible cause of the {@code ExceptionalException}.
     * @param <E2>            the type of the second possible cause of the {@code ExceptionalException}.
     * @param <E3>            the type of the third possible cause of the {@code ExceptionalException}.
     * @throws E1                   the cause of the {@code ExceptionalException}, if {@code runnable} throws one and its cause is an instance of {@code exceptionClass1}.
     * @throws E2                   the cause of the {@code ExceptionalException}, if {@code runnable} throws one and its cause is an instance of {@code exceptionClass2}.
     * @throws E3                   the cause of the {@code ExceptionalException}, if {@code runnable} throws one and its cause is an instance of {@code exceptionClass3}.
     * @throws ExceptionalException the {@code ExceptionalException}, if one was thrown and its cause is not an instance of {@code exceptionClass1},
     *                              {@code exceptionClass2} or {@code exceptionClass3}.
     */
    public static <E1 extends Exception, E2 extends Exception, E3 extends Exception> void unwrap(
            @NonNull Class<E1> exceptionClass1, @NonNull Class<E2> exceptionClass2, @NonNull Class<E3> exceptionClass3, @NonNull Runnable runnable)
            throws E1, E2, E3 {
        try {
            runnable.run();
        } catch (ExceptionalException e) {
            e.unwrap(exceptionClass1, exceptionClass2, exceptionClass3);
            // the compiler doesn't know that unwrap always throws an exception
            throw e;
        }
    }

    /**
     * Unwraps any {@code ExceptionalException} thrown when executing {@link Supplier#get() supplier.get()} and throws its {@link #getCause() cause} instead.
     *
     * @param supplier a supplier that may throw an {@code ExceptionalException}.
     * @param <T>      the return type of {@code supplier.get()}.
     * @return the result of {@code supplier.get()}.
     * @throws Exception the cause of the {@code ExceptionalExceptionExceptionalException}, if {@code runnable} throws one.
     */
    public static <T> T unwrap(@NonNull Supplier<T> supplier) throws Exception {
        try {
            return supplier.get();
        } catch (ExceptionalException e) {
            throw e.getCause();
        }
    }

    /**
     * Unwraps any {@code ExceptionalException} thrown when executing {@link Supplier#get() supplier.get()} and throws its {@link #getCause() cause} instead,
     * if it is an instance of {@code exceptionClass}.
     *
     * @param exceptionClass the class of the cause of the {@code ExceptionalException}.
     * @param supplier       a supplier that may throw an {@code ExceptionalException}.
     * @param <T>            the return type of {@code supplier.get()}.
     * @param <E>            the type of the cause of the {@code ExceptionalException}.
     * @return the result of {@code supplier.get()}.
     * @throws E                    the cause of the {@code ExceptionalException}, if {@code supplier} throws one and its cause is an instance of {@code exceptionClass}.
     * @throws ExceptionalException the {@code ExceptionalException}, if one was thrown and its cause is not an instance of {@code exceptionClass}.
     */
    public static <T, E extends Exception> T unwrap(@NonNull Class<E> exceptionClass, @NonNull Supplier<T> supplier) throws E {
        try {
            return supplier.get();
        } catch (ExceptionalException e) {
            e.unwrap(exceptionClass);
            // the compiler doesn't know that unwrap always throws an exception
            throw e;
        }
    }

    /**
     * Unwraps any {@code ExceptionalException} thrown when executing {@link Supplier#get() supplier.get()} and throws its {@link #getCause() cause} instead,
     * if it is an instance of {@code exceptionClass1} or {@code exceptionClass2}.
     *
     * @param exceptionClass1 the class of the first possible cause of the {@code ExceptionalException}.
     * @param exceptionClass2 the class of the second possible cause of the {@code ExceptionalException}.
     * @param supplier        a supplier that may throw an {@code ExceptionalException}.
     * @param <T>             the return type of {@code supplier.get()}.
     * @param <E1>            the type of the first possible cause of the {@code ExceptionalException}.
     * @param <E2>            the type of the second possible cause of the {@code ExceptionalException}.
     * @return the result of {@code supplier.get()}.
     * @throws E1                   the cause of the {@code ExceptionalException}, if {@code supplier} throws one and its cause is an instance of
     *                              {@code exceptionClass1}.
     * @throws E2                   the cause of the {@code ExceptionalException}, if {@code supplier} throws one and its cause is an instance of
     *                              {@code exceptionClass2}.
     * @throws ExceptionalException the {@code ExceptionalException}, if one was thrown and its cause is not an instance of {@code exceptionClass1} or
     *                              {@code exceptionClass2}.
     */
    public static <T, E1 extends Exception, E2 extends Exception> T unwrap(
            @NonNull Class<E1> exceptionClass1, @NonNull Class<E2> exceptionClass2, @NonNull Supplier<T> supplier) throws E1, E2 {
        try {
            return supplier.get();
        } catch (ExceptionalException e) {
            e.unwrap(exceptionClass1, exceptionClass2);
            // the compiler doesn't know that unwrap always throws an exception
            throw e;
        }
    }

    /**
     * Unwraps any {@code ExceptionalException} thrown when executing {@link Supplier#get() supplier.get()} and throws its {@link #getCause() cause} instead,
     * if it is an instance of {@code exceptionClass1}, {@code exceptionClass2} or {@code exceptionClass3}.
     *
     * @param exceptionClass1 the class of the first possible cause of the {@code ExceptionalException}.
     * @param exceptionClass2 the class of the second possible cause of the {@code ExceptionalException}.
     * @param exceptionClass3 the class of the third possible cause of the {@code ExceptionalException}.
     * @param supplier        a supplier that may throw an {@code ExceptionalException}.
     * @param <T>             the return type of {@code supplier.get()}.
     * @param <E1>            the type of the first possible cause of the {@code ExceptionalException}.
     * @param <E2>            the type of the second possible cause of the {@code ExceptionalException}.
     * @param <E3>            the type of the third possible cause of the {@code ExceptionalException}.
     * @return the result of {@code supplier.get()}.
     * @throws E1                   the cause of the {@code ExceptionalException}, if {@code supplier} throws one and its cause is an instance of
     *                              {@code exceptionClass1}.
     * @throws E2                   the cause of the {@code ExceptionalException}, if {@code supplier} throws one and its cause is an instance of
     *                              {@code exceptionClass2}.
     * @throws E3                   the cause of the {@code ExceptionalException}, if {@code supplier} throws one and its cause is an instance of
     *                              {@code exceptionClass3}.
     * @throws ExceptionalException the {@code ExceptionalException}, if one was thrown and its cause is not an instance of {@code exceptionClass1},
     *                              {@code exceptionClass2} or {@code exceptionClass3}.
     */
    public static <T, E1 extends Exception, E2 extends Exception, E3 extends Exception> T unwrap(
            @NonNull Class<E1> exceptionClass1, @NonNull Class<E2> exceptionClass2, @NonNull Class<E3> exceptionClass3, @NonNull Supplier<T> supplier)
            throws E1, E2, E3 {
        try {
            return supplier.get();
        } catch (ExceptionalException e) {
            e.unwrap(exceptionClass1, exceptionClass2, exceptionClass3);
            // the compiler doesn't know that unwrap always throws an exception
            throw e;
        }
    }

}
