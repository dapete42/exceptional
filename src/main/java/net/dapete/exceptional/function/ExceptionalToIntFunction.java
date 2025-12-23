package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.ToIntFunction;

@FunctionalInterface
public interface ExceptionalToIntFunction<T, E extends Exception> extends Wrappable<ToIntFunction<T>> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws E potentially
     */
    int applyAsInt(T value) throws E;

    @Override
    default ToIntFunction<T> wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
