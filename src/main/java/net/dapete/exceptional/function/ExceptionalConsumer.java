package net.dapete.exceptional.function;

/**
 * Equivalent of a {@link java.util.function.Consumer} that can throw exceptions.
 *
 * @param <T> the type of the first argument to the operation
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalConsumer<T, E extends Exception> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     * @throws E potentially
     */
    void accept(T t) throws E;

}
