package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.ObjLongConsumer;

@FunctionalInterface
public interface ExceptionalObjLongConsumer<T, E extends Exception> extends Wrappable<ObjLongConsumer<T>> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t     the first input argument
     * @param value the second input argument
     * @throws E potentially
     */
    void accept(T t, long value) throws E;

    @Override
    default ObjLongConsumer<T> wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
