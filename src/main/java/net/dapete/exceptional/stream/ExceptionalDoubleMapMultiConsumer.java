package net.dapete.exceptional.stream;

import net.dapete.exceptional.ExceptionalWrapper;
import net.dapete.exceptional.function.ExceptionalDoubleConsumer;
import net.dapete.exceptional.function.Wrappable;
import org.jspecify.annotations.NonNull;

import java.util.stream.DoubleStream;

/**
 * Equivalent of a {@link java.util.stream.DoubleStream.DoubleMapMultiConsumer} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalDoubleMapMultiConsumer<E extends Exception> extends Wrappable<DoubleStream.DoubleMapMultiConsumer> {

    /**
     * Replaces the given {@code value} with zero or more values by feeding the mapped
     * values to the {@code ic} consumer.
     *
     * @param value the double value coming from upstream
     * @param ic    an {@code ExceptionalDoubleConsumer} accepting the mapped values
     * @throws E possibly
     */
    void accept(double value, @NonNull ExceptionalDoubleConsumer<?> ic) throws E;

    @Override
    default DoubleStream.DoubleMapMultiConsumer wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
