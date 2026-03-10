package net.dapete.exceptional.function;

import net.dapete.exceptional.ExUtils;

import java.util.function.ObjLongConsumer;

/**
 * Equivalent of an {@link java.util.function.ObjLongConsumer} that can throw exceptions.
 *
 * @param <T> the type of the object argument to the operation
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExObjLongConsumer<T, E extends Exception> extends Wrappable<ObjLongConsumer<T>> {

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
        return (t, value) -> ExUtils.wrapAndRun(() -> accept(t, value));
    }

}
