package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.ToDoubleFunction;

@FunctionalInterface
public interface ExceptionalToDoubleFunction<T, E extends Exception> extends Wrappable<ToDoubleFunction<T>> {

    double applyAsDouble(T value) throws E;

    @Override
    default ToDoubleFunction<T> wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
