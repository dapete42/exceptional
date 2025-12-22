package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalObjIntConsumer<T, E extends Exception> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param value the second input argument
     */
    void accept(T t, int value) throws E;

}
