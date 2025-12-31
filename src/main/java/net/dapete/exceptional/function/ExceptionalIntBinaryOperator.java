package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalUtils;

import java.util.function.IntBinaryOperator;

/**
 * Equivalent of an {@link java.util.function.IntBinaryOperator} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalIntBinaryOperator<E extends Exception> extends Wrappable<IntBinaryOperator> {

    /**
     * Applies this operator to the given operands.
     *
     * @param left  the first operand
     * @param right the second operand
     * @return the operator result
     * @throws E potentially
     */
    int applyAsInt(int left, int right) throws E;

    @Override
    default IntBinaryOperator wrap() {
        return (left, right) -> ExceptionalUtils.wrapAndGet(() -> applyAsInt(left, right));
    }

}
