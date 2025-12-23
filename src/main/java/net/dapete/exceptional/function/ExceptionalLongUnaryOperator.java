package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.LongUnaryOperator;

@FunctionalInterface
public interface ExceptionalLongUnaryOperator<E extends Exception> extends Wrappable<LongUnaryOperator> {

    /**
     * Applies this operator to the given operand.
     *
     * @param operand the operand
     * @return the operator result
     */
    long applyAsLong(long operand) throws E;

    @Override
    default LongUnaryOperator wrap() {
        return ExceptionalWrapper.wrap(this);
    }

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @return a unary operator that always returns its input argument
     */
    static <E extends Exception> ExceptionalLongUnaryOperator<E> identity() {
        return t -> t;
    }

}
