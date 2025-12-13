package net.dapete.exceptional.function;

/**
 * Equivalent of a {@link java.util.function.BiConsumer} that can throw exceptions.
 *
 * @param <T> the type of the first argument to the operation
 * @param <U> the type of the second argument to the operation
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalBiConsumer<T, U, E extends Exception> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     * @throws E if necessary
     */
    void accept(T t, U u) throws E;

}
