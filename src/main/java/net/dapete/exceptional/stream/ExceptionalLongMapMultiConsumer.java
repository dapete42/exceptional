package net.dapete.exceptional.stream;

import net.dapete.exceptional.ExceptionalWrapper;
import net.dapete.exceptional.function.ExceptionalLongConsumer;
import net.dapete.exceptional.function.Wrappable;
import org.jspecify.annotations.NonNull;

import java.util.stream.LongStream;

@FunctionalInterface
public interface ExceptionalLongMapMultiConsumer<E extends Exception> extends Wrappable<LongStream.LongMapMultiConsumer> {

    /**
     * Replaces the given {@code value} with zero or more values by feeding the mapped
     * values to the {@code ic} consumer.
     *
     * @param value the long value coming from upstream
     * @param ic    an {@code ExceptionalLongConsumer} accepting the mapped values
     * @throws E possibly
     */
    void accept(long value, @NonNull ExceptionalLongConsumer<?> ic) throws E;

    @Override
    default LongStream.LongMapMultiConsumer wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
