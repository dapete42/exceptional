package net.dapete.exceptional.function;

import net.dapete.exceptional.WrappedExceptionalException;

public interface Wrappable<W> {

    /**
     * Wraps this lambda, allowing it to be used in contexts where exceptions are not allowed to be thrown directly.
     * <p>
     * If a checked exception is thrown, a {@link WrappedExceptionalException}, which is a runtime exception, will be thrown instead. This will have the
     * original exception as its {@link Throwable#getCause() cause}.
     *
     * @return a wrapped instance of type {@code W}
     */
    W wrap();

}
