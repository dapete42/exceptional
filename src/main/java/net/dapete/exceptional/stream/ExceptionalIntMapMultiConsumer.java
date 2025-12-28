package net.dapete.exceptional.stream;

import net.dapete.exceptional.ExceptionalWrapper;
import net.dapete.exceptional.function.ExceptionalIntConsumer;
import net.dapete.exceptional.function.Wrappable;
import org.jspecify.annotations.NonNull;

import java.util.stream.IntStream;

/**
 * Equivalent of a {@link java.util.stream.IntStream.IntMapMultiConsumer} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalIntMapMultiConsumer<E extends Exception> extends Wrappable<IntStream.IntMapMultiConsumer> {

    /**
     * Replaces the given {@code value} with zero or more values by feeding the mapped
     * values to the {@code ic} consumer.
     *
     * @param value the int value coming from upstream
     * @param ic    an {@code ExceptionalIntConsumer} accepting the mapped values
     * @throws E possibly
     */
    void accept(int value, @NonNull ExceptionalIntConsumer<?> ic) throws E;

    @Override
    default IntStream.IntMapMultiConsumer wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
