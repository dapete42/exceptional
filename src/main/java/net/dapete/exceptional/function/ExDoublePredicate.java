package net.dapete.exceptional.function;

import net.dapete.exceptional.ExUtils;
import org.jspecify.annotations.NonNull;

import java.util.function.DoublePredicate;

/**
 * Equivalent of a {@link java.util.function.DoublePredicate} that can throw exceptions.
 *
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExDoublePredicate<E extends Exception> extends Wrappable<DoublePredicate> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param value the input argument
     * @return {@code true} if the input argument matches the predicate, otherwise {@code false}
     * @throws E potentially
     */
    boolean test(double value) throws E;

    @Override
    default @NonNull DoublePredicate wrap() {
        return value -> ExUtils.wrap(() -> test(value));
    }

}
