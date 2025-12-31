package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalUtils;

import java.util.function.BiConsumer;

/**
 * Equivalent of a {@link java.util.function.BiConsumer} that can throw exceptions.
 *
 * @param <T> the type of the first argument to the operation
 * @param <U> the type of the second argument to the operation
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalBiConsumer<T, U, E extends Exception> extends Wrappable<BiConsumer<T, U>> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     * @throws E potentially
     */
    void accept(T t, U u) throws E;

    @Override
    default BiConsumer<T, U> wrap() {
        return (t, u) -> ExceptionalUtils.wrapAndRun(() -> accept(t, u));
    }

}
