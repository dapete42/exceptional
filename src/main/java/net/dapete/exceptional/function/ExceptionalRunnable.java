package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalRunnable<E extends Exception> {

    void run() throws E;

}
