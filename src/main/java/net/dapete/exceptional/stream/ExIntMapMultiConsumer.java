package net.dapete.exceptional.stream;

import net.dapete.exceptional.ExWrap;
import net.dapete.exceptional.function.ExIntConsumer;
import net.dapete.exceptional.function.Wrappable;

import java.util.stream.IntStream;

/**
 * Equivalent of a {@link java.util.stream.IntStream.IntMapMultiConsumer} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExIntMapMultiConsumer<E extends Exception> extends Wrappable<IntStream.IntMapMultiConsumer> {

    /**
     * Replaces the given {@code value} with zero or more values by feeding the mapped
     * values to the {@code ic} consumer.
     *
     * @param value the int value coming from upstream
     * @param ic    an {@code ExIntConsumer} accepting the mapped values
     * @throws E possibly
     */
    void accept(int value, ExIntConsumer<E> ic) throws E;

    @Override
    default IntStream.IntMapMultiConsumer wrap() {
        return (value, ic) -> ExWrap.wrap(() -> accept(value, ic::accept));
    }

}
