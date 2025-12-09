package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalToIntFunction<T, E extends Exception> {

    int applyAsInt(T value) throws E;

}
