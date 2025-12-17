package net.dapete.exceptional.function;

/**
 * Equivalent of a {@link java.util.function.DoublePredicate} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalDoublePredicate<E extends Exception> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param value the input argument
     * @return {@code true} if the input argument matches the predicate, otherwise {@code false}
     * @throws E potentially
     */
    boolean test(double value) throws E;

}
