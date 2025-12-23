package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.DoubleConsumer;

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
        return ExceptionalWrapper.wrap(this);
    }

}
