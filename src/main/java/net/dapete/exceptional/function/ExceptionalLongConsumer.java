package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalLongConsumer<E extends Exception> {

    void accept(long value) throws E;

}
