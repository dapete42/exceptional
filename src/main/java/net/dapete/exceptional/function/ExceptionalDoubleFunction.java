package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.DoubleFunction;

@FunctionalInterface
public interface ExceptionalDoubleFunction<R, E extends Exception> extends Wrappable<DoubleFunction<R>> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    R apply(double value) throws E;

    @Override
    default DoubleFunction<R> wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
