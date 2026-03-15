package net.dapete.exceptional.function;

import net.dapete.exceptional.ExUtils;
import org.jspecify.annotations.NonNull;

import java.util.function.IntUnaryOperator;

/**
 * Equivalent of an {@link java.util.function.IntUnaryOperator} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExIntUnaryOperator<E extends Exception> extends Wrappable<IntUnaryOperator> {

    /**
     * Applies this operator to the given operand.
     *
     * @param operand the operand
     * @return the operator result
     * @throws E potentially
     */
    int applyAsInt(int operand) throws E;

    @Override
    default @NonNull IntUnaryOperator wrap() {
        return operand -> ExUtils.wrap(() -> applyAsInt(operand));
    }

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @param <E> the type of exception thrown
     * @return a unary operator that always returns its input argument
     */
    static <E extends Exception> @NonNull ExIntUnaryOperator<E> identity() {
        return t -> t;
    }

}
