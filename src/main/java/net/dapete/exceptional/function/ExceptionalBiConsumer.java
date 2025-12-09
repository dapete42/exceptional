package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalBiConsumer<T, U, E extends Exception> {

    void accept(T t, U u) throws E;

}
