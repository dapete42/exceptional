package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalUtils;

import java.util.function.Predicate;

/**
 * Equivalent of a {@link java.util.function.Predicate} that can throw exceptions.
 *
 * @param <T> the type of the first argument to the predicate
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalPredicate<T, E extends Exception> extends Wrappable<Predicate<T>> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param t the input argument
     * @return {@code true} if the input argument matches the predicate, otherwise {@code false}
     * @throws E potentially
     */
    boolean test(T t) throws E;

    @Override
    default Predicate<T> wrap() {
        return t -> ExceptionalUtils.wrapAndGet(() -> test(t));
    }

}
