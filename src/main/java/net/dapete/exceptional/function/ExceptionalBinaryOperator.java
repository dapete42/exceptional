package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.BinaryOperator;

@FunctionalInterface
public interface ExceptionalBinaryOperator<T, E extends Exception> extends Wrappable<BinaryOperator<T>> {

    /**
     * Applies this operator to the given arguments.
     *
     * @param t1 the first operator argument
     * @param t2 the second operator argument
     * @return the function result
     */
    T apply(T t1, T t2) throws E;

    @Override
    default BinaryOperator<T> wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
