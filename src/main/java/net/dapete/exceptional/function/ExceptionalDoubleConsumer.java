package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.DoubleConsumer;

@FunctionalInterface
public interface ExceptionalDoubleConsumer<E extends Exception> extends Wrappable<DoubleConsumer> {

    void accept(double value) throws E;

    @Override
    default DoubleConsumer wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
