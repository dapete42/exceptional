package net.dapete.exceptional.function;

import java.util.function.LongToIntFunction;

import static net.dapete.exceptional.ExceptionalUtils.toRuntimeException;

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
        return t -> {
            try {
                return applyAsInt(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

}
