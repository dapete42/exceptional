package net.dapete.exceptional.function;

import java.util.function.ObjIntConsumer;

import static net.dapete.exceptional.ExceptionalUtils.toRuntimeException;

/**
 * Equivalent of an {@link java.util.function.ObjIntConsumer} that can throw exceptions.
 *
 * @param <T> the type of the object argument to the operation
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalObjIntConsumer<T, E extends Exception> extends Wrappable<ObjIntConsumer<T>> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t     the first input argument
     * @param value the second input argument
     * @throws E potentially
     */
    void accept(T t, int value) throws E;

    @Override
    default ObjIntConsumer<T> wrap() {
        return (t, u) -> {
            try {
                accept(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

}
