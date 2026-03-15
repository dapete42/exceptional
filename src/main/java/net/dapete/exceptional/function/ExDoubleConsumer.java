package net.dapete.exceptional.function;

import net.dapete.exceptional.ExWrap;
import org.jspecify.annotations.NonNull;

import java.util.function.DoubleConsumer;

/**
 * Equivalent of a {@link java.util.function.DoubleConsumer} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExDoubleConsumer<E extends Exception> extends Wrappable<DoubleConsumer> {

    /**
     * Performs this operation on the given argument.
     *
     * @param value the input argument
     * @throws E potentially
     */
    void accept(double value) throws E;

    @Override
    default @NonNull DoubleConsumer wrap() {
        return value -> ExWrap.wrap(() -> accept(value));
    }

}
