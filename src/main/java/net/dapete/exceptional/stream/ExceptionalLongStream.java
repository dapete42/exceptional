package net.dapete.exceptional.stream;

import lombok.experimental.Delegate;
import net.dapete.exceptional.ExceptionalException;
import net.dapete.exceptional.function.*;
import org.jspecify.annotations.NonNull;

import java.util.OptionalLong;
import java.util.function.*;
import java.util.stream.LongStream;

/**
 * A LongStream with additional functionality for functional interfaces that throw Exceptions.
 * <p>
 * Implements versions of all methods from LongStream that use functional interfaces, using their counterparts with Exceptions instead, e.g.
 * {@link #exceptionalMap} in parallel to {@link #map}.
 * <p>
 * If these functional interfaces throw a checked exception, a {@link ExceptionalException} will be thrown instead.
 * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
 * <p>
 * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
 * <em>stateful intermediate operation</em> is used on the stream (except if it is itself such an operation).
 */
public class ExceptionalLongStream implements LongStream {

    @Delegate
    private final @NonNull LongStream stream;

    private ExceptionalLongStream(@NonNull LongStream stream) {
        this.stream = stream;
    }

    /**
     * Create an instance from an existing {@code LongStream}.
     *
     * @param stream existing stream
     * @return instance from an existing {@code LongStream}
     */
    public static @NonNull ExceptionalLongStream of(@NonNull LongStream stream) {
        return new ExceptionalLongStream(stream);
    }

    /**
     * Returns an empty instance.
     *
     * @return an empty instance
     */
    public static ExceptionalLongStream empty() {
        return of(LongStream.empty());
    }

    /**
     * Returns an instance containing a single element.
     *
     * @param t the single element
     * @return an instance containing a single element
     */
    public static ExceptionalLongStream of(long t) {
        return of(LongStream.of(t));
    }

    /**
     * Returns an instance whose elements are the specified values.
     *
     * @param values the elements of the new stream
     * @return the new stream
     */
    public static ExceptionalLongStream of(long... values) {
        return of(LongStream.of(values));
    }

    /* Override all methods that usually return Stream to return an ExceptionalStream. */

    @Override
    public <U> @NonNull ExceptionalStream<U> mapToObj(@NonNull LongFunction<? extends U> mapper) {
        return ExceptionalStream.of(stream.mapToObj(mapper));
    }

    @Override
    public @NonNull ExceptionalStream<Long> boxed() {
        return ExceptionalStream.of(stream.boxed());
    }

    /* Override all methods that usually return DoubleStream to return an ExceptionalDoubleStream. */

    @Override
    public @NonNull ExceptionalDoubleStream mapToDouble(@NonNull LongToDoubleFunction mapper) {
        return ExceptionalDoubleStream.of(stream.mapToDouble(mapper));
    }

    @Override
    public ExceptionalDoubleStream asDoubleStream() {
        return ExceptionalDoubleStream.of(stream.asDoubleStream());
    }

    /* Override all methods that usually return IntStream to return an ExceptionalIntStream. */

    @Override
    public @NonNull ExceptionalIntStream mapToInt(@NonNull LongToIntFunction mapper) {
        return ExceptionalIntStream.of(stream.mapToInt(mapper));
    }

    /* Override all methods that usually return LongStream to return an ExceptionalLongStream. */

    @Override
    public @NonNull ExceptionalLongStream filter(@NonNull LongPredicate predicate) {
        return of(stream.filter(predicate));
    }

    @Override
    public @NonNull ExceptionalLongStream map(@NonNull LongUnaryOperator mapper) {
        return of(stream.map(mapper));
    }

    @Override
    public @NonNull ExceptionalLongStream flatMap(@NonNull LongFunction<? extends LongStream> mapper) {
        return of(stream.flatMap(mapper));
    }

    @Override
    public @NonNull ExceptionalLongStream distinct() {
        return of(stream.distinct());
    }

    @Override
    public @NonNull ExceptionalLongStream sorted() {
        return of(stream.sorted());
    }

    @Override
    public @NonNull ExceptionalLongStream peek(@NonNull LongConsumer action) {
        return of(stream.peek(action));
    }

    @Override
    public @NonNull ExceptionalLongStream limit(long maxSize) {
        return of(stream.limit(maxSize));
    }

    @Override
    public @NonNull ExceptionalLongStream skip(long n) {
        return of(stream.skip(n));
    }

    @Override
    public @NonNull ExceptionalLongStream sequential() {
        return of(stream.sequential());
    }

    @Override
    public @NonNull ExceptionalLongStream parallel() {
        return of(stream.parallel());
    }

    @Override
    public @NonNull ExceptionalLongStream unordered() {
        return of(stream.unordered());
    }

    @Override
    public @NonNull ExceptionalLongStream onClose(@NonNull Runnable closeHandler) {
        return of(stream.onClose(closeHandler));
    }

    /* Implement versions of all methods from LongStream that use functional interfaces, using their counterparts with Exceptions instead. */

    /**
     * Equivalent of {@link LongStream#filter}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param predicate see {@link LongStream#filter}
     * @return see {@link LongStream#filter}
     */
    public @NonNull ExceptionalLongStream exceptionalFilter(@NonNull ExceptionalLongPredicate<?> predicate) {
        return of(filter(predicate.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#map}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link LongStream#map}
     * @return see {@link LongStream#map}
     */
    public @NonNull ExceptionalLongStream exceptionalMap(@NonNull ExceptionalLongUnaryOperator<?> mapper) {
        return of(map(mapper.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#mapToObj}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <U>    the element type of the new stream
     * @param mapper see {@link LongStream#mapToObj}
     * @return see {@link LongStream#mapToObj}
     */
    public <U> @NonNull ExceptionalStream<U> exceptionalMapToObj(@NonNull ExceptionalLongFunction<? extends U, ?> mapper) {
        return ExceptionalStream.of(mapToObj(mapper.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#mapToDouble}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link LongStream#mapToDouble}
     * @return see {@link LongStream#mapToDouble}
     */
    public @NonNull ExceptionalDoubleStream exceptionalMapToDouble(@NonNull ExceptionalLongToDoubleFunction<?> mapper) {
        return ExceptionalDoubleStream.of(mapToDouble(mapper.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#mapToInt}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link LongStream#mapToInt}
     * @return see {@link LongStream#mapToInt}
     */
    public @NonNull ExceptionalIntStream exceptionalMapToInt(@NonNull ExceptionalLongToIntFunction<?> mapper) {
        return ExceptionalIntStream.of(mapToInt(mapper.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#flatMap}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link LongStream#flatMap}
     * @return see {@link LongStream#flatMap}
     */
    public @NonNull ExceptionalLongStream exceptionalFlatMap(@NonNull ExceptionalLongFunction<? extends LongStream, ?> mapper) {
        return of(flatMap(mapper.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#mapMulti}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link LongStream#mapMulti}
     * @return see {@link LongStream#mapMulti}
     */
    public @NonNull ExceptionalLongStream exceptionalMapMulti(@NonNull ExceptionalLongMapMultiConsumer<?> mapper) {
        return of(mapMulti(mapper.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#peek}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param action see {@link LongStream#peek}
     * @return see {@link LongStream#peek}
     */
    public @NonNull ExceptionalLongStream exceptionalPeek(@NonNull ExceptionalLongConsumer<?> action) {
        return of(peek(action.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#takeWhile}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param predicate see {@link LongStream#takeWhile}
     * @return see {@link LongStream#takeWhile}
     */
    public @NonNull ExceptionalLongStream exceptionalTakeWhile(@NonNull ExceptionalLongPredicate<?> predicate) {
        return of(takeWhile(predicate.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#dropWhile}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param predicate see {@link LongStream#dropWhile}
     * @return see {@link LongStream#dropWhile}
     */
    public @NonNull ExceptionalLongStream exceptionalDropWhile(@NonNull ExceptionalLongPredicate<?> predicate) {
        return of(dropWhile(predicate.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#forEach}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param action see {@link LongStream#forEach}
     */
    public void exceptionalForEach(@NonNull ExceptionalLongConsumer<?> action) {
        forEach(action.wrap());
    }

    /**
     * Equivalent of {@link LongStream#forEachOrdered}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param action see {@link LongStream#forEachOrdered}
     */
    public void exceptionalForEachOrdered(@NonNull ExceptionalLongConsumer<?> action) {
        forEachOrdered(action.wrap());
    }

    /**
     * Equivalent of {@link LongStream#reduce(long, LongBinaryOperator)}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param identity see {@link LongStream#reduce(long, LongBinaryOperator)}
     * @param op       see {@link LongStream#reduce(long, LongBinaryOperator)}
     * @return see {@link LongStream#reduce(long, LongBinaryOperator)}
     */
    public Long exceptionalReduce(Long identity, @NonNull ExceptionalLongBinaryOperator<?> op) {
        return reduce(identity, op.wrap());
    }

    /**
     * Equivalent of {@link LongStream#reduce(LongBinaryOperator)}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param op see {@link LongStream#reduce(LongBinaryOperator)}
     * @return see {@link LongStream#reduce(LongBinaryOperator)}
     */
    public OptionalLong exceptionalReduce(@NonNull ExceptionalLongBinaryOperator<?> op) {
        return reduce(op.wrap());
    }

    /**
     * Equivalent of {@link LongStream#collect}.
     * <p>
     * If {@code supplier}, {@code accumulator} or {@code combiner} throw a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param <R>         the type of the mutable result container
     * @param supplier    see {@link LongStream#collect}
     * @param accumulator see {@link LongStream#collect}
     * @param combiner    see {@link LongStream#collect}
     * @return see {@link LongStream#collect}
     */
    public <R> R exceptionalCollect(@NonNull ExceptionalSupplier<R, ?> supplier, @NonNull ExceptionalObjLongConsumer<R, ?> accumulator,
                                    @NonNull ExceptionalBiConsumer<R, R, ?> combiner) {
        return collect(supplier.wrap(), accumulator.wrap(), combiner.wrap());
    }

    /**
     * Equivalent of {@link LongStream#anyMatch}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param predicate see {@link LongStream#anyMatch}
     * @return see {@link LongStream#anyMatch}
     */
    public boolean exceptionalAnyMatch(@NonNull ExceptionalLongPredicate<?> predicate) {
        return anyMatch(predicate.wrap());
    }

    /**
     * Equivalent of {@link LongStream#allMatch}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param predicate see {@link LongStream#allMatch}
     * @return see {@link LongStream#allMatch}
     */
    public boolean exceptionalAllMatch(@NonNull ExceptionalLongPredicate<?> predicate) {
        return allMatch(predicate.wrap());
    }

    /**
     * Equivalent of {@link LongStream#noneMatch}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param predicate see {@link LongStream#noneMatch}
     * @return see {@link LongStream#noneMatch}
     */
    public boolean exceptionalNoneMatch(@NonNull ExceptionalLongPredicate<?> predicate) {
        return noneMatch(predicate.wrap());
    }

}
