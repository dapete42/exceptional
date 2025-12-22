package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalIntFunction<R, E extends Exception> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    R apply(int value) throws E;

}
