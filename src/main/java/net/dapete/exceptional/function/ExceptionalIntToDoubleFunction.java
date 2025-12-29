package net.dapete.exceptional.function;

import java.util.function.IntToDoubleFunction;

import static net.dapete.exceptional.ExceptionalUtils.toRuntimeException;

/**
 * Equivalent of an {@link java.util.function.IntToDoubleFunction} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalIntToDoubleFunction<E extends Exception> extends Wrappable<IntToDoubleFunction> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws E potentially
     */
    double applyAsDouble(int value) throws E;

    @Override
    default IntToDoubleFunction wrap() {
        return t -> {
            try {
                return applyAsDouble(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

}
