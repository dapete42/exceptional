package net.dapete.exceptional.stream;

import lombok.experimental.Delegate;
import net.dapete.exceptional.ExException;
import net.dapete.exceptional.function.*;

import java.util.OptionalLong;
import java.util.function.*;
import java.util.stream.LongStream;

/**
 * A LongStream with additional functionality for functional interfaces that throw Exceptions.
 * <p>
 * Implements versions of all methods from LongStream that use functional interfaces, using their counterparts with Exceptions instead, e.g.
 * {@link #exMap} in parallel to {@link #map}.
 * <p>
 * If these functional interfaces throw a checked exception, a {@link ExException} will be thrown instead.
 * This will have the original exception as its {@link ExException#getCause() cause}.
 * <p>
 * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
 * <em>stateful intermediate operation</em> is used on the stream (except if it is itself such an operation).
 */
public class ExLongStream implements LongStream {

    @Delegate
    private final LongStream stream;

    private ExLongStream(LongStream stream) {
        this.stream = stream;
    }

    /**
     * Create an instance from an existing {@code LongStream}.
     *
     * @param stream existing stream
     * @return instance from an existing {@code LongStream}
     */
    public static ExLongStream of(LongStream stream) {
        return new ExLongStream(stream);
    }

    /**
     * Returns an empty instance.
     *
     * @return an empty instance
     */
    public static ExLongStream empty() {
        return of(LongStream.empty());
    }

    /**
     * Returns an instance containing a single element.
     *
     * @param t the single element
     * @return an instance containing a single element
     */
    public static ExLongStream of(long t) {
        return of(LongStream.of(t));
    }

    /**
     * Returns an instance whose elements are the specified values.
     *
     * @param values the elements of the new stream
     * @return the new stream
     */
    public static ExLongStream of(long... values) {
        return of(LongStream.of(values));
    }

    /* Override all methods that usually return Stream to return an ExStream. */

    @Override
    public <U> ExStream<U> mapToObj(LongFunction<? extends U> mapper) {
        return ExStream.of(stream.mapToObj(mapper));
    }

    @Override
    public ExStream<Long> boxed() {
        return ExStream.of(stream.boxed());
    }

    /* Override all methods that usually return DoubleStream to return an ExDoubleStream. */

    @Override
    public ExDoubleStream mapToDouble(LongToDoubleFunction mapper) {
        return ExDoubleStream.of(stream.mapToDouble(mapper));
    }

    @Override
    public ExDoubleStream asDoubleStream() {
        return ExDoubleStream.of(stream.asDoubleStream());
    }

    /* Override all methods that usually return IntStream to return an ExIntStream. */

    @Override
    public ExIntStream mapToInt(LongToIntFunction mapper) {
        return ExIntStream.of(stream.mapToInt(mapper));
    }

    /* Override all methods that usually return LongStream to return an ExLongStream. */

    @Override
    public ExLongStream filter(LongPredicate predicate) {
        return of(stream.filter(predicate));
    }

    @Override
    public ExLongStream map(LongUnaryOperator mapper) {
        return of(stream.map(mapper));
    }

    @Override
    public ExLongStream flatMap(LongFunction<? extends LongStream> mapper) {
        return of(stream.flatMap(mapper));
    }

    @Override
    public ExLongStream distinct() {
        return of(stream.distinct());
    }

    @Override
    public ExLongStream sorted() {
        return of(stream.sorted());
    }

    @Override
    public ExLongStream peek(LongConsumer action) {
        return of(stream.peek(action));
    }

    @Override
    public ExLongStream limit(long maxSize) {
        return of(stream.limit(maxSize));
    }

    @Override
    public ExLongStream skip(long n) {
        return of(stream.skip(n));
    }

    @Override
    public ExLongStream sequential() {
        return of(stream.sequential());
    }

    @Override
    public ExLongStream parallel() {
        return of(stream.parallel());
    }

    @Override
    public ExLongStream unordered() {
        return of(stream.unordered());
    }

    @Override
    public ExLongStream onClose(Runnable closeHandler) {
        return of(stream.onClose(closeHandler));
    }

    /* Implement versions of all methods from LongStream that use functional interfaces, using their counterparts with Exceptions instead. */

    /**
     * Equivalent of {@link LongStream#filter}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param predicate see {@link LongStream#filter}
     * @return see {@link LongStream#filter}
     */
    public ExLongStream exFilter(ExLongPredicate<?> predicate) {
        return of(filter(predicate.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#map}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link LongStream#map}
     * @return see {@link LongStream#map}
     */
    public ExLongStream exMap(ExLongUnaryOperator<?> mapper) {
        return of(map(mapper.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#mapToObj}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <U>    the element type of the new stream
     * @param mapper see {@link LongStream#mapToObj}
     * @return see {@link LongStream#mapToObj}
     */
    public <U> ExStream<U> exMapToObj(ExLongFunction<? extends U, ?> mapper) {
        return ExStream.of(mapToObj(mapper.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#mapToDouble}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link LongStream#mapToDouble}
     * @return see {@link LongStream#mapToDouble}
     */
    public ExDoubleStream exMapToDouble(ExLongToDoubleFunction<?> mapper) {
        return ExDoubleStream.of(mapToDouble(mapper.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#mapToInt}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link LongStream#mapToInt}
     * @return see {@link LongStream#mapToInt}
     */
    public ExIntStream exMapToInt(ExLongToIntFunction<?> mapper) {
        return ExIntStream.of(mapToInt(mapper.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#flatMap}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link LongStream#flatMap}
     * @return see {@link LongStream#flatMap}
     */
    public ExLongStream exFlatMap(ExLongFunction<? extends LongStream, ?> mapper) {
        return of(flatMap(mapper.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#mapMulti}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link LongStream#mapMulti}
     * @return see {@link LongStream#mapMulti}
     */
    public ExLongStream exMapMulti(ExLongMapMultiConsumer<?> mapper) {
        return of(mapMulti(mapper.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#peek}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param action see {@link LongStream#peek}
     * @return see {@link LongStream#peek}
     */
    public ExLongStream exPeek(ExLongConsumer<?> action) {
        return of(peek(action.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#takeWhile}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param predicate see {@link LongStream#takeWhile}
     * @return see {@link LongStream#takeWhile}
     */
    public ExLongStream exTakeWhile(ExLongPredicate<?> predicate) {
        return of(takeWhile(predicate.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#dropWhile}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param predicate see {@link LongStream#dropWhile}
     * @return see {@link LongStream#dropWhile}
     */
    public ExLongStream exDropWhile(ExLongPredicate<?> predicate) {
        return of(dropWhile(predicate.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#forEach}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param action see {@link LongStream#forEach}
     */
    public void exForEach(ExLongConsumer<?> action) {
        forEach(action.wrap());
    }

    /**
     * Equivalent of {@link LongStream#forEachOrdered}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param action see {@link LongStream#forEachOrdered}
     */
    public void exForEachOrdered(ExLongConsumer<?> action) {
        forEachOrdered(action.wrap());
    }

    /**
     * Equivalent of {@link LongStream#reduce(long, LongBinaryOperator)}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param identity see {@link LongStream#reduce(long, LongBinaryOperator)}
     * @param op       see {@link LongStream#reduce(long, LongBinaryOperator)}
     * @return see {@link LongStream#reduce(long, LongBinaryOperator)}
     */
    public Long exReduce(Long identity, ExLongBinaryOperator<?> op) {
        return reduce(identity, op.wrap());
    }

    /**
     * Equivalent of {@link LongStream#reduce(LongBinaryOperator)}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param op see {@link LongStream#reduce(LongBinaryOperator)}
     * @return see {@link LongStream#reduce(LongBinaryOperator)}
     */
    public OptionalLong exReduce(ExLongBinaryOperator<?> op) {
        return reduce(op.wrap());
    }

    /**
     * Equivalent of {@link LongStream#collect}.
     * <p>
     * If {@code supplier}, {@code accumulator} or {@code combiner} throw a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <R>         the type of the mutable result container
     * @param supplier    see {@link LongStream#collect}
     * @param accumulator see {@link LongStream#collect}
     * @param combiner    see {@link LongStream#collect}
     * @return see {@link LongStream#collect}
     */
    public <R> R exCollect(ExSupplier<R, ?> supplier, ExObjLongConsumer<R, ?> accumulator,
                           ExBiConsumer<R, R, ?> combiner) {
        return collect(supplier.wrap(), accumulator.wrap(), combiner.wrap());
    }

    /**
     * Equivalent of {@link LongStream#anyMatch}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param predicate see {@link LongStream#anyMatch}
     * @return see {@link LongStream#anyMatch}
     */
    public boolean exAnyMatch(ExLongPredicate<?> predicate) {
        return anyMatch(predicate.wrap());
    }

    /**
     * Equivalent of {@link LongStream#allMatch}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param predicate see {@link LongStream#allMatch}
     * @return see {@link LongStream#allMatch}
     */
    public boolean exAllMatch(ExLongPredicate<?> predicate) {
        return allMatch(predicate.wrap());
    }

    /**
     * Equivalent of {@link LongStream#noneMatch}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param predicate see {@link LongStream#noneMatch}
     * @return see {@link LongStream#noneMatch}
     */
    public boolean exNoneMatch(ExLongPredicate<?> predicate) {
        return noneMatch(predicate.wrap());
    }

}
