package net.dapete.exceptional.function;

import java.util.function.DoublePredicate;

import static net.dapete.exceptional.ExceptionalUtils.toRuntimeException;

/**
 * Equivalent of a {@link java.util.function.DoublePredicate} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalDoublePredicate<E extends Exception> extends Wrappable<DoublePredicate> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param value the input argument
     * @return {@code true} if the input argument matches the predicate, otherwise {@code false}
     * @throws E potentially
     */
    boolean test(double value) throws E;

    @Override
    default DoublePredicate wrap() {
        return t -> {
            try {
                return test(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

}
