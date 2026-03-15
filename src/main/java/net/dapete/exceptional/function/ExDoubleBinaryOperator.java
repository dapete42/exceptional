package net.dapete.exceptional.function;

import net.dapete.exceptional.ExWrap;
import org.jspecify.annotations.NonNull;

import java.util.function.DoubleBinaryOperator;

/**
 * Equivalent of a {@link java.util.function.DoubleBinaryOperator} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExDoubleBinaryOperator<E extends Exception> extends Wrappable<DoubleBinaryOperator> {

    /**
     * Applies this operator to the given operands.
     *
     * @param left  the first operand
     * @param right the second operand
     * @return the operator result
     * @throws E potentially
     */
    double applyAsDouble(double left, double right) throws E;

    @Override
    default @NonNull DoubleBinaryOperator wrap() {
        return (left, right) -> ExWrap.wrap(() -> applyAsDouble(left, right));
    }

}
