package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.IntFunction;

@FunctionalInterface
public interface ExceptionalIntFunction<R, E extends Exception> extends Wrappable<IntFunction<R>> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    R apply(int value) throws E;

    @Override
    default IntFunction<R> wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
