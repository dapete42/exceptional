package net.dapete.exceptional.function;

import java.util.function.LongConsumer;

import static net.dapete.exceptional.ExceptionalUtils.toRuntimeException;

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
        return t -> {
            try {
                accept(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

}
