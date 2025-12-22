package net.dapete.exceptional;

import net.dapete.exceptional.function.*;
import org.jspecify.annotations.NonNull;

import java.util.function.*;

/**
 * Utility class to wrap exceptions thrown in lambas from the {@link net.dapete.exceptional.function} package in runtime exceptions of type
 * {@link WrappedExceptionalException}, or leave them as they are if they are already runtime exceptions.
 */
public final class ExceptionalWrapper {

    // Utility class with private constructor
    private ExceptionalWrapper() {
    }

    public static RuntimeException toRuntimeException(Exception exception) {
        if (exception instanceof RuntimeException runtimeException) {
            return runtimeException;
        } else {
            return new WrappedExceptionalException(exception);
        }
    }

    /**
     * Turns a lambda with exceptions into one that throws a {@link WrappedExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param biConsumer lambda with exceptions
     * @param <T>        the type of the first argument to the operation
     * @param <U>        the type of the second argument to the operation
     * @param <E>        the type of exception thrown by {@code biConsumer}
     * @return a lambda that throws a {@link WrappedExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <T, U, E extends Exception> @NonNull BiConsumer<T, U> wrap(@NonNull ExceptionalBiConsumer<? super T, ? super U, E> biConsumer) {
        return (t, u) -> {
            try {
                biConsumer.accept(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, E extends Exception> @NonNull ObjDoubleConsumer<T> wrap(@NonNull ExceptionalObjDoubleConsumer<? super T, E> biConsumer) {
        return wrap((ExceptionalBiConsumer<T, Double, E>) biConsumer::accept)::accept;
    }

    public static <T, E extends Exception> @NonNull ObjIntConsumer<T> wrap(@NonNull ExceptionalObjIntConsumer<? super T, E> biConsumer) {
        return wrap((ExceptionalBiConsumer<T, Integer, E>) biConsumer::accept)::accept;
    }

    public static <T, E extends Exception> @NonNull ObjLongConsumer<T> wrap(@NonNull ExceptionalObjLongConsumer<? super T, E> biConsumer) {
        return wrap((ExceptionalBiConsumer<T, Long, E>) biConsumer::accept)::accept;
    }

    /**
     * Turns a lambda with exceptions into one that throws a {@link WrappedExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param biFunction lambda with exceptions
     * @param <T>        the type of the first argument to the function
     * @param <U>        the type of the second argument to the function
     * @param <R>        the type of the result of the function
     * @param <E>        the type of exception thrown by {@code biFunction}
     * @return a lambda that throws a {@link WrappedExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <T, U, R, E extends Exception> @NonNull BiFunction<T, U, R> wrap(@NonNull ExceptionalBiFunction<? super T, ? super U, ? extends R, E> biFunction) {
        return (t, u) -> {
            try {
                return biFunction.apply(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, U, E extends Exception> @NonNull ToDoubleBiFunction<T, U> wrap(@NonNull ExceptionalToDoubleBiFunction<T, U, E> biFunction) {
        return wrap((ExceptionalBiFunction<T, U, Double, E>) biFunction::applyAsDouble)::apply;
    }

    public static <T, U, E extends Exception> @NonNull ToIntBiFunction<T, U> wrap(@NonNull ExceptionalToIntBiFunction<T, U, E> biFunction) {
        return wrap((ExceptionalBiFunction<T, U, Integer, E>) biFunction::applyAsInt)::apply;
    }

    public static <T, U, E extends Exception> @NonNull ToLongBiFunction<T, U> wrap(@NonNull ExceptionalToLongBiFunction<T, U, E> biFunction) {
        return wrap((ExceptionalBiFunction<T, U, Long, E>) biFunction::applyAsLong)::apply;
    }

    public static <T, U, E extends Exception> @NonNull BiPredicate<T, U> wrap(@NonNull ExceptionalBiPredicate<? super T, ? super U, E> biFunction) {
        return (t, u) -> {
            try {
                return biFunction.test(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, E extends Exception> @NonNull BinaryOperator<T> wrap(@NonNull ExceptionalBinaryOperator<T, E> binaryOperator) {
        return wrap((ExceptionalBiFunction<T, T, T, E>) binaryOperator)::apply;
    }

    public static <E extends Exception> @NonNull DoubleBinaryOperator wrap(@NonNull ExceptionalDoubleBinaryOperator<E> binaryOperator) {
        return wrap((ExceptionalBiFunction<Double, Double, Double, E>) binaryOperator::applyAsDouble)::apply;
    }

    public static <E extends Exception> @NonNull IntBinaryOperator wrap(@NonNull ExceptionalIntBinaryOperator<E> binaryOperator) {
        return wrap((ExceptionalBiFunction<Integer, Integer, Integer, E>) binaryOperator::applyAsInt)::apply;
    }

    public static <E extends Exception> @NonNull LongBinaryOperator wrap(@NonNull ExceptionalLongBinaryOperator<E> binaryOperator) {
        return wrap((ExceptionalBiFunction<Long, Long, Long, E>) binaryOperator::applyAsLong)::apply;
    }

    public static <T, E extends Exception> @NonNull Consumer<T> wrap(@NonNull ExceptionalConsumer<? super T, E> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <E extends Exception> @NonNull DoubleConsumer wrap(@NonNull ExceptionalDoubleConsumer<E> consumer) {
        return wrap((ExceptionalConsumer<Double, E>) consumer::accept)::accept;
    }

    public static <E extends Exception> @NonNull IntConsumer wrap(@NonNull ExceptionalIntConsumer<E> consumer) {
        return wrap((ExceptionalConsumer<Integer, E>) consumer::accept)::accept;
    }

    public static <E extends Exception> @NonNull LongConsumer wrap(@NonNull ExceptionalLongConsumer<E> consumer) {
        return wrap((ExceptionalConsumer<Long, E>) consumer::accept)::accept;
    }

    public static <T, R, E extends Exception> @NonNull Function<T, R> wrap(@NonNull ExceptionalFunction<? super T, ? extends R, E> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <R, E extends Exception> @NonNull DoubleFunction<R> wrap(@NonNull ExceptionalDoubleFunction<? extends R, E> function) {
        return wrap((ExceptionalFunction<Double, R, E>) function::apply)::apply;
    }

    public static <R, E extends Exception> @NonNull IntFunction<R> wrap(@NonNull ExceptionalIntFunction<? extends R, E> function) {
        return wrap((ExceptionalFunction<Integer, R, E>) function::apply)::apply;
    }

    public static <R, E extends Exception> @NonNull LongFunction<R> wrap(@NonNull ExceptionalLongFunction<? extends R, E> function) {
        return wrap((ExceptionalFunction<Long, R, E>) function::apply)::apply;
    }

    public static <T, E extends Exception> @NonNull ToDoubleFunction<T> wrap(@NonNull ExceptionalToDoubleFunction<? super T, E> function) {
        return wrap((ExceptionalFunction<T, Double, E>) function::applyAsDouble)::apply;
    }

    public static <E extends Exception> @NonNull IntToDoubleFunction wrap(@NonNull ExceptionalIntToDoubleFunction<E> function) {
        return wrap((ExceptionalFunction<Integer, Double, E>) function::applyAsDouble)::apply;
    }

    public static <E extends Exception> @NonNull LongToDoubleFunction wrap(@NonNull ExceptionalLongToDoubleFunction<E> function) {
        return wrap((ExceptionalFunction<Long, Double, E>) function::applyAsDouble)::apply;
    }

    public static <T, E extends Exception> @NonNull ToIntFunction<T> wrap(@NonNull ExceptionalToIntFunction<? super T, E> function) {
        return wrap((ExceptionalFunction<T, Integer, E>) function::applyAsInt)::apply;
    }

    public static <E extends Exception> @NonNull DoubleToIntFunction wrap(@NonNull ExceptionalDoubleToIntFunction<E> function) {
        return wrap((ExceptionalFunction<Double, Integer, E>) function::applyAsInt)::apply;
    }

    public static <E extends Exception> @NonNull LongToIntFunction wrap(@NonNull ExceptionalLongToIntFunction<E> function) {
        return wrap((ExceptionalFunction<Long, Integer, E>) function::applyAsInt)::apply;
    }

    public static <T, E extends Exception> @NonNull ToLongFunction<T> wrap(@NonNull ExceptionalToLongFunction<? super T, E> function) {
        return wrap((ExceptionalFunction<T, Long, E>) function::applyAsLong)::apply;
    }

    public static <E extends Exception> @NonNull DoubleToLongFunction wrap(@NonNull ExceptionalDoubleToLongFunction<E> function) {
        return wrap((ExceptionalFunction<Double, Long, E>) function::applyAsLong)::apply;
    }

    public static <E extends Exception> @NonNull IntToLongFunction wrap(@NonNull ExceptionalIntToLongFunction<E> function) {
        return wrap((ExceptionalFunction<Integer, Long, E>) function::applyAsLong)::apply;
    }

    public static <T, E extends Exception> @NonNull Predicate<T> wrap(@NonNull ExceptionalPredicate<? super T, E> predicate) {
        return t -> {
            try {
                return predicate.test(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <E extends Exception> @NonNull DoublePredicate wrap(@NonNull ExceptionalDoublePredicate<E> predicate) {
        return wrap((ExceptionalPredicate<Double, E>) predicate::test)::test;
    }

    public static <E extends Exception> @NonNull IntPredicate wrap(@NonNull ExceptionalIntPredicate<E> predicate) {
        return wrap((ExceptionalPredicate<Integer, E>) predicate::test)::test;
    }

    public static <E extends Exception> @NonNull LongPredicate wrap(@NonNull ExceptionalLongPredicate<E> predicate) {
        return wrap((ExceptionalPredicate<Long, E>) predicate::test)::test;
    }

    public static <E extends Exception> @NonNull Runnable wrap(@NonNull ExceptionalRunnable<E> runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, E extends Exception> @NonNull Supplier<T> wrap(@NonNull ExceptionalSupplier<T, E> supplier) {
        return () -> {
            try {
                return supplier.get();
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <E extends Exception> @NonNull BooleanSupplier wrap(@NonNull ExceptionalBooleanSupplier<E> supplier) {
        return wrap((ExceptionalSupplier<Boolean, E>) supplier::getAsBoolean)::get;
    }

    public static <E extends Exception> @NonNull DoubleSupplier wrap(@NonNull ExceptionalDoubleSupplier<E> supplier) {
        return wrap((ExceptionalSupplier<Double, E>) supplier::getAsDouble)::get;
    }

    public static <E extends Exception> @NonNull IntSupplier wrap(@NonNull ExceptionalIntSupplier<E> supplier) {
        return wrap((ExceptionalSupplier<Integer, E>) supplier::getAsInt)::get;
    }

    public static <E extends Exception> @NonNull LongSupplier wrap(@NonNull ExceptionalLongSupplier<E> supplier) {
        return wrap((ExceptionalSupplier<Long, E>) supplier::getAsLong)::get;
    }

    public static <T, E extends Exception> @NonNull UnaryOperator<T> wrap(@NonNull ExceptionalUnaryOperator<T, E> unaryOperator) {
        return wrap((ExceptionalFunction<T, T, E>) unaryOperator)::apply;
    }

    public static <E extends Exception> @NonNull DoubleUnaryOperator wrap(@NonNull ExceptionalDoubleUnaryOperator<E> unaryOperator) {
        return wrap((ExceptionalFunction<Double, Double, E>) unaryOperator::applyAsDouble)::apply;
    }

    public static <E extends Exception> @NonNull IntUnaryOperator wrap(@NonNull ExceptionalIntUnaryOperator<E> unaryOperator) {
        return wrap((ExceptionalFunction<Integer, Integer, E>) unaryOperator::applyAsInt)::apply;
    }

    public static <E extends Exception> @NonNull LongUnaryOperator wrap(@NonNull ExceptionalLongUnaryOperator<E> unaryOperator) {
        return wrap((ExceptionalFunction<Long, Long, E>) unaryOperator::applyAsLong)::apply;
    }

    /* Specialized method names for easier use. */

    public static <T, E extends Exception> @NonNull Consumer<T> wrapConsumer(@NonNull ExceptionalConsumer<T, E> consumer) {
        return wrap(consumer);
    }

    public static <T, R, E extends Exception> @NonNull Function<T, R> wrapFunction(@NonNull ExceptionalFunction<T, R, E> function) {
        return wrap(function);
    }

    public static <E extends Exception> @NonNull Runnable wrapRunnable(@NonNull ExceptionalRunnable<E> runnable) {
        return wrap(runnable);
    }

    public static <T, E extends Exception> @NonNull Supplier<T> wrapSupplier(@NonNull ExceptionalSupplier<T, E> supplier) {
        return wrap(supplier);
    }

}
