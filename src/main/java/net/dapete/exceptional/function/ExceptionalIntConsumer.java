package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.IntConsumer;

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
        return ExceptionalWrapper.wrap(this);
    }

}
