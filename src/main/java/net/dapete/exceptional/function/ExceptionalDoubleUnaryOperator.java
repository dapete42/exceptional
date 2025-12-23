package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.DoubleUnaryOperator;

@FunctionalInterface
public interface ExceptionalDoubleUnaryOperator<E extends Exception> extends Wrappable<DoubleUnaryOperator> {

    /**
     * Applies this operator to the given operand.
     *
     * @param operand the operand
     * @return the operator result
     * @throws E potentially
     */
    double applyAsDouble(double operand) throws E;

    @Override
    default DoubleUnaryOperator wrap() {
        return ExceptionalWrapper.wrap(this);
    }

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @return a unary operator that always returns its input argument
     */
    static <E extends Exception> ExceptionalDoubleUnaryOperator<E> identity() {
        return t -> t;
    }

}
