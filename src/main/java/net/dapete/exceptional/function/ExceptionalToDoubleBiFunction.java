package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalToDoubleBiFunction<T, U, E extends Exception> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    double applyAsDouble(T t, U u) throws E;

}
