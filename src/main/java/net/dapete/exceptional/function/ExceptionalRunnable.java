package net.dapete.exceptional.function;

import static net.dapete.exceptional.ExceptionalUtils.toRuntimeException;

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
        return () -> {
            try {
                run();
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

}
