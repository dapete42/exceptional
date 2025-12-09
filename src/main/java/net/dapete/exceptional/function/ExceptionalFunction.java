package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalFunction<T, R, E extends Exception> {

    R apply(T t) throws E;

}
