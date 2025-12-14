package net.dapete.exceptional.function;

/**
 * Equivalent of an {@link java.util.function.IntSupplier} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalIntSupplier<E extends Exception> {

    /**
     * Gets a result.
     *
     * @return a result
     * @throws E potentially
     */
    int getAsInt() throws E;

}
