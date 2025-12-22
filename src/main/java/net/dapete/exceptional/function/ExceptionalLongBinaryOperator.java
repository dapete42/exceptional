package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalLongBinaryOperator<E extends Exception> {

    /**
     * Applies this operator to the given operands.
     *
     * @param left the first operand
     * @param right the second operand
     * @return the operator result
     */
    long applyAsLong(long left, long right) throws E;

}
