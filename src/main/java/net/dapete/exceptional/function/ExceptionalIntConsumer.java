package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalIntConsumer<E extends Exception> {

    void accept(int value) throws E;

}
