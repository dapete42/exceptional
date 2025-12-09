package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalSupplier<T, E extends Exception> {

    T get() throws E;

}
