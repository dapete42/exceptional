package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalUtils;

import java.util.function.LongToIntFunction;

/**
 * Equivalent of a {@link java.util.function.LongToIntFunction} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalLongToIntFunction<E extends Exception> extends Wrappable<LongToIntFunction> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws E potentially
     */
    int applyAsInt(long value) throws E;

    @Override
    default LongToIntFunction wrap() {
        return value -> ExceptionalUtils.wrapAndGet(() -> applyAsInt(value));
    }

}
