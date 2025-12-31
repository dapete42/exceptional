package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalUtils;

import java.util.function.ObjDoubleConsumer;

/**
 * Equivalent of a {@link java.util.function.ObjDoubleConsumer} that can throw exceptions.
 *
 * @param <T> the type of the object argument to the operation
 * @param <E> the type of exception thrown
 */
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
        return (t, value) -> ExceptionalUtils.wrapAndRun(() -> accept(t, value));
    }

}
