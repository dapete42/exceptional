package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalLongFunction<R, E extends Exception> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    R apply(long value) throws E;

}
