package net.dapete.exceptional.function;

/**
 * Equivalent of a {@link java.util.function.DoubleSupplier} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalDoubleSupplier<E extends Exception> {

    /**
     * Gets a result.
     *
     * @return a result
     * @throws E potentially
     */
    double getAsDouble() throws E;

}
