package net.dapete.exceptional.function;

import java.util.function.LongPredicate;

import static net.dapete.exceptional.ExceptionalUtils.toRuntimeException;

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
        return t -> {
            try {
                return test(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

}
