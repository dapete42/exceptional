package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalException;

/**
 * Interfaces which extend this can wrap functional interfaces throwing exceptions using the {@link #wrap()} method, allowing them to be used in contexts where
 * exceptions are not allowed to be thrown directly.
 * <p>
 * If a checked exception is thrown, a {@link ExceptionalException}, which is a runtime exception, will be thrown instead.
 * This will have the original exception as its {@link Throwable#getCause() cause}.
 *
 * @param <W> the type the functional interface returned by {@link #wrap()}
 */
public interface Wrappable<W> {

    /**
     * Wraps this functional interface, allowing it to be used in contexts where exceptions are not allowed to be thrown directly.
     * <p>
     * If a checked exception is thrown, a {@link ExceptionalException}, which is a runtime exception, will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     *
     * @return a wrapped instance of type {@code W}
     */
    W wrap();

}
