package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalUtils;

import java.util.function.Consumer;

/**
 * Equivalent of a {@link java.util.function.Consumer} that can throw exceptions.
 *
 * @param <T> the type of the first argument to the operation
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalConsumer<T, E extends Exception> extends Wrappable<Consumer<T>> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     * @throws E potentially
     */
    void accept(T t) throws E;

    @Override
    default Consumer<T> wrap() {
        return t -> ExceptionalUtils.wrapAndRun(() -> accept(t));
    }

}
