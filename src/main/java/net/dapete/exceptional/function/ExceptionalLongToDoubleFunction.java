package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.LongToDoubleFunction;

@FunctionalInterface
public interface ExceptionalLongToDoubleFunction<E extends Exception> extends Wrappable<LongToDoubleFunction> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    double applyAsDouble(long value) throws E;

    @Override
    default LongToDoubleFunction wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
