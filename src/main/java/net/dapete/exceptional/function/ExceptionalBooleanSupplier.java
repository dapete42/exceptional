package net.dapete.exceptional.function;


/**
 * Equivalent of a {@link java.util.function.BooleanSupplier} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalBooleanSupplier<E extends Exception> {

    /**
     * Gets a result.
     *
     * @return a result
     * @throws E potentially
     */
    boolean getAsBoolean() throws E;

}
