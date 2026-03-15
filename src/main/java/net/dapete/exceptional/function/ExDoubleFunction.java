package net.dapete.exceptional.function;

import net.dapete.exceptional.ExWrap;
import org.jspecify.annotations.NonNull;

import java.util.function.DoubleFunction;

/**
 * Equivalent of a {@link java.util.function.DoubleFunction} that can throw exceptions.
 *
 * @param <R> the type of the result of the function
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExDoubleFunction<R, E extends Exception> extends Wrappable<DoubleFunction<R>> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws E potentially
     */
    R apply(double value) throws E;

    @Override
    default @NonNull DoubleFunction<R> wrap() {
        return value -> ExWrap.wrap(() -> apply(value));
    }

}
