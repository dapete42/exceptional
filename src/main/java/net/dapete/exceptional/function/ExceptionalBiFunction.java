package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.BiFunction;

/**
 * Equivalent of a {@link java.util.function.BiFunction} that can throw exceptions.
 *
 * @param <T> the type of the first argument to the operation
 * @param <U> the type of the second argument to the function
 * @param <R> the type of the result of the function
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalBiFunction<T, U, R, E extends Exception> extends Wrappable<BiFunction<T, U, R>> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     * @throws E potentially
     */
    R apply(T t, U u) throws E;

    @Override
    default BiFunction<T, U, R> wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
