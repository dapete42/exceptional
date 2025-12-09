package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalBinaryOperator<T, E extends Exception> extends ExceptionalBiFunction<T, T, T, E> {
}
