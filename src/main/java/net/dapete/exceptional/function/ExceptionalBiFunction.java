package net.dapete.exceptional.function;

/**
 * Equivalent of a {@link java.util.function.BiFunction} that can throw exceptions.
 *
 * @param <T> the type of the first argument to the operation
 * @param <U> the type of the second argument to the function
 * @param <R> the type of the result of the function
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalBiFunction<T, U, R, E extends Exception> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     * @throws E potentially
     */
    R apply(T t, U u) throws E;

}
