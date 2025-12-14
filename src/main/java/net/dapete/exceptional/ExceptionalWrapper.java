package net.dapete.exceptional;

import net.dapete.exceptional.function.*;
import org.jspecify.annotations.NullMarked;

import java.util.function.*;

/**
 * Utility class to wrap exceptions thrown in lambas from the {@link net.dapete.exceptional.function} package in runtime exceptions of type
 * {@link WrappedExceptionalException}, or leave them as they are if they are already runtime exceptions.
 */
@NullMarked
public final class ExceptionalWrapper {

    // Utility class with private constructor
    private ExceptionalWrapper() {
    }

    private static RuntimeException toRuntimeException(Exception exception) {
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
    public static <T, U, E extends Exception> BiConsumer<T, U> wrap(ExceptionalBiConsumer<? super T, ? super U, E> biConsumer) {
        return (t, u) -> {
            try {
                biConsumer.accept(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
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
    public static <T, U, R, E extends Exception> BiFunction<T, U, R> wrap(ExceptionalBiFunction<? super T, ? super U, ? extends R, E> biFunction) {
        return (t, u) -> {
            try {
                return biFunction.apply(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, U, E extends Exception> BiPredicate<T, U> wrap(ExceptionalBiPredicate<? super T, ? super U, E> biFunction) {
        return (t, u) -> {
            try {
                return biFunction.test(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, E extends Exception> BinaryOperator<T> wrap(ExceptionalBinaryOperator<T, E> binaryOperator) {
        return (t, u) -> {
            try {
                return binaryOperator.apply(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, E extends Exception> Consumer<T> wrap(ExceptionalConsumer<? super T, E> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <E extends Exception> IntConsumer wrap(ExceptionalIntConsumer<E> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <E extends Exception> LongConsumer wrap(ExceptionalLongConsumer<E> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <E extends Exception> DoubleConsumer wrap(ExceptionalDoubleConsumer<E> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, R, E extends Exception> Function<T, R> wrap(ExceptionalFunction<? super T, ? extends R, E> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, E extends Exception> ToIntFunction<T> wrap(ExceptionalToIntFunction<? super T, E> toIntFunction) {
        return t -> {
            try {
                return toIntFunction.applyAsInt(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, E extends Exception> ToLongFunction<T> wrap(ExceptionalToLongFunction<? super T, E> toLongFunction) {
        return t -> {
            try {
                return toLongFunction.applyAsLong(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, E extends Exception> ToDoubleFunction<T> wrap(ExceptionalToDoubleFunction<? super T, E> toDoubleFunction) {
        return t -> {
            try {
                return toDoubleFunction.applyAsDouble(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, E extends Exception> Predicate<T> wrap(ExceptionalPredicate<? super T, E> predicate) {
        return t -> {
            try {
                return predicate.test(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <E extends Exception> Runnable wrap(ExceptionalRunnable<E> runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, E extends Exception> Supplier<T> wrap(ExceptionalSupplier<T, E> supplier) {
        return () -> {
            try {
                return supplier.get();
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /* Specialized method names for easier use. */

    public static <T, E extends Exception> Consumer<T> wrapConsumer(ExceptionalConsumer<T, E> consumer) {
        return wrap(consumer);
    }

    public static <T, R, E extends Exception> Function<T, R> wrapFunction(ExceptionalFunction<T, R, E> function) {
        return wrap(function);
    }

    public static <E extends Exception> Runnable wrapRunnable(ExceptionalRunnable<E> runnable) {
        return wrap(runnable);
    }

    public static <T, E extends Exception> Supplier<T> wrapSupplier(ExceptionalSupplier<T, E> supplier) {
        return wrap(supplier);
    }

}
