package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.LongConsumer;

@FunctionalInterface
public interface ExceptionalLongConsumer<E extends Exception> extends Wrappable<LongConsumer> {

    void accept(long value) throws E;

    @Override
    default LongConsumer wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
