package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.DoubleBinaryOperator;

@FunctionalInterface
public interface ExceptionalDoubleBinaryOperator<E extends Exception> extends Wrappable<DoubleBinaryOperator> {

    /**
     * Applies this operator to the given operands.
     *
     * @param left  the first operand
     * @param right the second operand
     * @return the operator result
     * @throws E potentially
     */
    double applyAsDouble(double left, double right) throws E;

    @Override
    default DoubleBinaryOperator wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
