package net.dapete.exceptional.function;

import net.dapete.exceptional.ExWrap;
import org.jspecify.annotations.NonNull;

import java.util.function.LongSupplier;

/**
 * Equivalent of a {@link java.util.function.LongSupplier} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExLongSupplier<E extends Exception> extends Wrappable<LongSupplier> {

    /**
     * Gets a result.
     *
     * @return a result
     * @throws E potentially
     */
    long getAsLong() throws E;

    @Override
    default @NonNull LongSupplier wrap() {
        return () -> ExWrap.wrap(this);
    }

}
