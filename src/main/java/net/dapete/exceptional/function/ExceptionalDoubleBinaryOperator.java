package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalDoubleBinaryOperator<E extends Exception> {

    /**
     * Applies this operator to the given operands.
     *
     * @param left  the first operand
     * @param right the second operand
     * @return the operator result
     */
    double applyAsDouble(double left, double right) throws E;

}
