package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalBiFunction<T, U, R, E extends Exception> {

    R apply(T t, U u) throws E;

}
