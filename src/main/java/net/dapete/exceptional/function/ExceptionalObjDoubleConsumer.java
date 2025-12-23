package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.ObjDoubleConsumer;

@FunctionalInterface
public interface ExceptionalObjDoubleConsumer<T, E extends Exception> extends Wrappable<ObjDoubleConsumer<T>> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t     the first input argument
     * @param value the second input argument
     * @throws E potentially
     */
    void accept(T t, double value) throws E;

    @Override
    default ObjDoubleConsumer<T> wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
