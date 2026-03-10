package net.dapete.exceptional.stream;

import lombok.experimental.Delegate;
import net.dapete.exceptional.ExException;
import net.dapete.exceptional.function.*;
import org.jspecify.annotations.NonNull;

import java.util.OptionalDouble;
import java.util.function.*;
import java.util.stream.DoubleStream;

/**
 * A DoubleStream with additional functionality for functional interfaces that throw Exceptions.
 * <p>
 * Implements versions of all methods from DoubleStream that use functional interfaces, using their counterparts with Exceptions instead, e.g.
 * {@link #exceptionalMap} in parallel to {@link #map}.
 * <p>
 * If these functional interfaces throw a checked exception, a {@link ExException} will be thrown instead.
 * This will have the original exception as its {@link ExException#getCause() cause}.
 * <p>
 * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
 * <em>stateful intermediate operation</em> is used on the stream (except if it is itself such an operation).
 */
public class ExDoubleStream implements DoubleStream {

    @Delegate
    private final @NonNull DoubleStream stream;

    private ExDoubleStream(@NonNull DoubleStream stream) {
        this.stream = stream;
    }

    /**
     * Create an instance from an existing {@code DoubleStream}.
     *
     * @param stream existing stream
     * @return instance from an existing {@code DoubleStream}
     */
    public static @NonNull ExDoubleStream of(@NonNull DoubleStream stream) {
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
    public <U> @NonNull ExStream<U> mapToObj(@NonNull DoubleFunction<? extends U> mapper) {
        return ExStream.of(stream.mapToObj(mapper));
    }

    @Override
    public @NonNull ExStream<Double> boxed() {
        return ExStream.of(stream.boxed());
    }

    /* Override all methods that usually return DoubleStream to return an ExDoubleStream. */

    @Override
    public @NonNull ExDoubleStream filter(@NonNull DoublePredicate predicate) {
        return of(stream.filter(predicate));
    }

    @Override
    public @NonNull ExDoubleStream map(@NonNull DoubleUnaryOperator mapper) {
        return of(stream.map(mapper));
    }

    @Override
    public @NonNull ExDoubleStream flatMap(@NonNull DoubleFunction<? extends DoubleStream> mapper) {
        return of(stream.flatMap(mapper));
    }

    @Override
    public @NonNull ExDoubleStream distinct() {
        return of(stream.distinct());
    }

    @Override
    public @NonNull ExDoubleStream sorted() {
        return of(stream.sorted());
    }

    @Override
    public @NonNull ExDoubleStream peek(@NonNull DoubleConsumer action) {
        return of(stream.peek(action));
    }

    @Override
    public @NonNull ExDoubleStream limit(long maxSize) {
        return of(stream.limit(maxSize));
    }

    @Override
    public @NonNull ExDoubleStream skip(long n) {
        return of(stream.skip(n));
    }

    @Override
    public @NonNull ExDoubleStream sequential() {
        return of(stream.sequential());
    }

    @Override
    public @NonNull ExDoubleStream parallel() {
        return of(stream.parallel());
    }

    @Override
    public @NonNull ExDoubleStream unordered() {
        return of(stream.unordered());
    }

    @Override
    public @NonNull ExDoubleStream onClose(@NonNull Runnable closeHandler) {
        return of(stream.onClose(closeHandler));
    }

    /* Override all methods that usually return IntStream to return an ExIntStream. */

    @Override
    public @NonNull ExIntStream mapToInt(@NonNull DoubleToIntFunction mapper) {
        return ExIntStream.of(stream.mapToInt(mapper));
    }

    /* Override all methods that usually return LongStream to return an ExLongStream. */

    @Override
    public @NonNull ExLongStream mapToLong(@NonNull DoubleToLongFunction mapper) {
        return ExLongStream.of(stream.mapToLong(mapper));
    }

    /* Implement versions of all methods from DoubleStream that use functional interfaces, using their counterparts with Exceptions instead. */

    /**
     * Equivalent of {@link DoubleStream#filter}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param predicate see {@link DoubleStream#filter}
     * @return see {@link DoubleStream#filter}
     */
    public @NonNull ExDoubleStream exceptionalFilter(@NonNull ExDoublePredicate<?> predicate) {
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
     * @param mapper see {@link DoubleStream#map}
     * @return see {@link DoubleStream#map}
     */
    public @NonNull ExDoubleStream exceptionalMap(@NonNull ExDoubleUnaryOperator<?> mapper) {
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
     * @param <U>    the element type of the new stream
     * @param mapper see {@link DoubleStream#mapToObj}
     * @return see {@link DoubleStream#mapToObj}
     */
    public <U> @NonNull ExStream<U> exceptionalMapToObj(@NonNull ExDoubleFunction<? extends U, ?> mapper) {
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
     * @param mapper see {@link DoubleStream#mapToInt}
     * @return see {@link DoubleStream#mapToInt}
     */
    public @NonNull ExIntStream exceptionalMapToInt(@NonNull ExDoubleToIntFunction<?> mapper) {
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
     * @param mapper see {@link DoubleStream#mapToLong}
     * @return see {@link DoubleStream#mapToLong}
     */
    public @NonNull ExLongStream exceptionalMapToLong(@NonNull ExDoubleToLongFunction<?> mapper) {
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
     * @param mapper see {@link DoubleStream#flatMap}
     * @return see {@link DoubleStream#flatMap}
     */
    public @NonNull ExDoubleStream exceptionalFlatMap(@NonNull ExDoubleFunction<? extends DoubleStream, ?> mapper) {
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
     * @param mapper see {@link DoubleStream#mapMulti}
     * @return see {@link DoubleStream#mapMulti}
     */
    public @NonNull ExDoubleStream exceptionalMapMulti(@NonNull ExDoubleMapMultiConsumer<?> mapper) {
        return of(mapMulti(mapper.wrap()));
    }

    /**
     * Equivalent of {@link DoubleStream#peek}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param action see {@link DoubleStream#peek}
     * @return see {@link DoubleStream#peek}
     */
    public @NonNull ExDoubleStream exceptionalPeek(@NonNull ExDoubleConsumer<?> action) {
        return of(peek(action.wrap()));
    }

    /**
     * Equivalent of {@link DoubleStream#takeWhile}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param predicate see {@link DoubleStream#takeWhile}
     * @return see {@link DoubleStream#takeWhile}
     */
    public @NonNull ExDoubleStream exceptionalTakeWhile(@NonNull ExDoublePredicate<?> predicate) {
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
    public @NonNull ExDoubleStream exceptionalDropWhile(@NonNull ExDoublePredicate<?> predicate) {
        return of(dropWhile(predicate.wrap()));
    }

    /**
     * Equivalent of {@link DoubleStream#forEach}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param action see {@link DoubleStream#forEach}
     */
    public void exceptionalForEach(@NonNull ExDoubleConsumer<?> action) {
        forEach(action.wrap());
    }

    /**
     * Equivalent of {@link DoubleStream#forEachOrdered}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param action see {@link DoubleStream#forEachOrdered}
     */
    public void exceptionalForEachOrdered(@NonNull ExDoubleConsumer<?> action) {
        forEachOrdered(action.wrap());
    }

    /**
     * Equivalent of {@link DoubleStream#reduce(double, DoubleBinaryOperator)}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param identity see {@link DoubleStream#reduce(double, DoubleBinaryOperator)}
     * @param op       see {@link DoubleStream#reduce(double, DoubleBinaryOperator)}
     * @return see {@link DoubleStream#reduce(double, DoubleBinaryOperator)}
     */
    public Double exceptionalReduce(double identity, @NonNull ExDoubleBinaryOperator<?> op) {
        return reduce(identity, op.wrap());
    }

    /**
     * Equivalent of {@link DoubleStream#reduce(DoubleBinaryOperator)}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param op see {@link DoubleStream#reduce(DoubleBinaryOperator)}
     * @return see {@link DoubleStream#reduce(DoubleBinaryOperator)}
     */
    public OptionalDouble exceptionalReduce(@NonNull ExDoubleBinaryOperator<?> op) {
        return reduce(op.wrap());
    }

    /**
     * Equivalent of {@link DoubleStream#collect}.
     * <p>
     * If {@code supplier}, {@code accumulator} or {@code combiner} throw a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <R>         the type of the mutable result container
     * @param supplier    see {@link DoubleStream#collect}
     * @param accumulator see {@link DoubleStream#collect}
     * @param combiner    see {@link DoubleStream#collect}
     * @return see {@link DoubleStream#collect}
     */
    public <R> R exceptionalCollect(@NonNull ExSupplier<R, ?> supplier, @NonNull ExObjDoubleConsumer<R, ?> accumulator,
                                    @NonNull ExBiConsumer<R, R, ?> combiner) {
        return collect(supplier.wrap(), accumulator.wrap(), combiner.wrap());
    }

    /**
     * Equivalent of {@link DoubleStream#anyMatch}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param predicate see {@link DoubleStream#anyMatch}
     * @return see {@link DoubleStream#anyMatch}
     */
    public boolean exceptionalAnyMatch(@NonNull ExDoublePredicate<?> predicate) {
        return anyMatch(predicate.wrap());
    }

    /**
     * Equivalent of {@link DoubleStream#allMatch}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param predicate see {@link DoubleStream#allMatch}
     * @return see {@link DoubleStream#allMatch}
     */
    public boolean exceptionalAllMatch(@NonNull ExDoublePredicate<?> predicate) {
        return allMatch(predicate.wrap());
    }

    /**
     * Equivalent of {@link DoubleStream#noneMatch}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param predicate see {@link DoubleStream#noneMatch}
     * @return see {@link DoubleStream#noneMatch}
     */
    public boolean exceptionalNoneMatch(@NonNull ExDoublePredicate<?> predicate) {
        return noneMatch(predicate.wrap());
    }

}
