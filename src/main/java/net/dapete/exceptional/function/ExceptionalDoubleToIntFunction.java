package net.dapete.exceptional.function;

import java.util.function.DoubleToIntFunction;

import static net.dapete.exceptional.ExceptionalUtils.toRuntimeException;

/**
 * Equivalent of a {@link java.util.function.DoubleToIntFunction} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalDoubleToIntFunction<E extends Exception> extends Wrappable<DoubleToIntFunction> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws E potentially
     */
    int applyAsInt(double value) throws E;

    @Override
    default DoubleToIntFunction wrap() {
        return t -> {
            try {
                return applyAsInt(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

}
