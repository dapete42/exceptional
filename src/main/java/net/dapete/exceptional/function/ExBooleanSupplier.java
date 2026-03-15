package net.dapete.exceptional.function;


import net.dapete.exceptional.ExWrap;
import org.jspecify.annotations.NonNull;

import java.util.function.BooleanSupplier;

/**
 * Equivalent of a {@link java.util.function.BooleanSupplier} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExBooleanSupplier<E extends Exception> extends Wrappable<BooleanSupplier> {

    /**
     * Gets a result.
     *
     * @return a result
     * @throws E potentially
     */
    boolean getAsBoolean() throws E;

    @Override
    default @NonNull BooleanSupplier wrap() {
        return () -> ExWrap.wrap(this);
    }

}
