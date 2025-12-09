package net.dapete.exceptional.function;

@FunctionalInterface
public interface ExceptionalPredicate<T, E extends Exception> {

    boolean test(T t) throws E;

}
