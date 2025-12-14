package net.dapete.exceptional.function;

/**
 * Equivalent of a {@link java.util.function.LongSupplier} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalLongSupplier<E extends Exception> {

    /**
     * Gets a result.
     *
     * @return a result
     * @throws E potentially
     */
    long getAsLong() throws E;

}
