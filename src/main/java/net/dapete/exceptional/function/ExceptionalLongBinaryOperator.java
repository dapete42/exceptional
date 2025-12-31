package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalUtils;

import java.util.function.LongBinaryOperator;

/**
 * Equivalent of a {@link java.util.function.LongBinaryOperator} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalLongBinaryOperator<E extends Exception> extends Wrappable<LongBinaryOperator> {

    /**
     * Applies this operator to the given operands.
     *
     * @param left  the first operand
     * @param right the second operand
     * @return the operator result
     * @throws E potentially
     */
    long applyAsLong(long left, long right) throws E;

    @Override
    default LongBinaryOperator wrap() {
        return (left, right) -> ExceptionalUtils.wrapAndGet(() -> applyAsLong(left, right));
    }

}
