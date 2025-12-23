package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.LongToIntFunction;

@FunctionalInterface
public interface ExceptionalLongToIntFunction<E extends Exception> extends Wrappable<LongToIntFunction> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    int applyAsInt(long value) throws E;

    @Override
    default LongToIntFunction wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
