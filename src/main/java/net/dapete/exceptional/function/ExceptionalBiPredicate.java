package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalBiPredicate<T, U, E extends Exception> {

    boolean test(T t, U u) throws E;

}
