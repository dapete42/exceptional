package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalUtils;

import java.util.function.Supplier;

/**
 * Equivalent of a {@link java.util.function.Supplier} that can throw exceptions.
 *
 * @param <T> the type of results supplied by this supplier
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalSupplier<T, E extends Exception> extends Wrappable<Supplier<T>> {

    /**
     * Gets a result.
     *
     * @return a result
     * @throws E potentially
     */
    T get() throws E;

    @Override
    default Supplier<T> wrap() {
        return () -> ExceptionalUtils.wrapAndGet(this);
    }

}
