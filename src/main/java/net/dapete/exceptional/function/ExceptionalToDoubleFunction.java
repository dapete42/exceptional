package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.ToDoubleFunction;

@FunctionalInterface
public interface ExceptionalToDoubleFunction<T, E extends Exception> extends Wrappable<ToDoubleFunction<T>> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws E potentially
     */
    double applyAsDouble(T value) throws E;

    @Override
    default ToDoubleFunction<T> wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
