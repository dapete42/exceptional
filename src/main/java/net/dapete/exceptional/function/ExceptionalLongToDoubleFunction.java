package net.dapete.exceptional.function;

import java.util.function.LongToDoubleFunction;

import static net.dapete.exceptional.ExceptionalUtils.toRuntimeException;

/**
 * Equivalent of a {@link java.util.function.LongToDoubleFunction} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalLongToDoubleFunction<E extends Exception> extends Wrappable<LongToDoubleFunction> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws E potentially
     */
    double applyAsDouble(long value) throws E;

    @Override
    default LongToDoubleFunction wrap() {
        return t -> {
            try {
                return applyAsDouble(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

}
