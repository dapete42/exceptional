package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.DoubleToIntFunction;

@FunctionalInterface
public interface ExceptionalDoubleToIntFunction<E extends Exception> extends Wrappable<DoubleToIntFunction> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws E potentially
     */
    int applyAsInt(double value) throws E;

    @Override
    default DoubleToIntFunction wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
