package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalDoubleFunction<R, E extends Exception> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    R apply(double value) throws E;

}
