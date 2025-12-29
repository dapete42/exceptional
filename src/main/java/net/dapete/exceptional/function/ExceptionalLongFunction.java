package net.dapete.exceptional.function;

import java.util.function.LongFunction;

import static net.dapete.exceptional.ExceptionalUtils.toRuntimeException;

/**
 * Equivalent of a {@link java.util.function.LongFunction} that can throw exceptions.
 *
 * @param <R> the type of the result of the function
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalLongFunction<R, E extends Exception> extends Wrappable<LongFunction<R>> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws E potentially
     */
    R apply(long value) throws E;

    @Override
    default LongFunction<R> wrap() {
        return t -> {
            try {
                return apply(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

}
