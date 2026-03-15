package net.dapete.exceptional.stream;

import net.dapete.exceptional.ExWrap;
import net.dapete.exceptional.function.ExLongConsumer;
import net.dapete.exceptional.function.Wrappable;

import java.util.stream.LongStream;

/**
 * Equivalent of a {@link java.util.stream.LongStream.LongMapMultiConsumer} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExLongMapMultiConsumer<E extends Exception> extends Wrappable<LongStream.LongMapMultiConsumer> {

    /**
     * Replaces the given {@code value} with zero or more values by feeding the mapped
     * values to the {@code ic} consumer.
     *
     * @param value the long value coming from upstream
     * @param ic    an {@code ExLongConsumer} accepting the mapped values
     * @throws E possibly
     */
    void accept(long value, ExLongConsumer<E> ic) throws E;

    @Override
    default LongStream.LongMapMultiConsumer wrap() {
        return (value, ic) -> ExWrap.wrap(() -> accept(value, ic::accept));
    }

}
