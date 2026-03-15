package net.dapete.exceptional;

import org.jspecify.annotations.NullMarked;

import java.io.Serial;

/**
 * Exception that wraps exceptions returned by the functional interfaces in the {@link net.dapete.exceptional.function} package.
 */
@NullMarked
public final class ExException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2311138051728963821L;

    /**
     * Create a new instance. The {@code cause} must not be a {@link RuntimeException}.
     *
     * @param cause the cause of this exception. Must not be a {@code RuntimeException}.
     * @throws IllegalArgumentException if {@code cause} is a {@code RuntimeException}.
     */
    ExException(Exception cause) {
        super(cause);
        if (cause instanceof RuntimeException) {
            throw new IllegalArgumentException("The cause of an ExException must not be a RuntimeException");
        }
    }

    /**
     * Override the default {@link Throwable#getCause()} to return an {@code Exception}, not just a {@code Throwable}.
     * The cause of an {@code ExException} is guaranteed to be an {@code Exception}.
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
     * @throws E           the cause of this exception, if it is an instance of {@code exceptionClass}.
     * @throws ExException if the cause of this exception is not an instance of {@code exceptionClass}.
     */
    public <E extends Exception> void unwrap(Class<E> exceptionClass) throws E {
        final Exception cause = getCause();
        ExUtils.throwIfInstance(exceptionClass, cause);
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
     * @throws E1          the cause of this exception, if it is an instance of {@code exceptionClass1}.
     * @throws E2          the cause of this exception, if it is an instance of {@code exceptionClass2}.
     * @throws ExException if the cause of this exception is not an instance of {@code exceptionClass1} or {@code exceptionClass2}.
     */
    public <E1 extends Exception, E2 extends Exception> void unwrap(Class<E1> exceptionClass1, Class<E2> exceptionClass2) throws E1, E2 {
        final Exception cause = getCause();
        ExUtils.throwIfInstance(exceptionClass1, cause);
        ExUtils.throwIfInstance(exceptionClass2, cause);
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
     * @throws E1          the cause of this exception, if it is an instance of {@code exceptionClass1}.
     * @throws E2          the cause of this exception, if it is an instance of {@code exceptionClass2}.
     * @throws E3          the cause of this exception, if it is an instance of {@code exceptionClass3}.
     * @throws ExException if the cause of this exception is not an instance of {@code exceptionClass1}, {@code exceptionClass2} or
     *                     {@code exceptionClass3}.
     */
    public <E1 extends Exception, E2 extends Exception, E3 extends Exception> void unwrap(
            Class<E1> exceptionClass1, Class<E2> exceptionClass2, Class<E3> exceptionClass3) throws E1, E2, E3 {
        final Exception cause = getCause();
        ExUtils.throwIfInstance(exceptionClass1, cause);
        ExUtils.throwIfInstance(exceptionClass2, cause);
        ExUtils.throwIfInstance(exceptionClass3, cause);
        throw this;
    }

}
