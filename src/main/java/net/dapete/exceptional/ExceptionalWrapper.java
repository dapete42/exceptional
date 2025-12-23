package net.dapete.exceptional;

import net.dapete.exceptional.function.*;
import org.jspecify.annotations.NonNull;

import java.util.function.*;

/**
 * Utility class to wrap exceptions thrown in functional interfaces from the {@link net.dapete.exceptional.function} package in runtime exceptions of type
 * {@link ExceptionalException}, or leave them as they are if they are already runtime exceptions.
 */
public final class ExceptionalWrapper {

    // Utility class with private constructor
    private ExceptionalWrapper() {
    }

    private static RuntimeException toRuntimeException(Exception exception) {
        if (exception instanceof RuntimeException runtimeException) {
            return runtimeException;
        } else {
            return new ExceptionalException(exception);
        }
    }

    /**
     * Turns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param biConsumer functional interface with exceptions
     * @param <T>        the type of the first argument to the operation
     * @param <U>        the type of the second argument to the operation
     * @param <E>        the type of exception thrown by {@code biConsumer}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <T, U, E extends Exception> @NonNull BiConsumer<T, U> wrap(@NonNull ExceptionalBiConsumer<T, U, E> biConsumer) {
        return (t, u) -> {
            try {
                biConsumer.accept(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param biConsumer functional interface with exceptions
     * @param <T>        the type of the object argument to the operation
     * @param <E>        the type of exception thrown by {@code biConsumer}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <T, E extends Exception> @NonNull ObjDoubleConsumer<T> wrap(@NonNull ExceptionalObjDoubleConsumer<T, E> biConsumer) {
        return (t, u) -> {
            try {
                biConsumer.accept(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param biConsumer functional interface with exceptions
     * @param <T>        the type of the object argument to the operation
     * @param <E>        the type of exception thrown by {@code biConsumer}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <T, E extends Exception> @NonNull ObjIntConsumer<T> wrap(@NonNull ExceptionalObjIntConsumer<T, E> biConsumer) {
        return (t, u) -> {
            try {
                biConsumer.accept(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param biConsumer functional interface with exceptions
     * @param <T>        the type of the object argument to the operation
     * @param <E>        the type of exception thrown by {@code biConsumer}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <T, E extends Exception> @NonNull ObjLongConsumer<T> wrap(@NonNull ExceptionalObjLongConsumer<T, E> biConsumer) {
        return (t, u) -> {
            try {
                biConsumer.accept(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param biFunction functional interface with exceptions
     * @param <T>        the type of the first argument to the function
     * @param <U>        the type of the second argument to the function
     * @param <R>        the type of the result of the function
     * @param <E>        the type of exception thrown by {@code biFunction}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <T, U, R, E extends Exception> @NonNull BiFunction<T, U, R> wrap(
            @NonNull ExceptionalBiFunction<T, U, R, E> biFunction) {
        return (t, u) -> {
            try {
                return biFunction.apply(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param biFunction functional interface with exceptions
     * @param <T>        the type of the first argument to the function
     * @param <U>        the type of the second argument to the function
     * @param <E>        the type of exception thrown by {@code biFunction}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <T, U, E extends Exception> @NonNull ToDoubleBiFunction<T, U> wrap(@NonNull ExceptionalToDoubleBiFunction<T, U, E> biFunction) {
        return (t, u) -> {
            try {
                return biFunction.applyAsDouble(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param biFunction functional interface with exceptions
     * @param <T>        the type of the first argument to the function
     * @param <U>        the type of the second argument to the function
     * @param <E>        the type of exception thrown by {@code biFunction}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <T, U, E extends Exception> @NonNull ToIntBiFunction<T, U> wrap(@NonNull ExceptionalToIntBiFunction<T, U, E> biFunction) {
        return (t, u) -> {
            try {
                return biFunction.applyAsInt(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param biFunction functional interface with exceptions
     * @param <T>        the type of the first argument to the function
     * @param <U>        the type of the second argument to the function
     * @param <E>        the type of exception thrown by {@code biFunction}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <T, U, E extends Exception> @NonNull ToLongBiFunction<T, U> wrap(@NonNull ExceptionalToLongBiFunction<T, U, E> biFunction) {
        return (t, u) -> {
            try {
                return biFunction.applyAsLong(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param biPredicate functional interface with exceptions
     * @param <T>         the type of the first argument to the predicate
     * @param <U>         the type of the second argument to the predicate
     * @param <E>         the type of exception thrown by {@code biPredicate}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <T, U, E extends Exception> @NonNull BiPredicate<T, U> wrap(@NonNull ExceptionalBiPredicate<T, U, E> biPredicate) {
        return (t, u) -> {
            try {
                return biPredicate.test(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param binaryOperator functional interface with exceptions
     * @param <T>            the type of the operands and result of the operator
     * @param <E>            the type of exception thrown by {@code binaryOperator}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <T, E extends Exception> @NonNull BinaryOperator<T> wrap(@NonNull ExceptionalBinaryOperator<T, E> binaryOperator) {
        return (t, u) -> {
            try {
                return binaryOperator.apply(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param binaryOperator functional interface with exceptions
     * @param <E>            the type of exception thrown by {@code binaryOperator}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <E extends Exception> @NonNull DoubleBinaryOperator wrap(@NonNull ExceptionalDoubleBinaryOperator<E> binaryOperator) {
        return (t, u) -> {
            try {
                return binaryOperator.applyAsDouble(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param binaryOperator functional interface with exceptions
     * @param <E>            the type of exception thrown by {@code binaryOperator}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <E extends Exception> @NonNull IntBinaryOperator wrap(@NonNull ExceptionalIntBinaryOperator<E> binaryOperator) {
        return (t, u) -> {
            try {
                return binaryOperator.applyAsInt(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param binaryOperator functional interface with exceptions
     * @param <E>            the type of exception thrown by {@code binaryOperator}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <E extends Exception> @NonNull LongBinaryOperator wrap(@NonNull ExceptionalLongBinaryOperator<E> binaryOperator) {
        return (t, u) -> {
            try {
                return binaryOperator.applyAsLong(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param consumer functional interface with exceptions
     * @param <T>      the type of the input to the operation
     * @param <E>      the type of exception thrown by {@code consumer}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <T, E extends Exception> @NonNull Consumer<T> wrap(@NonNull ExceptionalConsumer<T, E> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param consumer functional interface with exceptions
     * @param <E>      the type of exception thrown by {@code consumer}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <E extends Exception> @NonNull DoubleConsumer wrap(@NonNull ExceptionalDoubleConsumer<E> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param consumer functional interface with exceptions
     * @param <E>      the type of exception thrown by {@code consumer}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <E extends Exception> @NonNull IntConsumer wrap(@NonNull ExceptionalIntConsumer<E> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param consumer functional interface with exceptions
     * @param <E>      the type of exception thrown by {@code consumer}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <E extends Exception> @NonNull LongConsumer wrap(@NonNull ExceptionalLongConsumer<E> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param function functional interface with exceptions
     * @param <T>      the type of the input to the function
     * @param <R>      the type of the result of the function
     * @param <E>      the type of exception thrown by {@code function}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <T, R, E extends Exception> @NonNull Function<T, R> wrap(@NonNull ExceptionalFunction<T, R, E> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param function functional interface with exceptions
     * @param <R>      the type of the result of the function
     * @param <E>      the type of exception thrown by {@code function}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <R, E extends Exception> @NonNull DoubleFunction<R> wrap(@NonNull ExceptionalDoubleFunction<R, E> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param function functional interface with exceptions
     * @param <R>      the type of the result of the function
     * @param <E>      the type of exception thrown by {@code function}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <R, E extends Exception> @NonNull IntFunction<R> wrap(@NonNull ExceptionalIntFunction<R, E> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param function functional interface with exceptions
     * @param <R>      the type of the result of the function
     * @param <E>      the type of exception thrown by {@code function}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <R, E extends Exception> @NonNull LongFunction<R> wrap(@NonNull ExceptionalLongFunction<R, E> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param function functional interface with exceptions
     * @param <T>      the type of the input to the function
     * @param <E>      the type of exception thrown by {@code function}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <T, E extends Exception> @NonNull ToDoubleFunction<T> wrap(@NonNull ExceptionalToDoubleFunction<T, E> function) {
        return t -> {
            try {
                return function.applyAsDouble(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param function functional interface with exceptions
     * @param <E>      the type of exception thrown by {@code function}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <E extends Exception> @NonNull IntToDoubleFunction wrap(@NonNull ExceptionalIntToDoubleFunction<E> function) {
        return t -> {
            try {
                return function.applyAsDouble(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param function functional interface with exceptions
     * @param <E>      the type of exception thrown by {@code function}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <E extends Exception> @NonNull LongToDoubleFunction wrap(@NonNull ExceptionalLongToDoubleFunction<E> function) {
        return t -> {
            try {
                return function.applyAsDouble(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param function functional interface with exceptions
     * @param <T>      the type of the input to the function
     * @param <E>      the type of exception thrown by {@code function}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <T, E extends Exception> @NonNull ToIntFunction<T> wrap(@NonNull ExceptionalToIntFunction<T, E> function) {
        return t -> {
            try {
                return function.applyAsInt(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param function functional interface with exceptions
     * @param <E>      the type of exception thrown by {@code function}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <E extends Exception> @NonNull DoubleToIntFunction wrap(@NonNull ExceptionalDoubleToIntFunction<E> function) {
        return t -> {
            try {
                return function.applyAsInt(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param function functional interface with exceptions
     * @param <E>      the type of exception thrown by {@code function}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <E extends Exception> @NonNull LongToIntFunction wrap(@NonNull ExceptionalLongToIntFunction<E> function) {
        return t -> {
            try {
                return function.applyAsInt(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param function functional interface with exceptions
     * @param <T>      the type of the input to the function
     * @param <E>      the type of exception thrown by {@code function}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <T, E extends Exception> @NonNull ToLongFunction<T> wrap(@NonNull ExceptionalToLongFunction<T, E> function) {
        return t -> {
            try {
                return function.applyAsLong(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param function functional interface with exceptions
     * @param <E>      the type of exception thrown by {@code function}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <E extends Exception> @NonNull DoubleToLongFunction wrap(@NonNull ExceptionalDoubleToLongFunction<E> function) {
        return t -> {
            try {
                return function.applyAsLong(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param function functional interface with exceptions
     * @param <E>      the type of exception thrown by {@code function}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <E extends Exception> @NonNull IntToLongFunction wrap(@NonNull ExceptionalIntToLongFunction<E> function) {
        return t -> {
            try {
                return function.applyAsLong(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param predicate functional interface with exceptions
     * @param <T>       the type of the input to the predicate
     * @param <E>       the type of exception thrown by {@code predicate}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <T, E extends Exception> @NonNull Predicate<T> wrap(@NonNull ExceptionalPredicate<T, E> predicate) {
        return t -> {
            try {
                return predicate.test(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param predicate functional interface with exceptions
     * @param <E>       the type of exception thrown by {@code predicate}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <E extends Exception> @NonNull DoublePredicate wrap(@NonNull ExceptionalDoublePredicate<E> predicate) {
        return t -> {
            try {
                return predicate.test(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param predicate functional interface with exceptions
     * @param <E>       the type of exception thrown by {@code predicate}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <E extends Exception> @NonNull IntPredicate wrap(@NonNull ExceptionalIntPredicate<E> predicate) {
        return t -> {
            try {
                return predicate.test(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param predicate functional interface with exceptions
     * @param <E>       the type of exception thrown by {@code predicate}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <E extends Exception> @NonNull LongPredicate wrap(@NonNull ExceptionalLongPredicate<E> predicate) {
        return t -> {
            try {
                return predicate.test(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param runnable functional interface with exceptions
     * @param <E>      the type of exception thrown by {@code runnable}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <E extends Exception> @NonNull Runnable wrap(@NonNull ExceptionalRunnable<E> runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param supplier functional interface with exceptions
     * @param <T>      the type of results supplied by this supplier
     * @param <E>      the type of exception thrown by {@code supplier}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <T, E extends Exception> @NonNull Supplier<T> wrap(@NonNull ExceptionalSupplier<T, E> supplier) {
        return () -> {
            try {
                return supplier.get();
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param supplier functional interface with exceptions
     * @param <E>      the type of exception thrown by {@code supplier}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <E extends Exception> @NonNull BooleanSupplier wrap(@NonNull ExceptionalBooleanSupplier<E> supplier) {
        return () -> {
            try {
                return supplier.getAsBoolean();
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param supplier functional interface with exceptions
     * @param <E>      the type of exception thrown by {@code supplier}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <E extends Exception> @NonNull DoubleSupplier wrap(@NonNull ExceptionalDoubleSupplier<E> supplier) {
        return () -> {
            try {
                return supplier.getAsDouble();
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param supplier functional interface with exceptions
     * @param <E>      the type of exception thrown by {@code supplier}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <E extends Exception> @NonNull IntSupplier wrap(@NonNull ExceptionalIntSupplier<E> supplier) {
        return () -> {
            try {
                return supplier.getAsInt();
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param supplier functional interface with exceptions
     * @param <E>      the type of exception thrown by {@code supplier}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <E extends Exception> @NonNull LongSupplier wrap(@NonNull ExceptionalLongSupplier<E> supplier) {
        return () -> {
            try {
                return supplier.getAsLong();
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param unaryOperator functional interface with exceptions
     * @param <T>           the type of the operand and result of the operator
     * @param <E>           the type of exception thrown by {@code unaryOperator}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <T, E extends Exception> @NonNull UnaryOperator<T> wrap(@NonNull ExceptionalUnaryOperator<T, E> unaryOperator) {
        return t -> {
            try {
                return unaryOperator.apply(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param unaryOperator functional interface with exceptions
     * @param <E>           the type of exception thrown by {@code unaryOperator}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <E extends Exception> @NonNull DoubleUnaryOperator wrap(@NonNull ExceptionalDoubleUnaryOperator<E> unaryOperator) {
        return t -> {
            try {
                return unaryOperator.applyAsDouble(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param unaryOperator functional interface with exceptions
     * @param <E>           the type of exception thrown by {@code unaryOperator}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <E extends Exception> @NonNull IntUnaryOperator wrap(@NonNull ExceptionalIntUnaryOperator<E> unaryOperator) {
        return t -> {
            try {
                return unaryOperator.applyAsInt(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /**
     * Truns a functional interface with exceptions into one that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown.
     *
     * @param unaryOperator functional interface with exceptions
     * @param <E>           the type of exception thrown by {@code unaryOperator}
     * @return a functional interface that throws a {@link ExceptionalException} if an exception of type {@link E} was thrown
     */
    public static <E extends Exception> @NonNull LongUnaryOperator wrap(@NonNull ExceptionalLongUnaryOperator<E> unaryOperator) {
        return t -> {
            try {
                return unaryOperator.applyAsLong(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

}
