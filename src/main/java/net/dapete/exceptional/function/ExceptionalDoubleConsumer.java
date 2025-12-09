package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalDoubleConsumer<E extends Exception> {

    void accept(double value) throws E;

}
