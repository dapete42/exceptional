package net.dapete.exceptional.function;

import java.util.function.IntConsumer;

import static net.dapete.exceptional.ExceptionalUtils.toRuntimeException;

/**
 * Equivalent of an {@link java.util.function.IntConsumer} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalIntConsumer<E extends Exception> extends Wrappable<IntConsumer> {

    /**
     * Performs this operation on the given argument.
     *
     * @param value the input argument
     * @throws E potentially
     */
    void accept(int value) throws E;

    @Override
    default IntConsumer wrap() {
        return t -> {
            try {
                accept(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

}
