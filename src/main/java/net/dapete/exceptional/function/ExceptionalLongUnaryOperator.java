package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalUtils;

import java.util.function.LongUnaryOperator;

/**
 * Equivalent of a {@link java.util.function.LongUnaryOperator} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalLongUnaryOperator<E extends Exception> extends Wrappable<LongUnaryOperator> {

    /**
     * Applies this operator to the given operand.
     *
     * @param operand the operand
     * @return the operator result
     * @throws E potentially
     */
    long applyAsLong(long operand) throws E;

    @Override
    default LongUnaryOperator wrap() {
        return operand -> ExceptionalUtils.wrapAndGet(() -> applyAsLong(operand));
    }

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @param <E> the type of exception thrown
     * @return a unary operator that always returns its input argument
     */
    static <E extends Exception> ExceptionalLongUnaryOperator<E> identity() {
        return t -> t;
    }

}
