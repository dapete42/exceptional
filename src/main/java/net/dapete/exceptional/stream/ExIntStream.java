package net.dapete.exceptional.stream;

import lombok.experimental.Delegate;
import net.dapete.exceptional.ExException;
import net.dapete.exceptional.function.*;
import org.jspecify.annotations.NonNull;

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
    private final @NonNull IntStream stream;

    private ExIntStream(@NonNull IntStream stream) {
        this.stream = stream;
    }

    /**
     * Create an instance from an existing {@code IntStream}.
     *
     * @param stream existing stream
     * @return instance from an existing {@code IntStream}
     */
    public static @NonNull ExIntStream of(@NonNull IntStream stream) {
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
    public <U> @NonNull ExStream<U> mapToObj(@NonNull IntFunction<? extends U> mapper) {
        return ExStream.of(stream.mapToObj(mapper));
    }

    @Override
    public @NonNull ExStream<Integer> boxed() {
        return ExStream.of(stream.boxed());
    }

    /* Override all methods that usually return DoubleStream to return an ExDoubleStream. */

    @Override
    public @NonNull ExDoubleStream mapToDouble(@NonNull IntToDoubleFunction mapper) {
        return ExDoubleStream.of(stream.mapToDouble(mapper));
    }

    @Override
    public @NonNull ExDoubleStream asDoubleStream() {
        return ExDoubleStream.of(stream.asDoubleStream());
    }

    /* Override all methods that usually return IntStream to return an ExIntStream. */

    @Override
    public @NonNull ExIntStream filter(@NonNull IntPredicate predicate) {
        return of(stream.filter(predicate));
    }

    @Override
    public @NonNull ExIntStream map(@NonNull IntUnaryOperator mapper) {
        return of(stream.map(mapper));
    }

    @Override
    public @NonNull ExIntStream flatMap(@NonNull IntFunction<? extends IntStream> mapper) {
        return of(stream.flatMap(mapper));
    }

    @Override
    public @NonNull ExIntStream distinct() {
        return of(stream.distinct());
    }

    @Override
    public @NonNull ExIntStream sorted() {
        return of(stream.sorted());
    }

    @Override
    public @NonNull ExIntStream peek(@NonNull IntConsumer action) {
        return of(stream.peek(action));
    }

    @Override
    public @NonNull ExIntStream limit(long maxSize) {
        return of(stream.limit(maxSize));
    }

    @Override
    public @NonNull ExIntStream skip(long n) {
        return of(stream.skip(n));
    }

    @Override
    public @NonNull ExIntStream sequential() {
        return of(stream.sequential());
    }

    @Override
    public @NonNull ExIntStream parallel() {
        return of(stream.parallel());
    }

    @Override
    public @NonNull ExIntStream unordered() {
        return of(stream.unordered());
    }

    @Override
    public @NonNull ExIntStream onClose(@NonNull Runnable closeHandler) {
        return of(stream.onClose(closeHandler));
    }

    /* Override all methods that usually return LongStream to return an ExLongStream. */

    @Override
    public @NonNull ExLongStream mapToLong(@NonNull IntToLongFunction mapper) {
        return ExLongStream.of(stream.mapToLong(mapper));
    }

    @Override
    public @NonNull ExLongStream asLongStream() {
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
    public @NonNull ExIntStream exFilter(@NonNull ExIntPredicate<?> predicate) {
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
    public @NonNull ExIntStream exMap(@NonNull ExIntUnaryOperator<?> mapper) {
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
    public <U> @NonNull ExStream<U> exMapToObj(@NonNull ExIntFunction<? extends U, ?> mapper) {
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
    public @NonNull ExDoubleStream exMapToDouble(@NonNull ExIntToDoubleFunction<?> mapper) {
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
    public @NonNull ExLongStream exMapToLong(@NonNull ExIntToLongFunction<?> mapper) {
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
    public @NonNull ExIntStream exFlatMap(@NonNull ExIntFunction<? extends IntStream, ?> mapper) {
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
    public @NonNull ExIntStream exMapMulti(@NonNull ExIntMapMultiConsumer<?> mapper) {
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
    public @NonNull ExIntStream exPeek(@NonNull ExIntConsumer<?> action) {
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
    public @NonNull ExIntStream exTakeWhile(@NonNull ExIntPredicate<?> predicate) {
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
    public @NonNull ExIntStream exDropWhile(@NonNull ExIntPredicate<?> predicate) {
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
    public void exForEach(@NonNull ExIntConsumer<?> action) {
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
    public void exForEachOrdered(@NonNull ExIntConsumer<?> action) {
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
    public int exReduce(int identity, @NonNull ExIntBinaryOperator<?> op) {
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
    public OptionalInt exReduce(@NonNull ExIntBinaryOperator<?> op) {
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
    public <R> R exCollect(@NonNull ExSupplier<R, ?> supplier, @NonNull ExObjIntConsumer<R, ?> accumulator,
                           @NonNull ExBiConsumer<R, R, ?> combiner) {
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
    public boolean exAnyMatch(@NonNull ExIntPredicate<?> predicate) {
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
    public boolean exAllMatch(@NonNull ExIntPredicate<?> predicate) {
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
    public boolean exNoneMatch(@NonNull ExIntPredicate<?> predicate) {
        return noneMatch(predicate.wrap());
    }

}
