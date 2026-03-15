package net.dapete.exceptional.function;

import net.dapete.exceptional.ExWrap;
import org.jspecify.annotations.NonNull;

import java.util.function.LongToDoubleFunction;

/**
 * Equivalent of a {@link java.util.function.LongToDoubleFunction} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExLongToDoubleFunction<E extends Exception> extends Wrappable<LongToDoubleFunction> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws E potentially
     */
    double applyAsDouble(long value) throws E;

    @Override
    default @NonNull LongToDoubleFunction wrap() {
        return value -> ExWrap.wrap(() -> applyAsDouble(value));
    }

}
