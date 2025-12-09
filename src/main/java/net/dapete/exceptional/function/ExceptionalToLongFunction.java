package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalToLongFunction<T, E extends Exception> {

    long applyAsLong(T value) throws E;

}
