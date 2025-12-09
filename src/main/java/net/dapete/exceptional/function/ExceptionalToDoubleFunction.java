package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalToDoubleFunction<T, E extends Exception> {

    double applyAsDouble(T value) throws E;

}
