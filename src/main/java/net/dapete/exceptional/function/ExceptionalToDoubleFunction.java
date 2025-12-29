package net.dapete.exceptional.function;

import java.util.function.ToDoubleFunction;

import static net.dapete.exceptional.ExceptionalUtils.toRuntimeException;

/**
 * Equivalent of a {@link java.util.function.ToDoubleFunction} that can throw exceptions.
 *
 * @param <T> the type of the input to the function
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalToDoubleFunction<T, E extends Exception> extends Wrappable<ToDoubleFunction<T>> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws E potentially
     */
    double applyAsDouble(T value) throws E;

    @Override
    default ToDoubleFunction<T> wrap() {
        return t -> {
            try {
                return applyAsDouble(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

}
