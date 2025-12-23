package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.ToLongFunction;

/**
 * Equivalent of a {@link java.util.function.ToLongFunction} that can throw exceptions.
 *
 * @param <T> the type of the input to the function
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalToLongFunction<T, E extends Exception> extends Wrappable<ToLongFunction<T>> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws E potentially
     */
    long applyAsLong(T value) throws E;

    @Override
    default ToLongFunction<T> wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
