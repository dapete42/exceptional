package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.ToIntBiFunction;

@FunctionalInterface
public interface ExceptionalToIntBiFunction<T, U, E extends Exception> extends Wrappable<ToIntBiFunction<T, U>> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     * @throws E potentially
     */
    int applyAsInt(T t, U u) throws E;

    @Override
    default ToIntBiFunction<T, U> wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
