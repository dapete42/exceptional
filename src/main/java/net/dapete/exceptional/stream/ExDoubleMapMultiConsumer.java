package net.dapete.exceptional.stream;

import net.dapete.exceptional.ExUtils;
import net.dapete.exceptional.function.ExDoubleConsumer;
import net.dapete.exceptional.function.Wrappable;

import java.util.stream.DoubleStream;

/**
 * Equivalent of a {@link java.util.stream.DoubleStream.DoubleMapMultiConsumer} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExDoubleMapMultiConsumer<E extends Exception> extends Wrappable<DoubleStream.DoubleMapMultiConsumer> {

    /**
     * Replaces the given {@code value} with zero or more values by feeding the mapped
     * values to the {@code ic} consumer.
     *
     * @param value the double value coming from upstream
     * @param ic    an {@code ExDoubleConsumer} accepting the mapped values
     * @throws E possibly
     */
    void accept(double value, ExDoubleConsumer<E> ic) throws E;

    @Override
    default DoubleStream.DoubleMapMultiConsumer wrap() {
        return (value, ic) -> ExUtils.wrap(() -> accept(value, ic::accept));
    }

}
