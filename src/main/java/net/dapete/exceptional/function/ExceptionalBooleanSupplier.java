package net.dapete.exceptional.function;


import java.util.function.BooleanSupplier;

import static net.dapete.exceptional.ExceptionalUtils.toRuntimeException;

/**
 * Equivalent of a {@link java.util.function.BooleanSupplier} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalBooleanSupplier<E extends Exception> extends Wrappable<BooleanSupplier> {

    /**
     * Gets a result.
     *
     * @return a result
     * @throws E potentially
     */
    boolean getAsBoolean() throws E;

    @Override
    default BooleanSupplier wrap() {
        return () -> {
            try {
                return getAsBoolean();
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

}
