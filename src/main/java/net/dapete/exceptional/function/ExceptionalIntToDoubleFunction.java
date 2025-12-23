package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.IntToDoubleFunction;

/**
 * Equivalent of an {@link java.util.function.IntToDoubleFunction} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalIntToDoubleFunction<E extends Exception> extends Wrappable<IntToDoubleFunction> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws E potentially
     */
    double applyAsDouble(int value) throws E;

    @Override
    default IntToDoubleFunction wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
