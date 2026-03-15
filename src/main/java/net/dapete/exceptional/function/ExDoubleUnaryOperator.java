package net.dapete.exceptional.function;

import net.dapete.exceptional.ExUtils;

import java.util.function.DoubleUnaryOperator;

/**
 * Equivalent of a {@link java.util.function.DoubleUnaryOperator} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExDoubleUnaryOperator<E extends Exception> extends Wrappable<DoubleUnaryOperator> {

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
        return operand -> ExUtils.wrap(() -> applyAsDouble(operand));
    }

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @param <E> the type of exception thrown
     * @return a unary operator that always returns its input argument
     */
    static <E extends Exception> ExDoubleUnaryOperator<E> identity() {
        return t -> t;
    }

}
