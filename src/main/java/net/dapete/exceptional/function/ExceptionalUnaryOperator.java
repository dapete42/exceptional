package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.UnaryOperator;

@FunctionalInterface
public interface ExceptionalUnaryOperator<T, E extends Exception> extends Wrappable<UnaryOperator<T>> {

    /**
     * Applies this operator to the given argument.
     *
     * @param t the operator argument
     * @return the operator result
     * @throws E potentially
     */
    T apply(T t) throws E;

    @Override
    default UnaryOperator<T> wrap() {
        return ExceptionalWrapper.wrap(this);
    }

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @param <T> the type of the input and output of the operator
     * @return a unary operator that always returns its input argument
     */
    static <T, E extends Exception> ExceptionalUnaryOperator<T, E> identity() {
        return t -> t;
    }

}
