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
        return (t, u) -> {
            try {
                return binaryOperator.apply(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
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

    public static <E extends Exception> @NonNull IntConsumer wrap(@NonNull ExceptionalIntConsumer<E> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <E extends Exception> @NonNull LongConsumer wrap(@NonNull ExceptionalLongConsumer<E> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <E extends Exception> @NonNull DoubleConsumer wrap(@NonNull ExceptionalDoubleConsumer<E> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
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

    public static <T, E extends Exception> @NonNull ToIntFunction<T> wrap(@NonNull ExceptionalToIntFunction<? super T, E> toIntFunction) {
        return t -> {
            try {
                return toIntFunction.applyAsInt(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, E extends Exception> @NonNull ToLongFunction<T> wrap(@NonNull ExceptionalToLongFunction<? super T, E> toLongFunction) {
        return t -> {
            try {
                return toLongFunction.applyAsLong(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, E extends Exception> @NonNull ToDoubleFunction<T> wrap(@NonNull ExceptionalToDoubleFunction<? super T, E> toDoubleFunction) {
        return t -> {
            try {
                return toDoubleFunction.applyAsDouble(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
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

    public static <E extends Exception> @NonNull IntPredicate wrap(@NonNull ExceptionalIntPredicate<E> predicate) {
        return t -> {
            try {
                return predicate.test(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <E extends Exception> @NonNull LongPredicate wrap(@NonNull ExceptionalLongPredicate<E> predicate) {
        return t -> {
            try {
                return predicate.test(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <E extends Exception> @NonNull DoublePredicate wrap(@NonNull ExceptionalDoublePredicate<E> predicate) {
        return t -> {
            try {
                return predicate.test(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
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

    public static <E extends Exception> @NonNull IntSupplier wrap(@NonNull ExceptionalIntSupplier<E> supplier) {
        return () -> {
            try {
                return supplier.getAsInt();
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <E extends Exception> @NonNull LongSupplier wrap(@NonNull ExceptionalLongSupplier<E> supplier) {
        return () -> {
            try {
                return supplier.getAsLong();
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <E extends Exception> @NonNull DoubleSupplier wrap(@NonNull ExceptionalDoubleSupplier<E> supplier) {
        return () -> {
            try {
                return supplier.getAsDouble();
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
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
