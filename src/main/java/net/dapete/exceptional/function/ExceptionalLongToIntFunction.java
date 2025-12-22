package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalLongToIntFunction<E extends Exception> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    int applyAsInt(long value) throws E;

}
