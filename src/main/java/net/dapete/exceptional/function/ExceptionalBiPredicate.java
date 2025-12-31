package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalUtils;

import java.util.function.BiPredicate;

/**
 * Equivalent of a {@link java.util.function.BiPredicate} that can throw exceptions.
 *
 * @param <T> the type of the first argument to the predicate
 * @param <U> the type of the second argument the predicate
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalBiPredicate<T, U, E extends Exception> extends Wrappable<BiPredicate<T, U>> {

    /**
     * Evaluates this predicate on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     * @return {@code true} if the input arguments match the predicate, otherwise {@code false}
     * @throws E potentially
     */
    boolean test(T t, U u) throws E;

    @Override
    default BiPredicate<T, U> wrap() {
        return (t, u) -> ExceptionalUtils.wrapAndGet(() -> test(t, u));
    }

}
