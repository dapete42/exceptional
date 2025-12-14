package net.dapete.exceptional.function;

/**
 * Equivalent of a {@link java.util.function.Supplier} that can throw exceptions.
 *
 * @param <T> the type of results supplied by this supplier
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalSupplier<T, E extends Exception> {

    /**
     * Gets a result.
     *
     * @return a result
     * @throws E potentially
     */
    T get() throws E;

}
