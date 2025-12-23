package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.LongBinaryOperator;

@FunctionalInterface
public interface ExceptionalLongBinaryOperator<E extends Exception> extends Wrappable<LongBinaryOperator> {

    /**
     * Applies this operator to the given operands.
     *
     * @param left  the first operand
     * @param right the second operand
     * @return the operator result
     */
    long applyAsLong(long left, long right) throws E;

    @Override
    default LongBinaryOperator wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
