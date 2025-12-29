package net.dapete.exceptional.function;

import java.util.function.DoubleSupplier;

import static net.dapete.exceptional.ExceptionalUtils.toRuntimeException;

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
        return () -> {
            try {
                return getAsDouble();
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

}
