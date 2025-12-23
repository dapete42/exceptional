package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.IntUnaryOperator;

/**
 * Equivalent of an {@link java.util.function.IntUnaryOperator} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalIntUnaryOperator<E extends Exception> extends Wrappable<IntUnaryOperator> {

    /**
     * Applies this operator to the given operand.
     *
     * @param operand the operand
     * @return the operator result
     * @throws E potentially
     */
    int applyAsInt(int operand) throws E;

    @Override
    default IntUnaryOperator wrap() {
        return ExceptionalWrapper.wrap(this);
    }

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @param <E> the type of exception thrown
     * @return a unary operator that always returns its input argument
     */
    static <E extends Exception> ExceptionalIntUnaryOperator<E> identity() {
        return t -> t;
    }

}
