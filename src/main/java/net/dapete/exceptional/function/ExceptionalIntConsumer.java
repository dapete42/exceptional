package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.IntConsumer;

@FunctionalInterface
public interface ExceptionalIntConsumer<E extends Exception> extends Wrappable<IntConsumer> {

    void accept(int value) throws E;

    @Override
    default IntConsumer wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
