package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalDoubleUnaryOperator<E extends Exception> {

    /**
     * Applies this operator to the given operand.
     *
     * @param operand the operand
     * @return the operator result
     */
    double applyAsDouble(double operand) throws E;

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @return a unary operator that always returns its input argument
     */
    static <E extends Exception> ExceptionalDoubleUnaryOperator<E> identity() {
        return t -> t;
    }

}
