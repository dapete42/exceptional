package net.dapete.exceptional.function;

import net.dapete.exceptional.ExUtils;
import org.jspecify.annotations.NonNull;

/**
 * Equivalent of a {@link java.lang.Runnable} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExRunnable<E extends Exception> extends Wrappable<Runnable> {

    /**
     * Runs this operation.
     *
     * @throws E potentially
     */
    void run() throws E;

    @Override
    default @NonNull Runnable wrap() {
        return () -> ExUtils.wrap(this);
    }

}
