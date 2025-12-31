package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalUtils;

import java.util.function.IntSupplier;

/**
 * Equivalent of an {@link java.util.function.IntSupplier} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalIntSupplier<E extends Exception> extends Wrappable<IntSupplier> {

    /**
     * Gets a result.
     *
     * @return a result
     * @throws E potentially
     */
    int getAsInt() throws E;

    @Override
    default IntSupplier wrap() {
        return () -> ExceptionalUtils.wrapAndGet(this::getAsInt);
    }

}
