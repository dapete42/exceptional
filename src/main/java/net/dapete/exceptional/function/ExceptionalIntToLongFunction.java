package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.IntToLongFunction;

/**
 * Equivalent of an {@link java.util.function.IntToLongFunction} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalIntToLongFunction<E extends Exception> extends Wrappable<IntToLongFunction> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws E potentially
     */
    long applyAsLong(int value) throws E;

    @Override
    default IntToLongFunction wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
