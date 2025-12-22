package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalDoubleToLongFunction<E extends Exception> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    long applyAsLong(double value) throws E;

}
