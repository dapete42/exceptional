package net.dapete.exceptional;

import net.dapete.exceptional.function.*;

import java.util.Optional;
import java.util.function.*;
import java.util.stream.*;

import static net.dapete.exceptional.ExceptionalWrapper.wrap;

/**
 * Class similar to {@link Stream} which implements methods analogue to {@link Stream#map(Function)}, {@link Stream#filter(Predicate)} etc. to allow lambdas
 * that throw exceptions. These the functional interface for these are from the {@link net.dapete.exceptional.function} package.
 * <p>
 * If an exception of type {@link E} is thrown by a lambda, a {@link WrappedExceptionalException}, which is a runtime exception, will be thrown instead. This
 * will have the original exception as its {@link Throwable#getCause() cause}.
 * <p>
 * Note that this exception will likely not be thrown when the method is called, but only when a <em>terminal operation</em> like
 * {@link Stream#collect(Collector)} is used on the stream.
 *
 * @param <T> the type of the stream elements
 * @param <E> the type of exceptions thrown
 */
public final class ActiveExceptionalStream<T, E extends Exception> {

    private final ExceptionalStream<T> exceptionalStream;

    ActiveExceptionalStream(ExceptionalStream<T> exceptionalStream) {
        this.exceptionalStream = exceptionalStream;
    }

    /* Implement versions of all methods from Stream that use lambdas, using lambdas with Exceptions instead. */

    /**
     * Equivalent of {@link Stream#filter}.
     * <p>
     * If {@code predicate} throws an exception of type {@link E}, a {@link WrappedExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like
     * {@link Stream#collect(Collector)} is used on the stream.
     *
     * @param predicate see {@link Stream#filter}
     * @return see {@link Stream#filter}
     */
    public ExceptionalStream<T> filter(ExceptionalPredicate<? super T, ? extends E> predicate) {
        return exceptionalStream.filter(wrap(predicate));
    }

    /**
     * Equivalent of {@link Stream#map}.
     * <p>
     * If {@code mapper} throws an exception of type {@link E}, a {@link WrappedExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like
     * {@link Stream#collect(Collector)} is used on the stream.
     *
     * @param mapper see {@link Stream#map}
     * @return see {@link Stream#map}
     */
    public <R> ExceptionalStream<R> map(ExceptionalFunction<? super T, ? extends R, ? extends E> mapper) {
        return exceptionalStream.map(wrap(mapper));
    }

    /**
     * Equivalent of {@link Stream#mapToInt}.
     * <p>
     * If {@code mapper} throws an exception of type {@link E}, a {@link WrappedExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like
     * {@link Stream#collect(Collector)} is used on the stream.
     *
     * @param mapper see {@link Stream#mapToInt}
     * @return see {@link Stream#mapToInt}
     */
    public IntStream mapToInt(ExceptionalToIntFunction<? super T, ? extends E> mapper) {
        return exceptionalStream.mapToInt(wrap(mapper));
    }

    /**
     * Equivalent of {@link Stream#mapToLong}.
     * <p>
     * If {@code mapper} throws an exception of type {@link E}, a {@link WrappedExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like
     * {@link Stream#collect(Collector)} is used on the stream.
     *
     * @param mapper see {@link Stream#mapToLong}
     * @return see {@link Stream#mapToLong}
     */
    public LongStream mapToLong(ExceptionalToLongFunction<? super T, ? extends E> mapper) {
        return exceptionalStream.mapToLong(wrap(mapper));
    }

    /**
     * Equivalent of {@link Stream#mapToDouble}.
     * <p>
     * If {@code mapper} throws an exception of type {@link E}, a {@link WrappedExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like
     * {@link Stream#collect(Collector)} is used on the stream.
     *
     * @param mapper see {@link Stream#mapToDouble}
     * @return see {@link Stream#mapToDouble}
     */
    public DoubleStream mapToDouble(ExceptionalToDoubleFunction<? super T, ? extends E> mapper) {
        return exceptionalStream.mapToDouble(wrap(mapper));
    }

    /**
     * Equivalent of {@link Stream#flatMap}.
     * <p>
     * If {@code mapper} throws an exception of type {@link E}, a {@link WrappedExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like
     * {@link Stream#collect(Collector)} is used on the stream.
     *
     * @param mapper see {@link Stream#flatMap}
     * @return see {@link Stream#flatMap}
     */
    public <R> ExceptionalStream<R> flatMap(ExceptionalFunction<? super T, ? extends Stream<? extends R>, ? extends E> mapper) {
        return exceptionalStream.flatMap(wrap(mapper));
    }

    /**
     * Equivalent of {@link Stream#flatMapToInt}.
     * <p>
     * If {@code mapper} throws an exception of type {@link E}, a {@link WrappedExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like
     * {@link Stream#collect(Collector)} is used on the stream.
     *
     * @param mapper see {@link Stream#flatMapToInt}
     * @return see {@link Stream#flatMapToInt}
     */
    public IntStream flatMapToInt(ExceptionalFunction<? super T, ? extends IntStream, ? extends E> mapper) {
        return exceptionalStream.flatMapToInt(wrap(mapper));
    }

    /**
     * Equivalent of {@link Stream#flatMapToLong}.
     * <p>
     * If {@code mapper} throws an exception of type {@link E}, a {@link WrappedExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like
     * {@link Stream#collect(Collector)} is used on the stream.
     *
     * @param mapper see {@link Stream#flatMapToLong}
     * @return see {@link Stream#flatMapToLong}
     */
    public LongStream flatMapToLong(ExceptionalFunction<? super T, ? extends LongStream, ? extends E> mapper) {
        return exceptionalStream.flatMapToLong(wrap(mapper));
    }

    /**
     * Equivalent of {@link Stream#flatMapToDouble}.
     * <p>
     * If {@code mapper} throws an exception of type {@link E}, a {@link WrappedExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like
     * {@link Stream#collect(Collector)} is used on the stream.
     *
     * @param mapper see {@link Stream#flatMapToDouble}
     * @return see {@link Stream#flatMapToDouble}
     */
    public DoubleStream flatMapToDouble(ExceptionalFunction<? super T, ? extends DoubleStream, ? extends E> mapper) {
        return exceptionalStream.flatMapToDouble(wrap(mapper));
    }

    /**
     * Equivalent of {@link Stream#mapMulti}.
     * <p>
     * If {@code mapper} throws an exception of type {@link E}, a {@link WrappedExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like
     * {@link Stream#collect(Collector)} is used on the stream.
     *
     * @param mapper see {@link Stream#mapMulti}
     * @return see {@link Stream#mapMulti}
     */
    public <R> ExceptionalStream<R> mapMulti(ExceptionalBiConsumer<? super T, ? super Consumer<R>, ? extends E> mapper) {
        return exceptionalStream.mapMulti(wrap(mapper));
    }

    /**
     * Equivalent of {@link Stream#mapMultiToInt}.
     * <p>
     * If {@code mapper} throws an exception of type {@link E}, a {@link WrappedExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like
     * {@link Stream#collect(Collector)} is used on the stream.
     *
     * @param mapper see {@link Stream#mapMultiToInt}
     * @return see {@link Stream#mapMultiToInt}
     */
    public IntStream mapMultiToInt(ExceptionalBiConsumer<? super T, ? super IntConsumer, ? extends E> mapper) {
        return exceptionalStream.mapMultiToInt(wrap(mapper));
    }

    /**
     * Equivalent of {@link Stream#mapMultiToLong}.
     * <p>
     * If {@code mapper} throws an exception of type {@link E}, a {@link WrappedExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like
     * {@link Stream#collect(Collector)} is used on the stream.
     *
     * @param mapper see {@link Stream#mapMultiToLong}
     * @return see {@link Stream#mapMultiToLong}
     */
    public LongStream mapMultiToLong(ExceptionalBiConsumer<? super T, ? super LongConsumer, ? extends E> mapper) {
        return exceptionalStream.mapMultiToLong(wrap(mapper));
    }

    /**
     * Equivalent of {@link Stream#mapMultiToDouble}.
     * <p>
     * If {@code mapper} throws an exception of type {@link E}, a {@link WrappedExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like
     * {@link Stream#collect(Collector)} is used on the stream.
     *
     * @param mapper see {@link Stream#mapMultiToDouble}
     * @return see {@link Stream#mapMultiToDouble}
     */
    public DoubleStream mapMultiToDouble(ExceptionalBiConsumer<? super T, ? super DoubleConsumer, ? extends E> mapper) {
        return exceptionalStream.mapMultiToDouble(wrap(mapper));
    }

    /**
     * Equivalent of {@link Stream#peek}.
     * <p>
     * If {@code action} throws an exception of type {@link E}, a {@link WrappedExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like
     * {@link Stream#collect(Collector)} is used on the stream.
     *
     * @param action see {@link Stream#peek}
     * @return see {@link Stream#peek}
     */
    public ExceptionalStream<T> peek(ExceptionalConsumer<? super T, ? extends E> action) {
        return exceptionalStream.peek(wrap(action));
    }

    /**
     * Equivalent of {@link Stream#takeWhile}.
     * <p>
     * If {@code predicate} throws an exception of type {@link E}, a {@link WrappedExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like
     * {@link Stream#collect(Collector)} is used on the stream.
     *
     * @param predicate see {@link Stream#takeWhile}
     * @return see {@link Stream#takeWhile}
     */
    public ExceptionalStream<T> takeWhile(ExceptionalPredicate<? super T, ? extends E> predicate) {
        return exceptionalStream.takeWhile(wrap(predicate));
    }

    /**
     * Equivalent of {@link Stream#takeWhile}.
     * <p>
     * If {@code predicate} throws an exception of type {@link E}, a {@link WrappedExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like
     * {@link Stream#collect(Collector)} is used on the stream.
     *
     * @param predicate see {@link Stream#takeWhile}
     * @return see {@link Stream#takeWhile}
     */
    public ExceptionalStream<T> dropWhile(ExceptionalPredicate<? super T, ? extends E> predicate) {
        return exceptionalStream.dropWhile(wrap(predicate));
    }

    /**
     * Equivalent of {@link Stream#forEach}.
     * <p>
     * If {@code action} throws an exception of type {@link E}, a {@link WrappedExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like
     * {@link Stream#collect(Collector)} is used on the stream.
     *
     * @param action see {@link Stream#forEach}
     */
    public void forEach(ExceptionalConsumer<? super T, ? extends E> action) {
        exceptionalStream.forEach(wrap(action));
    }

    /**
     * Equivalent of {@link Stream#forEachOrdered}.
     * <p>
     * If {@code action} throws an exception of type {@link E}, a {@link WrappedExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like
     * {@link Stream#collect(Collector)} is used on the stream.
     *
     * @param action see {@link Stream#forEachOrdered}
     */
    public void forEachOrdered(ExceptionalConsumer<? super T, ? extends E> action) {
        exceptionalStream.forEachOrdered(wrap(action));
    }

    /**
     * Equivalent of {@link Stream#reduce(BinaryOperator)}.
     * <p>
     * If {@code accumulator} throws an exception of type {@link E}, a {@link WrappedExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like
     * {@link Stream#collect(Collector)} is used on the stream.
     *
     * @param accumulator see {@link Stream#reduce(BinaryOperator)}
     * @return see {@link Stream#reduce(BinaryOperator)}
     */
    public Optional<T> reduce(ExceptionalBinaryOperator<T, ? extends E> accumulator) {
        return exceptionalStream.reduce(wrap(accumulator));
    }

    /**
     * Equivalent of {@link Stream#reduce(Object, BinaryOperator)}.
     * <p>
     * If {@code accumulator} throws an exception of type {@link E}, a {@link WrappedExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like
     * {@link Stream#collect(Collector)} is used on the stream.
     *
     * @param identity    see {@link Stream#reduce(Object, BiFunction, BinaryOperator)}
     * @param accumulator see {@link Stream#reduce(Object, BinaryOperator)}
     * @return see {@link Stream#reduce(Object, BinaryOperator)}
     */
    public T reduce(T identity, ExceptionalBinaryOperator<T, ? extends E> accumulator) {
        return exceptionalStream.reduce(identity, wrap(accumulator));
    }

    /**
     * Equivalent of {@link Stream#reduce(Object, BiFunction, BinaryOperator)}.
     * <p>
     * If {@code accumulator} or {@code combiner} throw an exception of type {@link E}, a {@link WrappedExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link Throwable#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like
     * {@link Stream#collect(Collector)} is used on the stream.
     *
     * @param identity    see {@link Stream#reduce(Object, BiFunction, BinaryOperator)}
     * @param accumulator see {@link Stream#reduce(Object, BiFunction, BinaryOperator)}
     * @param combiner    see {@link Stream#reduce(Object, BiFunction, BinaryOperator)}
     * @return see {@link Stream#reduce(Object, BiFunction, BinaryOperator)}
     */
    public <U> U reduce(U identity, ExceptionalBiFunction<U, ? super T, U, ? extends E> accumulator, ExceptionalBinaryOperator<U, ? extends E> combiner) {
        return exceptionalStream.reduce(identity, wrap(accumulator), wrap(combiner));
    }

    /**
     * Equivalent of {@link Stream#collect(Supplier, BiConsumer, BiConsumer)}.
     * <p>
     * If {@code supplier}, {@code accumulator} or {@code combiner} throw an exception of type {@link E}, a {@link WrappedExceptionalException} will be thrown
     * instead. This will have the original exception as its {@link Throwable#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like
     * {@link Stream#collect(Collector)} is used on the stream.
     *
     * @param supplier    see {@link Stream#collect(Supplier, BiConsumer, BiConsumer)}
     * @param accumulator see {@link Stream#collect(Supplier, BiConsumer, BiConsumer)}
     * @param combiner    see {@link Stream#collect(Supplier, BiConsumer, BiConsumer)}
     * @return see {@link Stream#collect(Supplier, BiConsumer, BiConsumer)}
     */
    public <R> R collect(ExceptionalSupplier<R, ? extends E> supplier, ExceptionalBiConsumer<R, ? super T, ? extends E> accumulator,
                         ExceptionalBiConsumer<R, R, ? extends E> combiner) {
        return exceptionalStream.collect(wrap(supplier), wrap(accumulator), wrap(combiner));
    }

}
