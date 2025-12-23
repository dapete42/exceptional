package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.ToLongBiFunction;

@FunctionalInterface
public interface ExceptionalToLongBiFunction<T, U, E extends Exception> extends Wrappable<ToLongBiFunction<T, U>> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    long applyAsLong(T t, U u) throws E;

    @Override
    default ToLongBiFunction<T, U> wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
