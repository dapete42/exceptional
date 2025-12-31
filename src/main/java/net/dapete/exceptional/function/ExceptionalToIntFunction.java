package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalUtils;

import java.util.function.ToIntFunction;

/**
 * Equivalent of a {@link java.util.function.ToIntFunction} that can throw exceptions.
 *
 * @param <T> the type of the input to the function
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalToIntFunction<T, E extends Exception> extends Wrappable<ToIntFunction<T>> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws E potentially
     */
    int applyAsInt(T value) throws E;

    @Override
    default ToIntFunction<T> wrap() {
        return value -> ExceptionalUtils.wrapAndGet(() -> applyAsInt(value));
    }

}
