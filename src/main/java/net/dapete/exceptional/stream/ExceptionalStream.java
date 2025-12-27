package net.dapete.exceptional.stream;

import lombok.experimental.Delegate;
import net.dapete.exceptional.ExceptionalException;
import net.dapete.exceptional.function.*;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.*;

/**
 * A Stream with additional functionality for functional interfaces that throw Exceptions.
 * <p>
 * Implements versions of all methods from Stream that use functional interfaces, using their counterparts with Exceptions instead, e.g.
 * {@link #exceptionalMap} in parallel to {@link #map}.
 * <p>
 * If these functional interfaces throw a checked exception, a {@link ExceptionalException} will be thrown instead.
 * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
 * <p>
 * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
 * <em>stateful intermediate operation</em> is used on the stream (except if it is itself such an operation).
 *
 * @param <T> the type of the stream elements
 */
public final class ExceptionalStream<T> implements Stream<T> {

    @Delegate
    private final @NonNull Stream<T> stream;

    private ExceptionalStream(@NonNull Stream<T> stream) {
        this.stream = stream;
    }

    /**
     * Create an instance from an existing Stream.
     *
     * @param <T>    the type of the stream elements
     * @param stream existing stream
     * @return instance from an existing Stream
     */
    public static <T> @NonNull ExceptionalStream<T> of(@NonNull Stream<T> stream) {
        return new ExceptionalStream<>(stream);
    }

    /**
     * Create an instance from an existing DoubleStream.
     *
     * @param stream existing stream
     * @return instance from an existing Stream
     */
    public static @NonNull ExceptionalStream<@NonNull Double> of(@NonNull DoubleStream stream) {
        return new ExceptionalStream<>(stream.boxed());
    }

    /**
     * Create an instance from an existing IntStream.
     *
     * @param stream existing stream
     * @return instance from an existing Stream
     */
    public static @NonNull ExceptionalStream<@NonNull Integer> of(@NonNull IntStream stream) {
        return new ExceptionalStream<>(stream.boxed());
    }

    /**
     * Create an instance from an existing IntStream.
     *
     * @param stream existing stream
     * @return instance from an existing Stream
     */
    public static @NonNull ExceptionalStream<@NonNull Long> of(@NonNull LongStream stream) {
        return new ExceptionalStream<>(stream.boxed());
    }

    /**
     * Create an instance from a collection.
     *
     * @param <T>        the type of the stream elements
     * @param collection collection
     * @return instance from a collection
     */
    public static <T> @NonNull ExceptionalStream<T> of(@NonNull Collection<T> collection) {
        return of(collection.stream());
    }

    /* Static methods from Stream to create streams. */

    /**
     * Returns an empty instance.
     *
     * @param <T> the type of the stream elements
     * @return an empty instance
     */
    public static <T> @NonNull ExceptionalStream<@NonNull T> empty() {
        return of(Stream.empty());
    }

    /**
     * Returns an instance containing a single element.
     *
     * @param <T> the type of the stream elements
     * @param t   the single element
     * @return an instance containing a single element
     */
    public static <T> @NonNull ExceptionalStream<T> of(T t) {
        return of(Stream.of(t));
    }

    /**
     * Returns an instance containing a single element, if non-null, otherwise returns an empty instance.
     *
     * @param <T> the type of stream elements
     * @param t   the single element
     * @return a stream with a single element if the specified element is non-null, otherwise an empty stream
     */
    public static <T> @NonNull ExceptionalStream<T> ofNullable(@Nullable T t) {
        return of(Stream.ofNullable(t));
    }

    /**
     * Returns an instance whose elements are the specified values.
     *
     * @param <T>    the type of the stream elements
     * @param values the elements of the new stream
     * @return the new instance
     */
    @SafeVarargs
    public static <T> @NonNull ExceptionalStream<T> of(T... values) {
        return of(Stream.of(values));
    }

    /* Override all methods that usually return Stream to return an ExceptionalStream. */

    @Override
    public @NonNull ExceptionalStream<T> filter(Predicate<? super T> predicate) {
        return of(stream.filter(predicate));
    }

    @Override
    public <R> @NonNull ExceptionalStream<R> map(Function<? super T, ? extends R> mapper) {
        return of(stream.map(mapper));
    }

    @Override
    public <R> @NonNull ExceptionalStream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return of(stream.flatMap(mapper));
    }

    @Override
    public <R> @NonNull ExceptionalStream<R> mapMulti(BiConsumer<? super T, ? super Consumer<R>> mapper) {
        return of(stream.mapMulti(mapper));
    }

    @Override
    public @NonNull ExceptionalStream<T> distinct() {
        return of(stream.distinct());
    }

    @Override
    public @NonNull ExceptionalStream<T> sorted() {
        return of(stream.sorted());
    }

    @Override
    public @NonNull ExceptionalStream<T> sorted(Comparator<? super T> comparator) {
        return of(stream.sorted(comparator));
    }

    @Override
    public @NonNull ExceptionalStream<T> peek(Consumer<? super T> action) {
        return of(stream.peek(action));
    }

    @Override
    public @NonNull ExceptionalStream<T> limit(long maxSize) {
        return of(stream.limit(maxSize));
    }

    @Override
    public @NonNull ExceptionalStream<T> skip(long n) {
        return of(stream.skip(n));
    }

    @Override
    public @NonNull ExceptionalStream<T> takeWhile(Predicate<? super T> predicate) {
        return of(stream.takeWhile(predicate));
    }

    @Override
    public @NonNull ExceptionalStream<T> dropWhile(Predicate<? super T> predicate) {
        return of(stream.dropWhile(predicate));
    }

    /* Override all methods that usually return DoubleStream to return an ExceptionalDoubleStream. */

    @Override
    public ExceptionalDoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return ExceptionalDoubleStream.of(stream.mapToDouble(mapper));
    }

    @Override
    public ExceptionalDoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return ExceptionalDoubleStream.of(stream.flatMapToDouble(mapper));
    }

    @Override
    public ExceptionalDoubleStream mapMultiToDouble(BiConsumer<? super T, ? super DoubleConsumer> mapper) {
        return ExceptionalDoubleStream.of(stream.mapMultiToDouble(mapper));
    }

    /* Override all methods that usually return IntStream to return an ExceptionalIntStream. */

    @Override
    public ExceptionalIntStream mapToInt(ToIntFunction<? super T> mapper) {
        return ExceptionalIntStream.of(stream.mapToInt(mapper));
    }

    @Override
    public ExceptionalIntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return ExceptionalIntStream.of(stream.flatMapToInt(mapper));
    }

    @Override
    public ExceptionalIntStream mapMultiToInt(BiConsumer<? super T, ? super IntConsumer> mapper) {
        return ExceptionalIntStream.of(stream.mapMultiToInt(mapper));
    }

    /* Override all methods that usually return LongStream to return an ExceptionalLongStream. */

    @Override
    public ExceptionalLongStream mapToLong(ToLongFunction<? super T> mapper) {
        return ExceptionalLongStream.of(stream.mapToLong(mapper));
    }

    @Override
    public ExceptionalLongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return ExceptionalLongStream.of(stream.flatMapToLong(mapper));
    }

    @Override
    public ExceptionalLongStream mapMultiToLong(BiConsumer<? super T, ? super LongConsumer> mapper) {
        return ExceptionalLongStream.of(stream.mapMultiToLong(mapper));
    }

    /* Implement versions of all methods from Stream that use functional interfaces, using their counterparts with Exceptions instead. */

    /**
     * Equivalent of {@link Stream#filter}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param predicate see {@link Stream#filter}
     * @return see {@link Stream#filter}
     */
    public @NonNull ExceptionalStream<T> exceptionalFilter(@NonNull ExceptionalPredicate<? super T, ?> predicate) {
        return filter(predicate.wrap());
    }

    /**
     * Equivalent of {@link Stream#map(Function)}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <R>    The element type of the new stream
     * @param mapper see {@link Stream#map}
     * @return see {@link Stream#map}
     */
    public <R> @NonNull ExceptionalStream<R> exceptionalMap(@NonNull ExceptionalFunction<? super T, ? extends R, ?> mapper) {
        return map(mapper.wrap());
    }

    /**
     * Equivalent of {@link Stream#mapToDouble}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link Stream#mapToDouble}
     * @return see {@link Stream#mapToDouble}
     */
    public @NonNull ExceptionalDoubleStream exceptionalMapToDouble(@NonNull ExceptionalToDoubleFunction<? super T, ?> mapper) {
        return ExceptionalDoubleStream.of(mapToDouble(mapper.wrap()));
    }

    /**
     * Equivalent of {@link Stream#mapToInt}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link Stream#mapToInt}
     * @return see {@link Stream#mapToInt}
     */
    public @NonNull ExceptionalIntStream exceptionalMapToInt(@NonNull ExceptionalToIntFunction<? super T, ?> mapper) {
        return mapToInt(mapper.wrap());
    }

    /**
     * Equivalent of {@link Stream#mapToLong}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link Stream#mapToLong}
     * @return see {@link Stream#mapToLong}
     */
    public @NonNull ExceptionalLongStream exceptionalMapToLong(@NonNull ExceptionalToLongFunction<? super T, ?> mapper) {
        return ExceptionalLongStream.of(mapToLong(mapper.wrap()));
    }

    /**
     * Equivalent of {@link Stream#flatMap}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <R>    The element type of the new stream
     * @param mapper see {@link Stream#flatMap}
     * @return see {@link Stream#flatMap}
     */
    public <R> @NonNull ExceptionalStream<R> exceptionalFlatMap(@NonNull ExceptionalFunction<? super T, ? extends Stream<? extends R>, ?> mapper) {
        return flatMap(mapper.wrap());
    }

    /**
     * Equivalent of {@link Stream#flatMapToDouble}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link Stream#flatMapToDouble}
     * @return see {@link Stream#flatMapToDouble}
     */
    public @NonNull ExceptionalDoubleStream exceptionalFlatMapToDouble(@NonNull ExceptionalFunction<? super T, ? extends DoubleStream, ?> mapper) {
        return ExceptionalDoubleStream.of(flatMapToDouble(mapper.wrap()));
    }

    /**
     * Equivalent of {@link Stream#flatMapToInt}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link Stream#flatMapToInt}
     * @return see {@link Stream#flatMapToInt}
     */
    public @NonNull ExceptionalIntStream exceptionalFlatMapToInt(@NonNull ExceptionalFunction<? super T, ? extends IntStream, ?> mapper) {
        return ExceptionalIntStream.of(flatMapToInt(mapper.wrap()));
    }

    /**
     * Equivalent of {@link Stream#flatMapToLong}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link Stream#flatMapToLong}
     * @return see {@link Stream#flatMapToLong}
     */
    public @NonNull ExceptionalLongStream exceptionalFlatMapToLong(@NonNull ExceptionalFunction<? super T, ? extends LongStream, ?> mapper) {
        return ExceptionalLongStream.of(flatMapToLong(mapper.wrap()));
    }

    /**
     * Equivalent of {@link Stream#mapMulti}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <R>    The element type of the new stream
     * @param mapper see {@link Stream#mapMulti}
     * @return see {@link Stream#mapMulti}
     */
    public <R> @NonNull ExceptionalStream<R> exceptionalMapMulti(@NonNull ExceptionalBiConsumer<? super T, ? super Consumer<R>, ?> mapper) {
        return mapMulti(mapper.wrap());
    }

    /**
     * Equivalent of {@link Stream#mapMultiToDouble}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link Stream#mapMultiToDouble}
     * @return see {@link Stream#mapMultiToDouble}
     */
    public @NonNull ExceptionalDoubleStream exceptionalMapMultiToDouble(@NonNull ExceptionalBiConsumer<? super T, ? super DoubleConsumer, ?> mapper) {
        return mapMultiToDouble(mapper.wrap());
    }

    /**
     * Equivalent of {@link Stream#mapMultiToInt}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link Stream#mapMultiToInt}
     * @return see {@link Stream#mapMultiToInt}
     */
    public @NonNull ExceptionalIntStream exceptionalMapMultiToInt(@NonNull ExceptionalBiConsumer<? super T, ? super IntConsumer, ?> mapper) {
        return mapMultiToInt(mapper.wrap());
    }

    /**
     * Equivalent of {@link Stream#mapMultiToLong}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link Stream#mapMultiToLong}
     * @return see {@link Stream#mapMultiToLong}
     */
    public @NonNull ExceptionalLongStream exceptionalMapMultiToLong(@NonNull ExceptionalBiConsumer<? super T, ? super LongConsumer, ?> mapper) {
        return mapMultiToLong(mapper.wrap());
    }

    /**
     * Equivalent of {@link Stream#peek}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param action see {@link Stream#peek}
     * @return see {@link Stream#peek}
     */
    public @NonNull ExceptionalStream<T> exceptionalPeek(@NonNull ExceptionalConsumer<? super T, ?> action) {
        return peek(action.wrap());
    }

    /**
     * Equivalent of {@link Stream#takeWhile}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param predicate see {@link Stream#takeWhile}
     * @return see {@link Stream#takeWhile}
     */
    public @NonNull ExceptionalStream<T> exceptionalTakeWhile(@NonNull ExceptionalPredicate<? super T, ?> predicate) {
        return takeWhile(predicate.wrap());
    }

    /**
     * Equivalent of {@link Stream#dropWhile}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like {@link Stream#toList()}
     * is used on the stream.
     *
     * @param predicate see {@link Stream#dropWhile}
     * @return see {@link Stream#dropWhile}
     */
    public @NonNull ExceptionalStream<T> exceptionalDropWhile(@NonNull ExceptionalPredicate<? super T, ?> predicate) {
        return dropWhile(predicate.wrap());
    }

    /**
     * Equivalent of {@link Stream#forEach}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param action see {@link Stream#forEach}
     */
    public void exceptionalForEach(@NonNull ExceptionalConsumer<? super T, ?> action) {
        forEach(action.wrap());
    }

    /**
     * Equivalent of {@link Stream#forEachOrdered}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param action see {@link Stream#forEachOrdered}
     */
    public void exceptionalForEachOrdered(@NonNull ExceptionalConsumer<? super T, ?> action) {
        forEachOrdered(action.wrap());
    }

    /**
     * Equivalent of {@link Stream#reduce(BinaryOperator)}.
     * <p>
     * If {@code accumulator} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param accumulator see {@link Stream#reduce(BinaryOperator)}
     * @return see {@link Stream#reduce(BinaryOperator)}
     */
    public @NonNull Optional<T> exceptionalReduce(@NonNull ExceptionalBinaryOperator<T, ?> accumulator) {
        return reduce(accumulator.wrap());
    }

    /**
     * Equivalent of {@link Stream#reduce(Object, BinaryOperator)}.
     * <p>
     * If {@code accumulator} throws a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param identity    see {@link Stream#reduce(Object, BiFunction, BinaryOperator)}
     * @param accumulator see {@link Stream#reduce(Object, BinaryOperator)}
     * @return see {@link Stream#reduce(Object, BinaryOperator)}
     */
    public T exceptionalReduce(T identity, @NonNull ExceptionalBinaryOperator<T, ?> accumulator) {
        return reduce(identity, accumulator.wrap());
    }

    /**
     * Equivalent of {@link Stream#reduce(Object, BiFunction, BinaryOperator)}.
     * <p>
     * If {@code accumulator} or {@code combiner} throw a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param <U>         The type of the result
     * @param identity    see {@link Stream#reduce(Object, BiFunction, BinaryOperator)}
     * @param accumulator see {@link Stream#reduce(Object, BiFunction, BinaryOperator)}
     * @param combiner    see {@link Stream#reduce(Object, BiFunction, BinaryOperator)}
     * @return see {@link Stream#reduce(Object, BiFunction, BinaryOperator)}
     */
    public <U> U exceptionalReduce(U identity, @NonNull ExceptionalBiFunction<U, ? super T, U, ?> accumulator,
                                   @NonNull ExceptionalBinaryOperator<U, ?> combiner) {
        return reduce(identity, accumulator.wrap(), combiner.wrap());
    }

    /**
     * Equivalent of {@link Stream#collect(Supplier, BiConsumer, BiConsumer)}.
     * <p>
     * If {@code supplier}, {@code accumulator} or {@code combiner} throw a checked exception, a {@link ExceptionalException} will be thrown instead.
     * This will have the original exception as its {@link ExceptionalException#getCause() cause}.
     *
     * @param <R>         the type of the mutable result container
     * @param supplier    see {@link Stream#collect(Supplier, BiConsumer, BiConsumer)}
     * @param accumulator see {@link Stream#collect(Supplier, BiConsumer, BiConsumer)}
     * @param combiner    see {@link Stream#collect(Supplier, BiConsumer, BiConsumer)}
     * @return see {@link Stream#collect(Supplier, BiConsumer, BiConsumer)}
     */
    public <R> R exceptionalCollect(@NonNull ExceptionalSupplier<R, ?> supplier, @NonNull ExceptionalBiConsumer<R, ? super T, ?> accumulator,
                                    @NonNull ExceptionalBiConsumer<R, R, ?> combiner) {
        return collect(supplier.wrap(), accumulator.wrap(), combiner.wrap());
    }

}
