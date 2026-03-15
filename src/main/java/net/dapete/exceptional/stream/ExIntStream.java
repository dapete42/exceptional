package net.dapete.exceptional.stream;

import lombok.experimental.Delegate;
import net.dapete.exceptional.ExException;
import net.dapete.exceptional.function.*;

import java.util.OptionalInt;
import java.util.function.*;
import java.util.stream.IntStream;

/**
 * A IntStream with additional functionality for functional interfaces that throw Exceptions.
 * <p>
 * Implements versions of all methods from IntStream that use functional interfaces, using their counterparts with Exceptions instead, e.g.
 * {@link #exMap} in parallel to {@link #map}.
 * <p>
 * If these functional interfaces throw a checked exception, a {@link ExException} will be thrown instead.
 * This will have the original exception as its {@link ExException#getCause() cause}.
 * <p>
 * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
 * <em>stateful intermediate operation</em> is used on the stream (except if it is itself such an operation).
 */
public class ExIntStream implements IntStream {

    @Delegate
    private final IntStream stream;

    private ExIntStream(IntStream stream) {
        this.stream = stream;
    }

    /**
     * Create an instance from an existing {@code IntStream}.
     *
     * @param stream existing stream
     * @return instance from an existing {@code IntStream}
     */
    public static ExIntStream of(IntStream stream) {
        return new ExIntStream(stream);
    }

    /**
     * Returns an empty instance.
     *
     * @return an empty instance
     */
    public static ExIntStream empty() {
        return of(IntStream.empty());
    }

    /**
     * Returns an instance containing a single element.
     *
     * @param t the single element
     * @return an instance containing a single element
     */
    public static ExIntStream of(int t) {
        return of(IntStream.of(t));
    }

    /**
     * Returns an instance whose elements are the specified values.
     *
     * @param values the elements of the new stream
     * @return the new stream
     */
    public static ExIntStream of(int... values) {
        return of(IntStream.of(values));
    }

    /* Override all methods that usually return Stream to return an ExStream. */

    @Override
    public <U> ExStream<U> mapToObj(IntFunction<? extends U> mapper) {
        return ExStream.of(stream.mapToObj(mapper));
    }

    @Override
    public ExStream<Integer> boxed() {
        return ExStream.of(stream.boxed());
    }

    /* Override all methods that usually return DoubleStream to return an ExDoubleStream. */

    @Override
    public ExDoubleStream mapToDouble(IntToDoubleFunction mapper) {
        return ExDoubleStream.of(stream.mapToDouble(mapper));
    }

    @Override
    public ExDoubleStream asDoubleStream() {
        return ExDoubleStream.of(stream.asDoubleStream());
    }

    /* Override all methods that usually return IntStream to return an ExIntStream. */

    @Override
    public ExIntStream filter(IntPredicate predicate) {
        return of(stream.filter(predicate));
    }

    @Override
    public ExIntStream map(IntUnaryOperator mapper) {
        return of(stream.map(mapper));
    }

    @Override
    public ExIntStream flatMap(IntFunction<? extends IntStream> mapper) {
        return of(stream.flatMap(mapper));
    }

    @Override
    public ExIntStream distinct() {
        return of(stream.distinct());
    }

    @Override
    public ExIntStream sorted() {
        return of(stream.sorted());
    }

    @Override
    public ExIntStream peek(IntConsumer action) {
        return of(stream.peek(action));
    }

    @Override
    public ExIntStream limit(long maxSize) {
        return of(stream.limit(maxSize));
    }

    @Override
    public ExIntStream skip(long n) {
        return of(stream.skip(n));
    }

    @Override
    public ExIntStream sequential() {
        return of(stream.sequential());
    }

    @Override
    public ExIntStream parallel() {
        return of(stream.parallel());
    }

    @Override
    public ExIntStream unordered() {
        return of(stream.unordered());
    }

    @Override
    public ExIntStream onClose(Runnable closeHandler) {
        return of(stream.onClose(closeHandler));
    }

    /* Override all methods that usually return LongStream to return an ExLongStream. */

    @Override
    public ExLongStream mapToLong(IntToLongFunction mapper) {
        return ExLongStream.of(stream.mapToLong(mapper));
    }

    @Override
    public ExLongStream asLongStream() {
        return ExLongStream.of(stream.asLongStream());
    }

    /* Implement versions of all methods from IntStream that use functional interfaces, using their counterparts with Exceptions instead. */

    /**
     * Equivalent of {@link IntStream#filter}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param predicate see {@link IntStream#filter}
     * @return see {@link IntStream#filter}
     */
    public ExIntStream exFilter(ExIntPredicate<?> predicate) {
        return of(filter(predicate.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#map}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link IntStream#map}
     * @return see {@link IntStream#map}
     */
    public ExIntStream exMap(ExIntUnaryOperator<?> mapper) {
        return of(map(mapper.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#mapToObj}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <U>    the element type of the new stream
     * @param mapper see {@link IntStream#mapToObj}
     * @return see {@link IntStream#mapToObj}
     */
    public <U> ExStream<U> exMapToObj(ExIntFunction<? extends U, ?> mapper) {
        return ExStream.of(mapToObj(mapper.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#mapToDouble}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link IntStream#mapToDouble}
     * @return see {@link IntStream#mapToDouble}
     */
    public ExDoubleStream exMapToDouble(ExIntToDoubleFunction<?> mapper) {
        return ExDoubleStream.of(mapToDouble(mapper.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#mapToLong}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link IntStream#mapToLong}
     * @return see {@link IntStream#mapToLong}
     */
    public ExLongStream exMapToLong(ExIntToLongFunction<?> mapper) {
        return ExLongStream.of(mapToLong(mapper.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#flatMap}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link IntStream#flatMap}
     * @return see {@link IntStream#flatMap}
     */
    public ExIntStream exFlatMap(ExIntFunction<? extends IntStream, ?> mapper) {
        return of(flatMap(mapper.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#mapMulti}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link IntStream#mapMulti}
     * @return see {@link IntStream#mapMulti}
     */
    public ExIntStream exMapMulti(ExIntMapMultiConsumer<?> mapper) {
        return of(mapMulti(mapper.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#peek}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param action see {@link IntStream#peek}
     * @return see {@link IntStream#peek}
     */
    public ExIntStream exPeek(ExIntConsumer<?> action) {
        return of(peek(action.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#takeWhile}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param predicate see {@link IntStream#takeWhile}
     * @return see {@link IntStream#takeWhile}
     */
    public ExIntStream exTakeWhile(ExIntPredicate<?> predicate) {
        return of(takeWhile(predicate.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#dropWhile}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param predicate see {@link IntStream#dropWhile}
     * @return see {@link IntStream#dropWhile}
     */
    public ExIntStream exDropWhile(ExIntPredicate<?> predicate) {
        return of(dropWhile(predicate.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#forEach}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param action see {@link IntStream#forEach}
     */
    public void exForEach(ExIntConsumer<?> action) {
        forEach(action.wrap());
    }

    /**
     * Equivalent of {@link IntStream#forEachOrdered}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param action see {@link IntStream#forEachOrdered}
     */
    public void exForEachOrdered(ExIntConsumer<?> action) {
        forEachOrdered(action.wrap());
    }

    /**
     * Equivalent of {@link IntStream#reduce(int, IntBinaryOperator)}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param identity see {@link IntStream#reduce(int, IntBinaryOperator)}
     * @param op       see {@link IntStream#reduce(int, IntBinaryOperator)}
     * @return see {@link IntStream#reduce(int, IntBinaryOperator)}
     */
    public int exReduce(int identity, ExIntBinaryOperator<?> op) {
        return reduce(identity, op.wrap());
    }

    /**
     * Equivalent of {@link IntStream#reduce(IntBinaryOperator)}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param op see {@link IntStream#reduce(IntBinaryOperator)}
     * @return see {@link IntStream#reduce(IntBinaryOperator)}
     */
    public OptionalInt exReduce(ExIntBinaryOperator<?> op) {
        return reduce(op.wrap());
    }

    /**
     * Equivalent of {@link IntStream#collect}.
     * <p>
     * If {@code supplier}, {@code accumulator} or {@code combiner} throw a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <R>         the type of the mutable result container
     * @param supplier    see {@link IntStream#collect}
     * @param accumulator see {@link IntStream#collect}
     * @param combiner    see {@link IntStream#collect}
     * @return see {@link IntStream#collect}
     */
    public <R> R exCollect(ExSupplier<R, ?> supplier, ExObjIntConsumer<R, ?> accumulator,
                           ExBiConsumer<R, R, ?> combiner) {
        return collect(supplier.wrap(), accumulator.wrap(), combiner.wrap());
    }

    /**
     * Equivalent of {@link IntStream#anyMatch}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param predicate see {@link IntStream#anyMatch}
     * @return see {@link IntStream#anyMatch}
     */
    public boolean exAnyMatch(ExIntPredicate<?> predicate) {
        return anyMatch(predicate.wrap());
    }

    /**
     * Equivalent of {@link IntStream#allMatch}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param predicate see {@link IntStream#allMatch}
     * @return see {@link IntStream#allMatch}
     */
    public boolean exAllMatch(ExIntPredicate<?> predicate) {
        return allMatch(predicate.wrap());
    }

    /**
     * Equivalent of {@link IntStream#noneMatch} }.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param predicate see {@link IntStream#noneMatch}
     * @return see {@link IntStream#noneMatch}
     */
    public boolean exNoneMatch(ExIntPredicate<?> predicate) {
        return noneMatch(predicate.wrap());
    }

}
