package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalIntToDoubleFunction<E extends Exception> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    double applyAsDouble(int value) throws E;

}
