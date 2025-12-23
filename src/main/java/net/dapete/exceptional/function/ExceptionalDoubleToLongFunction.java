package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.DoubleToLongFunction;

/**
 * Equivalent of a {@link java.util.function.DoubleToLongFunction} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalDoubleToLongFunction<E extends Exception> extends Wrappable<DoubleToLongFunction> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws E potentially
     */
    long applyAsLong(double value) throws E;

    @Override
    default DoubleToLongFunction wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
