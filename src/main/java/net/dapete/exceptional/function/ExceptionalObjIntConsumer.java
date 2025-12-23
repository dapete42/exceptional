package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.ObjIntConsumer;

@FunctionalInterface
public interface ExceptionalObjIntConsumer<T, E extends Exception> extends Wrappable<ObjIntConsumer<T>> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t     the first input argument
     * @param value the second input argument
     */
    void accept(T t, int value) throws E;

    @Override
    default ObjIntConsumer<T> wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
