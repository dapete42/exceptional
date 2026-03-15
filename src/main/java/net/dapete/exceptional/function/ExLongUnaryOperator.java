package net.dapete.exceptional.function;

import net.dapete.exceptional.ExUtils;
import org.jspecify.annotations.NonNull;

import java.util.function.LongUnaryOperator;

/**
 * Equivalent of a {@link java.util.function.LongUnaryOperator} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExLongUnaryOperator<E extends Exception> extends Wrappable<LongUnaryOperator> {

    /**
     * Applies this operator to the given operand.
     *
     * @param operand the operand
     * @return the operator result
     * @throws E potentially
     */
    long applyAsLong(long operand) throws E;

    @Override
    default @NonNull LongUnaryOperator wrap() {
        return operand -> ExUtils.wrap(() -> applyAsLong(operand));
    }

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @param <E> the type of exception thrown
     * @return a unary operator that always returns its input argument
     */
    static <E extends Exception> @NonNull ExLongUnaryOperator<E> identity() {
        return t -> t;
    }

}
