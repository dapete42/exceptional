package net.dapete.exceptional.function;

import java.util.function.DoubleConsumer;

import static net.dapete.exceptional.ExceptionalUtils.toRuntimeException;

/**
 * Equivalent of a {@link java.util.function.DoubleConsumer} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalDoubleConsumer<E extends Exception> extends Wrappable<DoubleConsumer> {

    /**
     * Performs this operation on the given argument.
     *
     * @param value the input argument
     * @throws E potentially
     */
    void accept(double value) throws E;

    @Override
    default DoubleConsumer wrap() {
        return t -> {
            try {
                accept(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

}
