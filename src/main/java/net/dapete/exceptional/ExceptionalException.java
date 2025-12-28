package net.dapete.exceptional;

import org.jspecify.annotations.NonNull;

import java.io.Serial;
import java.util.function.Supplier;

/**
 * Exception that wraps exceptions returned by the functional interfaces in the {@link net.dapete.exceptional.function} package.
 * <p>
 * Used by {@link ExceptionalWrapper}.
 */
public class ExceptionalException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1958847534223306021L;

    ExceptionalException(@NonNull Exception cause) {
        super(cause);
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
            throw new IllegalStateException("The cause of ExceptionalException can only be an Exception, but is just a Throwable");
        }
    }

    /**
     * Rethrows the {@link #getCause() cause} of this exception.
     *
     * @throws Exception the cause of this exception.
     */
    public void throwCause() throws Exception {
        throw getCause();
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
     * @param exceptionClass the class of the cause of the {@code ExceptionalException}
     * @param runnable       a runnable that may throw an {@code ExceptionalException}.
     * @param <E>            the type of the cause of the {@code ExceptionalException}
     * @throws E the cause of the {@code ExceptionalException}, if {@code runnable} throws one and its cause is an instance of {@code exceptionClass}.
     */
    public static <E extends Exception> void unwrap(@NonNull Class<E> exceptionClass, @NonNull Runnable runnable) throws E {
        try {
            runnable.run();
        } catch (ExceptionalException e) {
            final Exception cause = e.getCause();
            ExceptionalUtils.throwIfInstance(exceptionClass, cause);
            throw e;
        }
    }

    /**
     * Unwraps any {@code ExceptionalException} thrown when executing {@link Runnable#run() runnable.run()} and throws its {@link #getCause() cause} instead,
     * if it is an instance of {@code exceptionClass1} or {@code exceptionClass2}.
     *
     * @param exceptionClass1 the class of the first possible cause of the {@code ExceptionalException}
     * @param exceptionClass2 the class of the second possible cause of the {@code ExceptionalException}
     * @param runnable        a runnable that may throw an {@code ExceptionalException}.
     * @param <E1>            the type of the first possible cause of the {@code ExceptionalException}
     * @param <E2>            the type of the second possible cause of the {@code ExceptionalException}
     * @throws E1 the cause of the {@code ExceptionalException}, if {@code runnable} throws one and its cause is an instance of {@code exceptionClass1}.
     * @throws E2 the cause of the {@code ExceptionalException}, if {@code runnable} throws one and its cause is an instance of {@code exceptionClass2}.
     */
    public static <E1 extends Exception, E2 extends Exception> void unwrap(
            @NonNull Class<E1> exceptionClass1, @NonNull Class<E2> exceptionClass2, @NonNull Runnable runnable) throws E1, E2 {
        try {
            runnable.run();
        } catch (ExceptionalException e) {
            final Exception cause = e.getCause();
            ExceptionalUtils.throwIfInstance(exceptionClass1, cause);
            ExceptionalUtils.throwIfInstance(exceptionClass2, cause);
            throw e;
        }
    }

    /**
     * Unwraps any {@code ExceptionalException} thrown when executing {@link Runnable#run() runnable.run()} and throws its {@link #getCause() cause} instead,
     * if it is an instance of {@code exceptionClass1}, {@code exceptionClass2} or {@code exceptionClass3}.
     *
     * @param exceptionClass1 the class of the first possible cause of the {@code ExceptionalException}
     * @param exceptionClass2 the class of the second possible cause of the {@code ExceptionalException}
     * @param exceptionClass3 the class of the third possible cause of the {@code ExceptionalException}
     * @param runnable        a runnable that may throw an {@code ExceptionalException}.
     * @param <E1>            the type of the first possible cause of the {@code ExceptionalException}
     * @param <E2>            the type of the second possible cause of the {@code ExceptionalException}
     * @param <E3>            the type of the third possible cause of the {@code ExceptionalException}
     * @throws E1 the cause of the {@code ExceptionalException}, if {@code runnable} throws one and its cause is an instance of {@code exceptionClass1}.
     * @throws E2 the cause of the {@code ExceptionalException}, if {@code runnable} throws one and its cause is an instance of {@code exceptionClass2}.
     * @throws E3 the cause of the {@code ExceptionalException}, if {@code runnable} throws one and its cause is an instance of {@code exceptionClass3}.
     */
    public static <E1 extends Exception, E2 extends Exception, E3 extends Exception> void unwrap(
            @NonNull Class<E1> exceptionClass1, @NonNull Class<E2> exceptionClass2, @NonNull Class<E3> exceptionClass3, @NonNull Runnable runnable)
            throws E1, E2, E3 {
        try {
            runnable.run();
        } catch (ExceptionalException e) {
            final Exception cause = e.getCause();
            ExceptionalUtils.throwIfInstance(exceptionClass1, cause);
            ExceptionalUtils.throwIfInstance(exceptionClass2, cause);
            ExceptionalUtils.throwIfInstance(exceptionClass3, cause);
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
     * @param exceptionClass the class of the cause of the {@code ExceptionalException}
     * @param supplier       a supplier that may throw an {@code ExceptionalException}.
     * @param <T>            the return type of {@code supplier.get()}.
     * @param <E>            the type of the cause of the {@code ExceptionalException}
     * @return the result of {@code supplier.get()}.
     * @throws E the cause of the {@code ExceptionalException}, if {@code supplier} throws one and its cause is an instance of {@code exceptionClass}.
     */
    public static <T, E extends Exception> T unwrap(@NonNull Class<E> exceptionClass, @NonNull Supplier<T> supplier) throws E {
        try {
            return supplier.get();
        } catch (ExceptionalException e) {
            final Exception cause = e.getCause();
            if (exceptionClass.isInstance(cause)) {
                throw exceptionClass.cast(cause);
            } else {
                throw e;
            }
        }
    }

    /**
     * Unwraps any {@code ExceptionalException} thrown when executing {@link Supplier#get() supplier.get()} and throws its {@link #getCause() cause} instead,
     * if it is an instance of {@code exceptionClass1} or {@code exceptionClass2}.
     *
     * @param exceptionClass1 the class of the first possible cause of the {@code ExceptionalException}
     * @param exceptionClass2 the class of the second possible cause of the {@code ExceptionalException}
     * @param supplier        a supplier that may throw an {@code ExceptionalException}.
     * @param <T>             the return type of {@code supplier.get()}.
     * @param <E1>            the type of the first possible cause of the {@code ExceptionalException}
     * @param <E2>            the type of the second possible cause of the {@code ExceptionalException}
     * @return the result of {@code supplier.get()}.
     * @throws E1 the cause of the {@code ExceptionalException}, if {@code supplier} throws one and its cause is an instance of {@code exceptionClass1}.
     * @throws E2 the cause of the {@code ExceptionalException}, if {@code supplier} throws one and its cause is an instance of {@code exceptionClass2}.
     */
    public static <T, E1 extends Exception, E2 extends Exception> T unwrap(
            @NonNull Class<E1> exceptionClass1, @NonNull Class<E2> exceptionClass2, @NonNull Supplier<T> supplier) throws E1, E2 {
        try {
            return supplier.get();
        } catch (ExceptionalException e) {
            final Exception cause = e.getCause();
            ExceptionalUtils.throwIfInstance(exceptionClass1, cause);
            ExceptionalUtils.throwIfInstance(exceptionClass2, cause);
            throw e;
        }
    }

    /**
     * Unwraps any {@code ExceptionalException} thrown when executing {@link Supplier#get() supplier.get()} and throws its {@link #getCause() cause} instead,
     * if it is an instance of {@code exceptionClass1}, {@code exceptionClass2} or {@code exceptionClass3}.
     *
     * @param exceptionClass1 the class of the first possible cause of the {@code ExceptionalException}
     * @param exceptionClass2 the class of the second possible cause of the {@code ExceptionalException}
     * @param exceptionClass3 the class of the third possible cause of the {@code ExceptionalException}
     * @param supplier        a supplier that may throw an {@code ExceptionalException}.
     * @param <T>             the return type of {@code supplier.get()}.
     * @param <E1>            the type of the first possible cause of the {@code ExceptionalException}
     * @param <E2>            the type of the second possible cause of the {@code ExceptionalException}
     * @param <E3>            the type of the third possible cause of the {@code ExceptionalException}
     * @return the result of {@code supplier.get()}.
     * @throws E1 the cause of the {@code ExceptionalException}, if {@code supplier} throws one and its cause is an instance of {@code exceptionClass1}.
     * @throws E2 the cause of the {@code ExceptionalException}, if {@code supplier} throws one and its cause is an instance of {@code exceptionClass2}.
     * @throws E3 the cause of the {@code ExceptionalException}, if {@code supplier} throws one and its cause is an instance of {@code exceptionClass3}.
     */
    public static <T, E1 extends Exception, E2 extends Exception, E3 extends Exception> T unwrap(
            @NonNull Class<E1> exceptionClass1, @NonNull Class<E2> exceptionClass2, @NonNull Class<E3> exceptionClass3, @NonNull Supplier<T> supplier)
            throws E1, E2, E3 {
        try {
            return supplier.get();
        } catch (ExceptionalException e) {
            final Exception cause = e.getCause();
            ExceptionalUtils.throwIfInstance(exceptionClass1, cause);
            ExceptionalUtils.throwIfInstance(exceptionClass2, cause);
            ExceptionalUtils.throwIfInstance(exceptionClass3, cause);
            throw e;
        }
    }

}
