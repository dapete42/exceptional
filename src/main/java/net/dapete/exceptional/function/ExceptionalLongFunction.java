package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.LongFunction;

@FunctionalInterface
public interface ExceptionalLongFunction<R, E extends Exception> extends Wrappable<LongFunction<R>> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    R apply(long value) throws E;

    @Override
    default LongFunction<R> wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
