package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.BinaryOperator;

/**
 * Equivalent of a {@link java.util.function.BinaryOperator} that can throw exceptions.
 *
 * @param <T> the type of the operands and result of the operator
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalBinaryOperator<T, E extends Exception> extends Wrappable<BinaryOperator<T>> {

    /**
     * Applies this operator to the given arguments.
     *
     * @param t1 the first operator argument
     * @param t2 the second operator argument
     * @return the function result
     * @throws E potentially
     */
    T apply(T t1, T t2) throws E;

    @Override
    default BinaryOperator<T> wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
