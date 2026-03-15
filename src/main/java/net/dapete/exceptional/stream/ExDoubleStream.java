package net.dapete.exceptional.stream;

import lombok.experimental.Delegate;
import net.dapete.exceptional.ExException;
import net.dapete.exceptional.function.*;

import java.util.OptionalDouble;
import java.util.function.*;
import java.util.stream.DoubleStream;

/**
 * A DoubleStream with additional functionality for functional interfaces that throw Exceptions.
 * <p>
 * Implements versions of all methods from DoubleStream that use functional interfaces, using their counterparts with Exceptions instead, e.g.
 * {@link #map(Class, ExDoubleUnaryOperator)} in parallel to {@link #map(DoubleUnaryOperator)}.
 * <p>
 * If these functional interfaces throw a checked exception, a {@link ExException} will be thrown instead.
 * This will have the original exception as its {@link ExException#getCause() cause}.
 * <p>
 * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
 * <em>stateful intermediate operation</em> is used on the stream (except if it is itself such an operation).
 */
public class ExDoubleStream implements DoubleStream {

    @Delegate
    private final DoubleStream stream;

    private ExDoubleStream(DoubleStream stream) {
        this.stream = stream;
    }

    /**
     * Create an instance from an existing {@code DoubleStream}.
     *
     * @param stream existing stream
     * @return instance from an existing {@code DoubleStream}
     */
    public static ExDoubleStream of(DoubleStream stream) {
        return new ExDoubleStream(stream);
    }

    /**
     * Returns an empty instance.
     *
     * @return an empty instance
     */
    public static ExDoubleStream empty() {
        return of(DoubleStream.empty());
    }

    /**
     * Returns an instance containing a single element.
     *
     * @param t the single element
     * @return an instance containing a single element
     */
    public static ExDoubleStream of(double t) {
        return of(DoubleStream.of(t));
    }

    /**
     * Returns an instance whose elements are the specified values.
     *
     * @param values the elements of the new stream
     * @return the new stream
     */
    public static ExDoubleStream of(double... values) {
        return of(DoubleStream.of(values));
    }

    /* Override all methods that usually return Stream to return an ExStream */

    @Override
    public <U> ExStream<U> mapToObj(DoubleFunction<? extends U> mapper) {
        return ExStream.of(stream.mapToObj(mapper));
    }

    @Override
    public ExStream<Double> boxed() {
        return ExStream.of(stream.boxed());
    }

    /* Override all methods that usually return DoubleStream to return an ExDoubleStream. */

    @Override
    public ExDoubleStream filter(DoublePredicate predicate) {
        return of(stream.filter(predicate));
    }

    @Override
    public ExDoubleStream map(DoubleUnaryOperator mapper) {
        return of(stream.map(mapper));
    }

    @Override
    public ExDoubleStream flatMap(DoubleFunction<? extends DoubleStream> mapper) {
        return of(stream.flatMap(mapper));
    }

    @Override
    public ExDoubleStream distinct() {
        return of(stream.distinct());
    }

    @Override
    public ExDoubleStream sorted() {
        return of(stream.sorted());
    }

    @Override
    public ExDoubleStream peek(DoubleConsumer action) {
        return of(stream.peek(action));
    }

    @Override
    public ExDoubleStream limit(long maxSize) {
        return of(stream.limit(maxSize));
    }

    @Override
    public ExDoubleStream skip(long n) {
        return of(stream.skip(n));
    }

    @Override
    public ExDoubleStream sequential() {
        return of(stream.sequential());
    }

    @Override
    public ExDoubleStream parallel() {
        return of(stream.parallel());
    }

    @Override
    public ExDoubleStream unordered() {
        return of(stream.unordered());
    }

    @Override
    public ExDoubleStream onClose(Runnable closeHandler) {
        return of(stream.onClose(closeHandler));
    }

    /* Override all methods that usually return DoubleStream to return an ExDoubleStream. */

    @Override
    public ExIntStream mapToInt(DoubleToIntFunction mapper) {
        return ExIntStream.of(stream.mapToInt(mapper));
    }

    /* Override all methods that usually return LongStream to return an ExLongStream. */

    @Override
    public ExLongStream mapToLong(DoubleToLongFunction mapper) {
        return ExLongStream.of(stream.mapToLong(mapper));
    }

    /* Implement versions of all methods from DoubleStream that use functional Doubleerfaces, using their counterparts with Exceptions instead. */

    /**
     * Equivalent of {@link DoubleStream#filter}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param predicate      see {@link DoubleStream#filter}
     * @return see {@link DoubleStream#filter}
     */
    public <E extends Exception> ExDoubleStream filter(@SuppressWarnings("unused") Class<E> exceptionClass, ExDoublePredicate<? extends E> predicate) {
        return of(filter(predicate.wrap()));
    }

    /**
     * Equivalent of {@link DoubleStream#map}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link DoubleStream#map}
     * @return see {@link DoubleStream#map}
     */
    public <E extends Exception> ExDoubleStream map(@SuppressWarnings("unused") Class<E> exceptionClass, ExDoubleUnaryOperator<? extends E> mapper) {
        return of(map(mapper.wrap()));
    }

    /**
     * Equivalent of {@link DoubleStream#mapToObj}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <U>            the element type of the new stream
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link DoubleStream#mapToObj}
     * @return see {@link DoubleStream#mapToObj}
     */
    public <U, E extends Exception> ExStream<U> mapToObj(@SuppressWarnings("unused") Class<E> exceptionClass, ExDoubleFunction<? extends U, ? extends E> mapper) {
        return ExStream.of(mapToObj(mapper.wrap()));
    }

    /**
     * Equivalent of {@link DoubleStream#mapToInt}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link DoubleStream#mapToInt}
     * @return see {@link DoubleStream#mapToInt}
     */
    public <E extends Exception> ExIntStream mapToInt(@SuppressWarnings("unused") Class<E> exceptionClass, ExDoubleToIntFunction<? extends E> mapper) {
        return ExIntStream.of(mapToInt(mapper.wrap()));
    }

    /**
     * Equivalent of {@link DoubleStream#mapToLong}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link DoubleStream#mapToLong}
     * @return see {@link DoubleStream#mapToLong}
     */
    public <E extends Exception> ExLongStream mapToLong(@SuppressWarnings("unused") Class<E> exceptionClass, ExDoubleToLongFunction<? extends E> mapper) {
        return ExLongStream.of(mapToLong(mapper.wrap()));
    }

    /**
     * Equivalent of {@link DoubleStream#flatMap}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link DoubleStream#flatMap}
     * @return see {@link DoubleStream#flatMap}
     */
    public <E extends Exception> ExDoubleStream flatMap(@SuppressWarnings("unused") Class<E> exceptionClass,
                                                        ExDoubleFunction<? extends DoubleStream, ? extends E> mapper) {
        return of(flatMap(mapper.wrap()));
    }

    /**
     * Equivalent of {@link DoubleStream#mapMulti}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link DoubleStream#mapMulti}
     * @return see {@link DoubleStream#mapMulti}
     */
    public <E extends Exception> ExDoubleStream mapMulti(@SuppressWarnings("unused") Class<E> exceptionClass, ExDoubleMapMultiConsumer<? extends E> mapper) {
        return of(mapMulti(mapper.wrap()));
    }

    /**
     * Equivalent of {@link DoubleStream#peek}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code action}
     * @param exceptionClass The exception class for {@link E}
     * @param action         see {@link DoubleStream#peek}
     * @return see {@link DoubleStream#peek}
     */
    public <E extends Exception> ExDoubleStream peek(@SuppressWarnings("unused") Class<E> exceptionClass, ExDoubleConsumer<? extends E> action) {
        return of(peek(action.wrap()));
    }

    /**
     * Equivalent of {@link DoubleStream#takeWhile}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code predicate}
     * @param exceptionClass The exception class for {@link E}
     * @param predicate      see {@link DoubleStream#takeWhile}
     * @return see {@link DoubleStream#takeWhile}
     */
    public <E extends Exception> ExDoubleStream takeWhile(@SuppressWarnings("unused") Class<E> exceptionClass, ExDoublePredicate<? extends E> predicate) {
        return of(takeWhile(predicate.wrap()));
    }

    /**
     * Equivalent of {@link DoubleStream#dropWhile}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param predicate see {@link DoubleStream#dropWhile}
     * @return see {@link DoubleStream#dropWhile}
     */
    public <E extends Exception> ExDoubleStream dropWhile(@SuppressWarnings("unused") Class<E> exceptionClass, ExDoublePredicate<? extends E> predicate) {
        return of(dropWhile(predicate.wrap()));
    }

    /**
     * Equivalent of {@link DoubleStream#forEach}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code action}
     * @param exceptionClass The exception class for {@link E}
     * @param action         see {@link DoubleStream#forEach}
     */
    public <E extends Exception> void forEach(@SuppressWarnings("unused") Class<E> exceptionClass, ExDoubleConsumer<? extends E> action) {
        forEach(action.wrap());
    }

    /**
     * Equivalent of {@link DoubleStream#forEachOrdered}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code action}
     * @param exceptionClass The exception class for {@link E}
     * @param action         see {@link DoubleStream#forEachOrdered}
     */
    public <E extends Exception> void forEachOrdered(@SuppressWarnings("unused") Class<E> exceptionClass, ExDoubleConsumer<? extends E> action) {
        forEachOrdered(action.wrap());
    }

    /**
     * Equivalent of {@link DoubleStream#reduce(double, DoubleBinaryOperator)}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code op}
     * @param exceptionClass The exception class for {@link E}
     * @param identity       see {@link DoubleStream#reduce(double, DoubleBinaryOperator)}
     * @param op             see {@link DoubleStream#reduce(double, DoubleBinaryOperator)}
     * @return see {@link DoubleStream#reduce(double, DoubleBinaryOperator)}
     */
    public <E extends Exception> double reduce(@SuppressWarnings("unused") Class<E> exceptionClass, double identity, ExDoubleBinaryOperator<? extends E> op) {
        return reduce(identity, op.wrap());
    }

    /**
     * Equivalent of {@link DoubleStream#reduce(DoubleBinaryOperator)}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code op}
     * @param exceptionClass The exception class for {@link E}
     * @param op             see {@link DoubleStream#reduce(DoubleBinaryOperator)}
     * @return see {@link DoubleStream#reduce(DoubleBinaryOperator)}
     */
    public <E extends Exception> OptionalDouble reduce(@SuppressWarnings("unused") Class<E> exceptionClass, ExDoubleBinaryOperator<? extends E> op) {
        return reduce(op.wrap());
    }

    /**
     * Equivalent of {@link DoubleStream#collect}.
     * <p>
     * If {@code supplier}, {@code accumulator} or {@code combiner} throw a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <R>            the type of the mutable result container
     * @param <E>            The exception type thrown by {@code supplier}, {@code accumulator} or {@code combiner}
     * @param exceptionClass The exception class for {@link E}
     * @param supplier       see {@link DoubleStream#collect}
     * @param accumulator    see {@link DoubleStream#collect}
     * @param combiner       see {@link DoubleStream#collect}
     * @return see {@link DoubleStream#collect}
     */
    public <R, E extends Exception> R collect(@SuppressWarnings("unused") Class<E> exceptionClass, ExSupplier<R, ? extends E> supplier,
                                              ExObjDoubleConsumer<R, ? extends E> accumulator, ExBiConsumer<R, R, ? extends E> combiner) {
        return collect(supplier.wrap(), accumulator.wrap(), combiner.wrap());
    }

    /**
     * Equivalent of {@link DoubleStream#anyMatch}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code predicate}
     * @param exceptionClass The exception class for {@link E}
     * @param predicate      see {@link DoubleStream#anyMatch}
     * @return see {@link DoubleStream#anyMatch}
     */
    public <E extends Exception> boolean anyMatch(@SuppressWarnings("unused") Class<E> exceptionClass, ExDoublePredicate<? extends E> predicate) {
        return anyMatch(predicate.wrap());
    }

    /**
     * Equivalent of {@link DoubleStream#allMatch}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code predicate}
     * @param exceptionClass The exception class for {@link E}
     * @param predicate      see {@link DoubleStream#allMatch}
     * @return see {@link DoubleStream#allMatch}
     */
    public <E extends Exception> boolean allMatch(@SuppressWarnings("unused") Class<E> exceptionClass, ExDoublePredicate<? extends E> predicate) {
        return allMatch(predicate.wrap());
    }

    /**
     * Equivalent of {@link DoubleStream#noneMatch}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code predicate}
     * @param exceptionClass The exception class for {@link E}
     * @param predicate      see {@link DoubleStream#noneMatch}
     * @return see {@link DoubleStream#noneMatch}
     */
    public <E extends Exception> boolean noneMatch(@SuppressWarnings("unused") Class<E> exceptionClass, ExDoublePredicate<? extends E> predicate) {
        return noneMatch(predicate.wrap());
    }

}
