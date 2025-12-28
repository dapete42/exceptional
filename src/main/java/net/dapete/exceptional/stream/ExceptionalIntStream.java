package net.dapete.exceptional.stream;

import lombok.experimental.Delegate;
import net.dapete.exceptional.ExceptionalException;
import net.dapete.exceptional.function.*;
import org.jspecify.annotations.NonNull;

import java.util.OptionalInt;
import java.util.function.*;
import java.util.stream.IntStream;

/**
 * A IntStream with additional functionality for functional interfaces that throw Exceptions.
 * <p>
 * Implements versions of all methods from IntStream that use functional interfaces, using their counterparts with Exceptions instead, e.g.
 * {@link #exceptionalMap} in parallel to {@link #map}.
 * <p>
 * If these functional interfaces throw a checked exception, a {@link ExceptionalException} will be thrown instead.
 * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
 * <p>
 * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
 * <em>stateful intermediate operation</em> is used on the stream (except if it is itself such an operation).
 */
public class ExceptionalIntStream implements IntStream {

    @Delegate
    private final @NonNull IntStream stream;

    private ExceptionalIntStream(@NonNull IntStream stream) {
        this.stream = stream;
    }

    /**
     * Create an instance from an existing {@code IntStream}.
     *
     * @param stream existing stream
     * @return instance from an existing {@code IntStream}
     */
    public static @NonNull ExceptionalIntStream of(@NonNull IntStream stream) {
        return new ExceptionalIntStream(stream);
    }

    /**
     * Returns an empty instance.
     *
     * @return an empty instance
     */
    public static ExceptionalIntStream empty() {
        return of(IntStream.empty());
    }

    /**
     * Returns an instance containing a single element.
     *
     * @param t the single element
     * @return an instance containing a single element
     */
    public static ExceptionalIntStream of(int t) {
        return of(IntStream.of(t));
    }

    /**
     * Returns an instance whose elements are the specified values.
     *
     * @param values the elements of the new stream
     * @return the new stream
     */
    public static ExceptionalIntStream of(int... values) {
        return of(IntStream.of(values));
    }

    /* Override all methods that usually return Stream to return an ExceptionalStream. */

    @Override
    public <U> @NonNull ExceptionalStream<U> mapToObj(@NonNull IntFunction<? extends U> mapper) {
        return ExceptionalStream.of(stream.mapToObj(mapper));
    }

    @Override
    public @NonNull ExceptionalStream<Integer> boxed() {
        return ExceptionalStream.of(stream.boxed());
    }

    /* Override all methods that usually return DoubleStream to return an ExceptionalDoubleStream. */

    @Override
    public @NonNull ExceptionalDoubleStream mapToDouble(@NonNull IntToDoubleFunction mapper) {
        return ExceptionalDoubleStream.of(stream.mapToDouble(mapper));
    }

    @Override
    public @NonNull ExceptionalDoubleStream asDoubleStream() {
        return ExceptionalDoubleStream.of(stream.asDoubleStream());
    }

    /* Override all methods that usually return IntStream to return an ExceptionalIntStream. */

    @Override
    public @NonNull ExceptionalIntStream filter(@NonNull IntPredicate predicate) {
        return of(stream.filter(predicate));
    }

    @Override
    public @NonNull ExceptionalIntStream map(@NonNull IntUnaryOperator mapper) {
        return of(stream.map(mapper));
    }

    @Override
    public @NonNull ExceptionalIntStream flatMap(@NonNull IntFunction<? extends IntStream> mapper) {
        return of(stream.flatMap(mapper));
    }

    @Override
    public @NonNull ExceptionalIntStream distinct() {
        return of(stream.distinct());
    }

    @Override
    public @NonNull ExceptionalIntStream sorted() {
        return of(stream.sorted());
    }

    @Override
    public @NonNull ExceptionalIntStream peek(@NonNull IntConsumer action) {
        return of(stream.peek(action));
    }

    @Override
    public @NonNull ExceptionalIntStream limit(long maxSize) {
        return of(stream.limit(maxSize));
    }

    @Override
    public @NonNull ExceptionalIntStream skip(long n) {
        return of(stream.skip(n));
    }

    @Override
    public @NonNull ExceptionalIntStream sequential() {
        return of(stream.sequential());
    }

    @Override
    public @NonNull ExceptionalIntStream parallel() {
        return of(stream.parallel());
    }

    @Override
    public @NonNull ExceptionalIntStream unordered() {
        return of(stream.unordered());
    }

    @Override
    public @NonNull ExceptionalIntStream onClose(@NonNull Runnable closeHandler) {
        return of(stream.onClose(closeHandler));
    }

    /* Override all methods that usually return LongStream to return an ExceptionalLongStream. */

    @Override
    public @NonNull ExceptionalLongStream mapToLong(@NonNull IntToLongFunction mapper) {
        return ExceptionalLongStream.of(stream.mapToLong(mapper));
    }

    @Override
    public @NonNull ExceptionalLongStream asLongStream() {
        return ExceptionalLongStream.of(stream.asLongStream());
    }

    /* Implement versions of all methods from IntStream that use functional interfaces, using their counterparts with Exceptions instead. */

    /**
     * Equivalent of {@link IntStream#filter}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param predicate see {@link IntStream#filter}
     * @return see {@link IntStream#filter}
     */
    public @NonNull ExceptionalIntStream exceptionalFilter(@NonNull ExceptionalIntPredicate<?> predicate) {
        return of(filter(predicate.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#map}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link IntStream#map}
     * @return see {@link IntStream#map}
     */
    public @NonNull ExceptionalIntStream exceptionalMap(@NonNull ExceptionalIntUnaryOperator<?> mapper) {
        return of(map(mapper.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#mapToObj}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <U>    the element type of the new stream
     * @param mapper see {@link IntStream#mapToObj}
     * @return see {@link IntStream#mapToObj}
     */
    public <U> @NonNull ExceptionalStream<U> exceptionalMapToObj(@NonNull ExceptionalIntFunction<? extends U, ?> mapper) {
        return ExceptionalStream.of(mapToObj(mapper.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#mapToDouble}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link IntStream#mapToDouble}
     * @return see {@link IntStream#mapToDouble}
     */
    public @NonNull ExceptionalDoubleStream exceptionalMapToDouble(@NonNull ExceptionalIntToDoubleFunction<?> mapper) {
        return ExceptionalDoubleStream.of(mapToDouble(mapper.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#mapToLong}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link IntStream#mapToLong}
     * @return see {@link IntStream#mapToLong}
     */
    public @NonNull ExceptionalLongStream exceptionalMapToLong(@NonNull ExceptionalIntToLongFunction<?> mapper) {
        return ExceptionalLongStream.of(mapToLong(mapper.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#flatMap}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link IntStream#flatMap}
     * @return see {@link IntStream#flatMap}
     */
    public @NonNull ExceptionalIntStream exceptionalFlatMap(@NonNull ExceptionalIntFunction<? extends IntStream, ?> mapper) {
        return of(flatMap(mapper.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#mapMulti}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link IntStream#mapMulti}
     * @return see {@link IntStream#mapMulti}
     */
    public @NonNull ExceptionalIntStream exceptionalMapMulti(@NonNull ExceptionalIntMapMultiConsumer<?> mapper) {
        return of(mapMulti(mapper.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#peek}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param action see {@link IntStream#peek}
     * @return see {@link IntStream#peek}
     */
    public @NonNull ExceptionalIntStream exceptionalPeek(@NonNull ExceptionalIntConsumer<?> action) {
        return of(peek(action.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#takeWhile}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param predicate see {@link IntStream#takeWhile}
     * @return see {@link IntStream#takeWhile}
     */
    public @NonNull ExceptionalIntStream exceptionalTakeWhile(@NonNull ExceptionalIntPredicate<?> predicate) {
        return of(takeWhile(predicate.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#dropWhile}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param predicate see {@link IntStream#dropWhile}
     * @return see {@link IntStream#dropWhile}
     */
    public @NonNull ExceptionalIntStream exceptionalDropWhile(@NonNull ExceptionalIntPredicate<?> predicate) {
        return of(dropWhile(predicate.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#forEach}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param action see {@link IntStream#forEach}
     */
    public void exceptionalForEach(@NonNull ExceptionalIntConsumer<?> action) {
        forEach(action.wrap());
    }

    /**
     * Equivalent of {@link IntStream#forEachOrdered}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param action see {@link IntStream#forEachOrdered}
     */
    public void exceptionalForEachOrdered(@NonNull ExceptionalIntConsumer<?> action) {
        forEachOrdered(action.wrap());
    }

    /**
     * Equivalent of {@link IntStream#reduce(int, IntBinaryOperator)}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param identity see {@link IntStream#reduce(int, IntBinaryOperator)}
     * @param op       see {@link IntStream#reduce(int, IntBinaryOperator)}
     * @return see {@link IntStream#reduce(int, IntBinaryOperator)}
     */
    public int exceptionalReduce(int identity, @NonNull ExceptionalIntBinaryOperator<?> op) {
        return reduce(identity, op.wrap());
    }

    /**
     * Equivalent of {@link IntStream#reduce(IntBinaryOperator)}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param op see {@link IntStream#reduce(IntBinaryOperator)}
     * @return see {@link IntStream#reduce(IntBinaryOperator)}
     */
    public OptionalInt exceptionalReduce(@NonNull ExceptionalIntBinaryOperator<?> op) {
        return reduce(op.wrap());
    }

    /**
     * Equivalent of {@link IntStream#collect}.
     * <p>
     * If {@code supplier}, {@code accumulator} or {@code combiner} throw a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param <R>         the type of the mutable result container
     * @param supplier    see {@link IntStream#collect}
     * @param accumulator see {@link IntStream#collect}
     * @param combiner    see {@link IntStream#collect}
     * @return see {@link IntStream#collect}
     */
    public <R> R exceptionalCollect(@NonNull ExceptionalSupplier<R, ?> supplier, @NonNull ExceptionalObjIntConsumer<R, ?> accumulator,
                                    @NonNull ExceptionalBiConsumer<R, R, ?> combiner) {
        return collect(supplier.wrap(), accumulator.wrap(), combiner.wrap());
    }

    /**
     * Equivalent of {@link IntStream#anyMatch}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param predicate see {@link IntStream#anyMatch}
     * @return see {@link IntStream#anyMatch}
     */
    public boolean exceptionalAnyMatch(@NonNull ExceptionalIntPredicate<?> predicate) {
        return anyMatch(predicate.wrap());
    }

    /**
     * Equivalent of {@link IntStream#allMatch}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param predicate see {@link IntStream#allMatch}
     * @return see {@link IntStream#allMatch}
     */
    public boolean exceptionalAllMatch(@NonNull ExceptionalIntPredicate<?> predicate) {
        return allMatch(predicate.wrap());
    }

    /**
     * Equivalent of {@link IntStream#noneMatch} }.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param predicate see {@link IntStream#noneMatch}
     * @return see {@link IntStream#noneMatch}
     */
    public boolean exceptionalNoneMatch(@NonNull ExceptionalIntPredicate<?> predicate) {
        return noneMatch(predicate.wrap());
    }

}
