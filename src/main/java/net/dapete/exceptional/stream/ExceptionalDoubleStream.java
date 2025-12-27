package net.dapete.exceptional.stream;

import lombok.experimental.Delegate;
import net.dapete.exceptional.ExceptionalException;
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
 * If these functional interfaces throw a checked exception, a {@link ExceptionalException} will be thrown instead.
 * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
 * <p>
 * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
 * <em>stateful intermediate operation</em> is used on the stream (except if it is itself such an operation).
 */
public class ExceptionalDoubleStream implements DoubleStream {

    @Delegate
    private final @NonNull DoubleStream stream;

    private ExceptionalDoubleStream(@NonNull DoubleStream stream) {
        this.stream = stream;
    }

    /**
     * Create an instance from an existing {@code DoubleStream}.
     *
     * @param stream existing stream
     * @return instance from an existing {@code DoubleStream}
     */
    public static @NonNull ExceptionalDoubleStream of(@NonNull DoubleStream stream) {
        return new ExceptionalDoubleStream(stream);
    }

    /**
     * Returns an empty instance.
     *
     * @return an empty instance
     */
    public static ExceptionalDoubleStream empty() {
        return of(DoubleStream.empty());
    }

    /**
     * Returns an instance containing a single element.
     *
     * @param t the single element
     * @return an instance containing a single element
     */
    public static ExceptionalDoubleStream of(double t) {
        return of(DoubleStream.of(t));
    }

    /**
     * Returns an instance whose elements are the specified values.
     *
     * @param values the elements of the new stream
     * @return the new stream
     */
    public static ExceptionalDoubleStream of(double... values) {
        return of(DoubleStream.of(values));
    }

    /* Override all methods that usually return Stream to return an ExceptionalStream */

    @Override
    public <U> @NonNull ExceptionalStream<U> mapToObj(@NonNull DoubleFunction<? extends U> mapper) {
        return ExceptionalStream.of(stream.mapToObj(mapper));
    }

    @Override
    public @NonNull ExceptionalStream<Double> boxed() {
        return ExceptionalStream.of(stream.boxed());
    }

    /* Override all methods that usually return DoubleStream to return an ExceptionalDoubleStream. */

    @Override
    public @NonNull ExceptionalDoubleStream filter(@NonNull DoublePredicate predicate) {
        return of(stream.filter(predicate));
    }

    @Override
    public @NonNull ExceptionalDoubleStream map(@NonNull DoubleUnaryOperator mapper) {
        return of(stream.map(mapper));
    }

    @Override
    public @NonNull ExceptionalDoubleStream flatMap(@NonNull DoubleFunction<? extends DoubleStream> mapper) {
        return of(stream.flatMap(mapper));
    }

    @Override
    public @NonNull ExceptionalDoubleStream distinct() {
        return of(stream.distinct());
    }

    @Override
    public @NonNull ExceptionalDoubleStream sorted() {
        return of(stream.sorted());
    }

    @Override
    public @NonNull ExceptionalDoubleStream peek(@NonNull DoubleConsumer action) {
        return of(stream.peek(action));
    }

    @Override
    public @NonNull ExceptionalDoubleStream limit(long maxSize) {
        return of(stream.limit(maxSize));
    }

    @Override
    public @NonNull ExceptionalDoubleStream skip(long n) {
        return of(stream.skip(n));
    }

    @Override
    public @NonNull ExceptionalDoubleStream sequential() {
        return of(stream.sequential());
    }

    @Override
    public @NonNull ExceptionalDoubleStream parallel() {
        return of(stream.parallel());
    }

    @Override
    public @NonNull ExceptionalDoubleStream unordered() {
        return of(stream.unordered());
    }

    @Override
    public @NonNull ExceptionalDoubleStream onClose(@NonNull Runnable closeHandler) {
        return of(stream.onClose(closeHandler));
    }

    /* Override all methods that usually return IntStream to return an ExceptionalIntStream. */

    @Override
    public @NonNull ExceptionalIntStream mapToInt(@NonNull DoubleToIntFunction mapper) {
        return ExceptionalIntStream.of(stream.mapToInt(mapper));
    }

    /* Override all methods that usually return LongStream to return an ExceptionalLongStream. */

    @Override
    public @NonNull ExceptionalLongStream mapToLong(@NonNull DoubleToLongFunction mapper) {
        return ExceptionalLongStream.of(stream.mapToLong(mapper));
    }

    /* Implement versions of all methods from DoubleStream that use functional interfaces, using their counterparts with Exceptions instead. */

    /**
     * Equivalent of {@link DoubleStream#filter}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param predicate see {@link DoubleStream#filter}
     * @return see {@link DoubleStream#filter}
     */
    public @NonNull ExceptionalDoubleStream exceptionalFilter(@NonNull ExceptionalDoublePredicate<?> predicate) {
        return ExceptionalDoubleStream.of(filter(predicate.wrap()));
    }

    /**
     * Equivalent of {@link DoubleStream#map}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link DoubleStream#map}
     * @return see {@link DoubleStream#map}
     */
    public @NonNull ExceptionalDoubleStream exceptionalMap(@NonNull ExceptionalDoubleUnaryOperator<?> mapper) {
        return ExceptionalDoubleStream.of(map(mapper.wrap()));
    }

    /**
     * Equivalent of {@link DoubleStream#mapToObj}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link DoubleStream#mapToObj}
     * @return see {@link DoubleStream#mapToObj}
     */
    public <U> @NonNull ExceptionalStream<U> exceptionalMapToObj(@NonNull ExceptionalDoubleFunction<? extends U, ?> mapper) {
        return ExceptionalStream.of(mapToObj(mapper.wrap()));
    }

    /**
     * Equivalent of {@link DoubleStream#mapToInt}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link DoubleStream#mapToInt}
     * @return see {@link DoubleStream#mapToInt}
     */
    public @NonNull ExceptionalIntStream exceptionalMapToInt(@NonNull ExceptionalDoubleToIntFunction<?> mapper) {
        return ExceptionalIntStream.of(mapToInt(mapper.wrap()));
    }

    /**
     * Equivalent of {@link DoubleStream#mapToLong}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link DoubleStream#mapToLong}
     * @return see {@link DoubleStream#mapToLong}
     */
    public @NonNull ExceptionalLongStream exceptionalMapToLong(@NonNull ExceptionalDoubleToLongFunction<?> mapper) {
        return ExceptionalLongStream.of(mapToLong(mapper.wrap()));
    }

    /**
     * Equivalent of {@link DoubleStream#flatMap}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link DoubleStream#flatMap}
     * @return see {@link DoubleStream#flatMap}
     */
    public @NonNull ExceptionalDoubleStream exceptionalFlatMap(@NonNull ExceptionalDoubleFunction<? extends DoubleStream, ?> mapper) {
        return ExceptionalDoubleStream.of(flatMap(mapper.wrap()));
    }

    /**
     * Equivalent of {@link DoubleStream#mapMulti}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link DoubleStream#mapMulti}
     * @return see {@link DoubleStream#mapMulti}
     */
    public @NonNull ExceptionalDoubleStream exceptionalMapMulti(@NonNull ExceptionalDoubleMapMultiConsumer<?> mapper) {
        return ExceptionalDoubleStream.of(mapMulti(mapper.wrap()));
    }

    /**
     * Equivalent of {@link DoubleStream#peek}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param action see {@link DoubleStream#peek}
     * @return see {@link DoubleStream#peek}
     */
    public @NonNull ExceptionalDoubleStream exceptionalPeek(@NonNull ExceptionalDoubleConsumer<?> action) {
        return ExceptionalDoubleStream.of(peek(action.wrap()));
    }

    /**
     * Equivalent of {@link DoubleStream#takeWhile}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param predicate see {@link DoubleStream#takeWhile}
     * @return see {@link DoubleStream#takeWhile}
     */
    public @NonNull ExceptionalDoubleStream exceptionalTakeWhile(@NonNull ExceptionalDoublePredicate<?> predicate) {
        return ExceptionalDoubleStream.of(takeWhile(predicate.wrap()));
    }

    /**
     * Equivalent of {@link DoubleStream#dropWhile}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param predicate see {@link DoubleStream#dropWhile}
     * @return see {@link DoubleStream#dropWhile}
     */
    public @NonNull ExceptionalDoubleStream exceptionalDropWhile(@NonNull ExceptionalDoublePredicate<?> predicate) {
        return ExceptionalDoubleStream.of(dropWhile(predicate.wrap()));
    }

    /**
     * Equivalent of {@link DoubleStream#forEach}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param action see {@link DoubleStream#forEach}
     */
    public void exceptionalForEach(@NonNull ExceptionalDoubleConsumer<?> action) {
        forEach(action.wrap());
    }

    /**
     * Equivalent of {@link DoubleStream#forEachOrdered}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param action see {@link DoubleStream#forEachOrdered}
     */
    public void exceptionalForEachOrdered(@NonNull ExceptionalDoubleConsumer<?> action) {
        forEachOrdered(action.wrap());
    }

    /**
     * Equivalent of {@link DoubleStream#reduce(double, DoubleBinaryOperator)}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param identity see {@link DoubleStream#reduce(double, DoubleBinaryOperator)}
     * @param op       see {@link DoubleStream#reduce(double, DoubleBinaryOperator)}
     * @return see {@link DoubleStream#reduce(double, DoubleBinaryOperator)}
     */
    public Double exceptionalReduce(double identity, @NonNull ExceptionalDoubleBinaryOperator<?> op) {
        return reduce(identity, op.wrap());
    }

    /**
     * Equivalent of {@link DoubleStream#reduce(DoubleBinaryOperator)}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param op see {@link DoubleStream#reduce(DoubleBinaryOperator)}
     * @return see {@link DoubleStream#reduce(DoubleBinaryOperator)}
     */
    public OptionalDouble exceptionalReduce(@NonNull ExceptionalDoubleBinaryOperator<?> op) {
        return reduce(op.wrap());
    }

    /**
     * Equivalent of {@link DoubleStream#collect)}.
     * <p>
     * If {@code supplier}, {@code accumulator} or {@code combiner} throw a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param supplier    see {@link DoubleStream#collect)}
     * @param accumulator see {@link DoubleStream#collect)}
     * @param combiner    see {@link DoubleStream#collect)}
     * @return see {@link DoubleStream#collect)}
     */
    public <R> R exceptionalCollect(@NonNull ExceptionalSupplier<R, ?> supplier, @NonNull ExceptionalObjDoubleConsumer<R, ?> accumulator,
                                    @NonNull ExceptionalBiConsumer<R, R, ?> combiner) {
        return collect(supplier.wrap(), accumulator.wrap(), combiner.wrap());
    }

    /**
     * Equivalent of {@link DoubleStream#anyMatch)}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param predicate see {@link DoubleStream#anyMatch)}
     * @return see {@link DoubleStream#anyMatch)}
     */
    public boolean exceptionalAnyMatch(@NonNull ExceptionalDoublePredicate<?> predicate) {
        return anyMatch(predicate.wrap());
    }

    /**
     * Equivalent of {@link DoubleStream#allMatch} )}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param predicate see {@link DoubleStream#allMatch)}
     * @return see {@link DoubleStream#allMatch)}
     */
    public boolean exceptionalAllMatch(@NonNull ExceptionalDoublePredicate<?> predicate) {
        return allMatch(predicate.wrap());
    }

    public boolean exceptionalNoneMatch(@NonNull ExceptionalDoublePredicate<?> predicate) {
        return noneMatch(predicate.wrap());
    }

}
