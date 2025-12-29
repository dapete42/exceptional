package net.dapete.exceptional.function;

import java.util.function.LongSupplier;

import static net.dapete.exceptional.ExceptionalUtils.toRuntimeException;

/**
 * Equivalent of a {@link java.util.function.LongSupplier} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalLongSupplier<E extends Exception> extends Wrappable<LongSupplier> {

    /**
     * Gets a result.
     *
     * @return a result
     * @throws E potentially
     */
    long getAsLong() throws E;

    @Override
    default LongSupplier wrap() {
        return () -> {
            try {
                return getAsLong();
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

}
