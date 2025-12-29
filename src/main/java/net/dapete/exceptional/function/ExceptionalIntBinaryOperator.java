package net.dapete.exceptional.function;

import java.util.function.IntBinaryOperator;

import static net.dapete.exceptional.ExceptionalUtils.toRuntimeException;

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
        return (t, u) -> {
            try {
                return applyAsInt(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

}
