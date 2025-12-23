package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

/**
 * Equivalent of a {@link java.lang.Runnable} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalRunnable<E extends Exception> extends Wrappable<Runnable> {

    /**
     * Runs this operation.
     *
     * @throws E potentially
     */
    void run() throws E;

    @Override
    default Runnable wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
