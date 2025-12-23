package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.ToDoubleBiFunction;

/**
 * Equivalent of a {@link java.util.function.ToDoubleBiFunction} that can throw exceptions.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <E> the type of exception thrown
 */
@FunctionalInterface
public interface ExceptionalToDoubleBiFunction<T, U, E extends Exception> extends Wrappable<ToDoubleBiFunction<T, U>> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     * @throws E potentially
     */
    double applyAsDouble(T t, U u) throws E;

    @Override
    default ToDoubleBiFunction<T, U> wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
