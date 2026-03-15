package net.dapete.exceptional.function;

import net.dapete.exceptional.ExUtils;
import org.jspecify.annotations.NonNull;

import java.util.function.IntPredicate;

/**
 * Equivalent of a {@link java.util.function.IntPredicate} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExIntPredicate<E extends Exception> extends Wrappable<IntPredicate> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param value the input argument
     * @return {@code true} if the input argument matches the predicate, otherwise {@code false}
     * @throws E potentially
     */
    boolean test(int value) throws E;

    @Override
    default @NonNull IntPredicate wrap() {
        return value -> ExUtils.wrap(() -> test(value));
    }

}
