package net.dapete.exceptional.function;

import java.util.function.UnaryOperator;

import static net.dapete.exceptional.ExceptionalUtils.toRuntimeException;

/**
 * Equivalent of a {@link java.util.function.UnaryOperator} that can throw exceptions.
 *
 * @param <T> the type of the operand and result of the operator
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalUnaryOperator<T, E extends Exception> extends Wrappable<UnaryOperator<T>> {

    /**
     * Applies this operator to the given argument.
     *
     * @param t the operator argument
     * @return the operator result
     * @throws E potentially
     */
    T apply(T t) throws E;

    @Override
    default UnaryOperator<T> wrap() {
        return t -> {
            try {
                return apply(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @param <T> the type of the input and output of the operator
     * @param <E> the type of exception thrown
     * @return a unary operator that always returns its input argument
     */
    static <T, E extends Exception> ExceptionalUnaryOperator<T, E> identity() {
        return t -> t;
    }

}
