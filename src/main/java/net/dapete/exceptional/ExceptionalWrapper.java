package net.dapete.exceptional;

import net.dapete.exceptional.function.*;
import org.jspecify.annotations.NonNull;

import java.util.function.*;

public final class ExceptionalWrapper {

    private ExceptionalWrapper() {
    }

    private static RuntimeException toRuntimeException(Exception exception) {
        if (exception instanceof RuntimeException runtimeException) {
            return runtimeException;
        } else {
            return new WrappedExceptionalException(exception);
        }
    }

    public static <T, U, E extends Exception> @NonNull BiConsumer<T, U> wrap(ExceptionalBiConsumer<? super T, ? super U, E> biConsumer) {
        return (t, u) -> {
            try {
                biConsumer.accept(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, U, R, E extends Exception> @NonNull BiFunction<T, U, R> wrap(ExceptionalBiFunction<? super T, ? super U, ? extends R, E> biFunction) {
        return (t, u) -> {
            try {
                return biFunction.apply(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, U, E extends Exception> @NonNull BiPredicate<T, U> wrap(ExceptionalBiPredicate<? super T, ? super U, E> biFunction) {
        return (t, u) -> {
            try {
                return biFunction.test(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, E extends Exception> @NonNull BinaryOperator<T> wrap(ExceptionalBinaryOperator<T, E> binaryOperator) {
        return (t, u) -> {
            try {
                return binaryOperator.apply(t, u);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, E extends Exception> @NonNull Consumer<T> wrap(ExceptionalConsumer<? super T, E> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, E extends Exception> @NonNull IntConsumer wrap(ExceptionalIntConsumer<E> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, E extends Exception> @NonNull LongConsumer wrap(ExceptionalLongConsumer<E> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, E extends Exception> @NonNull DoubleConsumer wrap(ExceptionalDoubleConsumer<E> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, R, E extends Exception> @NonNull Function<T, R> wrap(ExceptionalFunction<? super T, ? extends R, E> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, E extends Exception> @NonNull ToIntFunction<T> wrap(ExceptionalToIntFunction<? super T, E> toIntFunction) {
        return t -> {
            try {
                return toIntFunction.applyAsInt(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, E extends Exception> @NonNull ToLongFunction<T> wrap(ExceptionalToLongFunction<? super T, E> toLongFunction) {
        return t -> {
            try {
                return toLongFunction.applyAsLong(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, E extends Exception> @NonNull ToDoubleFunction<T> wrap(ExceptionalToDoubleFunction<? super T, E> toDoubleFunction) {
        return t -> {
            try {
                return toDoubleFunction.applyAsDouble(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, E extends Exception> @NonNull Predicate<T> wrap(ExceptionalPredicate<? super T, E> predicate) {
        return t -> {
            try {
                return predicate.test(t);
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <E extends Exception> @NonNull Runnable wrap(ExceptionalRunnable runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    public static <T, E extends Exception> @NonNull Supplier<T> wrap(ExceptionalSupplier<T, E> supplier) {
        return () -> {
            try {
                return supplier.get();
            } catch (Exception e) {
                throw toRuntimeException(e);
            }
        };
    }

    /* Specialized method names for easier use. */

    public static <T, E extends Exception> @NonNull Consumer<T> wrapConsumer(ExceptionalConsumer<T, E> consumer) {
        return wrap(consumer);
    }

    public static <T, R, E extends Exception> @NonNull Function<T, R> wrapFunction(ExceptionalFunction<T, R, E> function) {
        return wrap(function);
    }

    public static <E extends Exception> @NonNull Runnable wrapRunnable(ExceptionalRunnable runnable) {
        return wrap(runnable);
    }

    public static <T, E extends Exception> @NonNull Supplier<T> wrapSupplier(ExceptionalSupplier<T, E> supplier) {
        return wrap(supplier);
    }

}
