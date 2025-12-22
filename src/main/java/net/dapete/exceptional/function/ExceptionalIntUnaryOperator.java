package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalIntUnaryOperator<E extends Exception> {

    /**
     * Applies this operator to the given operand.
     *
     * @param operand the operand
     * @return the operator result
     */
    int applyAsInt(int operand) throws E;

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @return a unary operator that always returns its input argument
     */
    static <E extends Exception> ExceptionalIntUnaryOperator<E> identity() {
        return t -> t;
    }

}
