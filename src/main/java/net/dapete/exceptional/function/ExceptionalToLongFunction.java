package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.ToLongFunction;

@FunctionalInterface
public interface ExceptionalToLongFunction<T, E extends Exception> extends Wrappable<ToLongFunction<T>> {

    long applyAsLong(T value) throws E;

    @Override
    default ToLongFunction<T> wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
