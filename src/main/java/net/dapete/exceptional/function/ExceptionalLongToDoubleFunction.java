package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalLongToDoubleFunction<E extends Exception> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    double applyAsDouble(long value) throws E;

}
