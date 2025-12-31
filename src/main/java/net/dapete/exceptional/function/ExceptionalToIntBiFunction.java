package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalUtils;

import java.util.function.ToIntBiFunction;

/**
 * Equivalent of a {@link java.util.function.ToIntBiFunction} that can throw exceptions.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalToIntBiFunction<T, U, E extends Exception> extends Wrappable<ToIntBiFunction<T, U>> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     * @throws E potentially
     */
    int applyAsInt(T t, U u) throws E;

    @Override
    default ToIntBiFunction<T, U> wrap() {
        return (t, u) -> ExceptionalUtils.wrapAndGet(() -> applyAsInt(t, u));
    }

}
