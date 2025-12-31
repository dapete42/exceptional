package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalUtils;

import java.util.function.LongPredicate;

/**
 * Equivalent of a {@link java.util.function.LongPredicate} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalLongPredicate<E extends Exception> extends Wrappable<LongPredicate> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param value the input argument
     * @return {@code true} if the input argument matches the predicate, otherwise {@code false}
     * @throws E potentially
     */
    boolean test(long value) throws E;

    @Override
    default LongPredicate wrap() {
        return value -> ExceptionalUtils.wrapAndGet(() -> test(value));
    }

}
