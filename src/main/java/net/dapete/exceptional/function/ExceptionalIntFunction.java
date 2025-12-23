package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.IntFunction;

/**
 * Equivalent of an {@link java.util.function.IntFunction} that can throw exceptions.
 *
 * @param <R> the type of the result of the function
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalIntFunction<R, E extends Exception> extends Wrappable<IntFunction<R>> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws E potentially
     */
    R apply(int value) throws E;

    @Override
    default IntFunction<R> wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
