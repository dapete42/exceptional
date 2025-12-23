package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.ToIntFunction;

@FunctionalInterface
public interface ExceptionalToIntFunction<T, E extends Exception> extends Wrappable<ToIntFunction<T>> {

    int applyAsInt(T value) throws E;

    @Override
    default ToIntFunction<T> wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
