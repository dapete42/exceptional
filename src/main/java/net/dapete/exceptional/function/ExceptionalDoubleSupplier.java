package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.DoubleSupplier;

/**
 * Equivalent of a {@link java.util.function.DoubleSupplier} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalDoubleSupplier<E extends Exception> extends Wrappable<DoubleSupplier> {

    /**
     * Gets a result.
     *
     * @return a result
     * @throws E potentially
     */
    double getAsDouble() throws E;

    @Override
    default DoubleSupplier wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
