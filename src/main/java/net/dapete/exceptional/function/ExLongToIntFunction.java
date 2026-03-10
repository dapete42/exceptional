package net.dapete.exceptional.function;

import net.dapete.exceptional.ExUtils;

import java.util.function.LongToIntFunction;

/**
 * Equivalent of a {@link java.util.function.LongToIntFunction} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExLongToIntFunction<E extends Exception> extends Wrappable<LongToIntFunction> {

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
        return value -> ExUtils.wrapAndGet(() -> applyAsInt(value));
    }

}
