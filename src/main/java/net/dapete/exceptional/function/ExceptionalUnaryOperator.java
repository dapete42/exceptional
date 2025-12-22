package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalUnaryOperator<T, E extends Exception> extends ExceptionalFunction<T, T, E> {

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @param <T> the type of the input and output of the operator
     * @return a unary operator that always returns its input argument
     */
    static <T, E extends Exception> ExceptionalUnaryOperator<T, E> identity() {
        return t -> t;
    }

}
