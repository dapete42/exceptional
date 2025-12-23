package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.Function;

/**
 * Equivalent of a {@link java.util.function.Function} that can throw exceptions.
 *
 * @param <T> the type of the first argument to the operation
 * @param <R> the type of the result of the function
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalFunction<T, R, E extends Exception> extends Wrappable<Function<T, R>> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     * @throws E potentially
     */
    R apply(T t) throws E;

    @Override
    default Function<T, R> wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
