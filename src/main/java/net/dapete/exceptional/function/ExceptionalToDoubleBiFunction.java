package net.dapete.exceptional.function;

import net.dapete.exceptional.ExceptionalWrapper;

import java.util.function.ToDoubleBiFunction;

@FunctionalInterface
public interface ExceptionalToDoubleBiFunction<T, U, E extends Exception> extends Wrappable<ToDoubleBiFunction<T, U>> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    double applyAsDouble(T t, U u) throws E;

    @Override
    default ToDoubleBiFunction<T, U> wrap() {
        return ExceptionalWrapper.wrap(this);
    }

}
