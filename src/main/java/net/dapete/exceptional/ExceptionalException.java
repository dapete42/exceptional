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
     * Unwraps any {@code ExceptionalException} thrown when executing {@link Supplier#get() supplier.get()} and throws its {@link #getCause() cause} instead.
     *
     * @param supplier a supplier that may throw an {@code ExceptionalException}.
     * @param <T>      the return type of {@code supplier}.
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
     * @param <T>            the return type of {@code supplier}.
     * @param <E>            the type of the cause of the {@code ExceptionalException}
     * @throws E the cause of the {@code ExceptionalException}, if {@code runnable} throws one and its cause is an instance of {@code exceptionClass}.
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

}
