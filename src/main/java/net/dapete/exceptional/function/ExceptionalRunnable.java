package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

@FunctionalInterface
public interface ExceptionalRunnable<E extends Exception> extends Wrappable<Runnable> {

    void run() throws E;

    @Override
    default Runnable wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
