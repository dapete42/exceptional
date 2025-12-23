package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.ToLongBiFunction;

/**
 * Equivalent of a {@link java.util.function.ToLongBiFunction} that can throw exceptions.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalToLongBiFunction<T, U, E extends Exception> extends Wrappable<ToLongBiFunction<T, U>> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     * @throws E potentially
     */
    long applyAsLong(T t, U u) throws E;

    @Override
    default ToLongBiFunction<T, U> wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
