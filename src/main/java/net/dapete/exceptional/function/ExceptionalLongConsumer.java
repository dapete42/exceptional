package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.LongConsumer;

/**
 * Equivalent of a {@link java.util.function.LongConsumer} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalLongConsumer<E extends Exception> extends Wrappable<LongConsumer> {

    /**
     * Performs this operation on the given argument.
     *
     * @param value the input argument
     * @throws E potentially
     */
    void accept(long value) throws E;

    @Override
    default LongConsumer wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
