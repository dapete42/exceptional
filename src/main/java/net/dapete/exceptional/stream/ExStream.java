package net.dapete.exceptional.stream;

import lombok.experimental.Delegate;
import net.dapete.exceptional.ExException;
import net.dapete.exceptional.function.*;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * A Stream with additional functionality for functional interfaces that throw Exceptions.
 * <p>
 * Implements versions of all methods from Stream that use functional interfaces, using their counterparts with Exceptions instead, e.g.
 * {@link #exMap} in parallel to {@link #map}.
 * <p>
 * If these functional interfaces throw a checked exception, a {@link ExException} will be thrown instead.
 * This will have the original exception as its {@link ExException#getCause() cause}.
 * <p>
 * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
 * <em>stateful intermediate operation</em> is used on the stream (except if it is itself such an operation).
 *
 * @param <T> the type of the stream elements
 */
public final class ExStream<T> implements Stream<T> {

    @Delegate
    private final @NonNull Stream<T> stream;

    private ExStream(@NonNull Stream<T> stream) {
        this.stream = stream;
    }

    /**
     * Create an instance from an existing Stream.
     *
     * @param <T>    the type of the stream elements
     * @param stream existing stream
     * @return instance from an existing Stream
     */
    public static <T> @NonNull ExStream<T> of(@NonNull Stream<T> stream) {
        return new ExStream<>(stream);
    }

    /**
     * Create an instance from an existing DoubleStream.
     *
     * @param stream existing stream
     * @return instance from an existing Stream
     */
    public static @NonNull ExStream<@NonNull Double> of(@NonNull DoubleStream stream) {
        return new ExStream<>(stream.boxed());
    }

    /**
     * Create an instance from an existing IntStream.
     *
     * @param stream existing stream
     * @return instance from an existing Stream
     */
    public static @NonNull ExStream<@NonNull Integer> of(@NonNull IntStream stream) {
        return new ExStream<>(stream.boxed());
    }

    /**
     * Create an instance from an existing IntStream.
     *
     * @param stream existing stream
     * @return instance from an existing Stream
     */
    public static @NonNull ExStream<@NonNull Long> of(@NonNull LongStream stream) {
        return new ExStream<>(stream.boxed());
    }

    /**
     * Create an instance from a collection.
     *
     * @param <T>        the type of the stream elements
     * @param collection collection
     * @return instance from a collection
     */
    public static <T> @NonNull ExStream<T> of(@NonNull Collection<T> collection) {
        return of(collection.stream());
    }

    /* Static methods from Stream to create streams. */

    /**
     * Returns an empty instance.
     *
     * @param <T> the type of the stream elements
     * @return an empty instance
     */
    public static <T> @NonNull ExStream<@NonNull T> empty() {
        return of(Stream.empty());
    }

    /**
     * Returns an instance containing a single element.
     *
     * @param <T> the type of the stream elements
     * @param t   the single element
     * @return an instance containing a single element
     */
    public static <T> @NonNull ExStream<T> of(T t) {
        return of(Stream.of(t));
    }

    /**
     * Returns an instance containing a single element, if non-null, otherwise returns an empty instance.
     *
     * @param <T> the type of stream elements
     * @param t   the single element
     * @return a stream with a single element if the specified element is non-null, otherwise an empty stream
     */
    public static <T> @NonNull ExStream<T> ofNullable(@Nullable T t) {
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
    public static <T> @NonNull ExStream<T> of(T... values) {
        return of(Stream.of(values));
    }

    /* Override all methods that usually return Stream to return an ExStream. */

    @Override
    public @NonNull ExStream<T> filter(Predicate<? super T> predicate) {
        return of(stream.filter(predicate));
    }

    @Override
    public <R> @NonNull ExStream<R> map(Function<? super T, ? extends R> mapper) {
        return of(stream.map(mapper));
    }

    @Override
    public <R> @NonNull ExStream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return of(stream.flatMap(mapper));
    }

    @Override
    public <R> @NonNull ExStream<R> mapMulti(BiConsumer<? super T, ? super Consumer<R>> mapper) {
        return of(stream.mapMulti(mapper));
    }

    @Override
    public @NonNull ExStream<T> distinct() {
        return of(stream.distinct());
    }

    @Override
    public @NonNull ExStream<T> sorted() {
        return of(stream.sorted());
    }

    @Override
    public @NonNull ExStream<T> sorted(Comparator<? super T> comparator) {
        return of(stream.sorted(comparator));
    }

    @Override
    public @NonNull ExStream<T> peek(Consumer<? super T> action) {
        return of(stream.peek(action));
    }

    @Override
    public @NonNull ExStream<T> limit(long maxSize) {
        return of(stream.limit(maxSize));
    }

    @Override
    public @NonNull ExStream<T> skip(long n) {
        return of(stream.skip(n));
    }

    @Override
    public @NonNull ExStream<T> takeWhile(Predicate<? super T> predicate) {
        return of(stream.takeWhile(predicate));
    }

    @Override
    public @NonNull ExStream<T> dropWhile(Predicate<? super T> predicate) {
        return of(stream.dropWhile(predicate));
    }

    /* Override all methods that usually return DoubleStream to return an ExDoubleStream. */

    @Override
    public ExDoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return ExDoubleStream.of(stream.mapToDouble(mapper));
    }

    @Override
    public ExDoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return ExDoubleStream.of(stream.flatMapToDouble(mapper));
    }

    @Override
    public ExDoubleStream mapMultiToDouble(BiConsumer<? super T, ? super DoubleConsumer> mapper) {
        return ExDoubleStream.of(stream.mapMultiToDouble(mapper));
    }

    /* Override all methods that usually return IntStream to return an ExIntStream. */

    @Override
    public ExIntStream mapToInt(ToIntFunction<? super T> mapper) {
        return ExIntStream.of(stream.mapToInt(mapper));
    }

    @Override
    public ExIntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return ExIntStream.of(stream.flatMapToInt(mapper));
    }

    @Override
    public ExIntStream mapMultiToInt(BiConsumer<? super T, ? super IntConsumer> mapper) {
        return ExIntStream.of(stream.mapMultiToInt(mapper));
    }

    /* Override all methods that usually return LongStream to return an ExLongStream. */

    @Override
    public ExLongStream mapToLong(ToLongFunction<? super T> mapper) {
        return ExLongStream.of(stream.mapToLong(mapper));
    }

    @Override
    public ExLongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return ExLongStream.of(stream.flatMapToLong(mapper));
    }

    @Override
    public ExLongStream mapMultiToLong(BiConsumer<? super T, ? super LongConsumer> mapper) {
        return ExLongStream.of(stream.mapMultiToLong(mapper));
    }

    /* Implement versions of all methods from Stream that use functional interfaces, using their counterparts with Exceptions instead. */

    /**
     * Equivalent of {@link Stream#filter}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param predicate see {@link Stream#filter}
     * @return see {@link Stream#filter}
     */
    public @NonNull ExStream<T> exFilter(@NonNull ExPredicate<? super T, ?> predicate) {
        return filter(predicate.wrap());
    }

    /**
     * Equivalent of {@link Stream#map(Function)}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <R>    The element type of the new stream
     * @param mapper see {@link Stream#map}
     * @return see {@link Stream#map}
     */
    public <R> @NonNull ExStream<R> exMap(@NonNull ExFunction<? super T, ? extends R, ?> mapper) {
        return map(mapper.wrap());
    }

    /**
     * Equivalent of {@link Stream#mapToDouble}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link Stream#mapToDouble}
     * @return see {@link Stream#mapToDouble}
     */
    public @NonNull ExDoubleStream exMapToDouble(@NonNull ExToDoubleFunction<? super T, ?> mapper) {
        return ExDoubleStream.of(mapToDouble(mapper.wrap()));
    }

    /**
     * Equivalent of {@link Stream#mapToInt}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link Stream#mapToInt}
     * @return see {@link Stream#mapToInt}
     */
    public @NonNull ExIntStream exMapToInt(@NonNull ExToIntFunction<? super T, ?> mapper) {
        return mapToInt(mapper.wrap());
    }

    /**
     * Equivalent of {@link Stream#mapToLong}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link Stream#mapToLong}
     * @return see {@link Stream#mapToLong}
     */
    public @NonNull ExLongStream exMapToLong(@NonNull ExToLongFunction<? super T, ?> mapper) {
        return ExLongStream.of(mapToLong(mapper.wrap()));
    }

    /**
     * Equivalent of {@link Stream#flatMap}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <R>    The element type of the new stream
     * @param mapper see {@link Stream#flatMap}
     * @return see {@link Stream#flatMap}
     */
    public <R> @NonNull ExStream<R> exFlatMap(@NonNull ExFunction<? super T, ? extends Stream<? extends R>, ?> mapper) {
        return flatMap(mapper.wrap());
    }

    /**
     * Equivalent of {@link Stream#flatMapToDouble}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link Stream#flatMapToDouble}
     * @return see {@link Stream#flatMapToDouble}
     */
    public @NonNull ExDoubleStream exFlatMapToDouble(@NonNull ExFunction<? super T, ? extends DoubleStream, ?> mapper) {
        return ExDoubleStream.of(flatMapToDouble(mapper.wrap()));
    }

    /**
     * Equivalent of {@link Stream#flatMapToInt}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link Stream#flatMapToInt}
     * @return see {@link Stream#flatMapToInt}
     */
    public @NonNull ExIntStream exFlatMapToInt(@NonNull ExFunction<? super T, ? extends IntStream, ?> mapper) {
        return ExIntStream.of(flatMapToInt(mapper.wrap()));
    }

    /**
     * Equivalent of {@link Stream#flatMapToLong}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link Stream#flatMapToLong}
     * @return see {@link Stream#flatMapToLong}
     */
    public @NonNull ExLongStream exFlatMapToLong(@NonNull ExFunction<? super T, ? extends LongStream, ?> mapper) {
        return ExLongStream.of(flatMapToLong(mapper.wrap()));
    }

    /**
     * Equivalent of {@link Stream#mapMulti}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <R>    The element type of the new stream
     * @param mapper see {@link Stream#mapMulti}
     * @return see {@link Stream#mapMulti}
     */
    public <R> @NonNull ExStream<R> exMapMulti(@NonNull ExBiConsumer<? super T, ? super Consumer<R>, ?> mapper) {
        return mapMulti(mapper.wrap());
    }

    /**
     * Equivalent of {@link Stream#mapMultiToDouble}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link Stream#mapMultiToDouble}
     * @return see {@link Stream#mapMultiToDouble}
     */
    public @NonNull ExDoubleStream exMapMultiToDouble(@NonNull ExBiConsumer<? super T, ? super DoubleConsumer, ?> mapper) {
        return mapMultiToDouble(mapper.wrap());
    }

    /**
     * Equivalent of {@link Stream#mapMultiToInt}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link Stream#mapMultiToInt}
     * @return see {@link Stream#mapMultiToInt}
     */
    public @NonNull ExIntStream exMapMultiToInt(@NonNull ExBiConsumer<? super T, ? super IntConsumer, ?> mapper) {
        return mapMultiToInt(mapper.wrap());
    }

    /**
     * Equivalent of {@link Stream#mapMultiToLong}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param mapper see {@link Stream#mapMultiToLong}
     * @return see {@link Stream#mapMultiToLong}
     */
    public @NonNull ExLongStream exMapMultiToLong(@NonNull ExBiConsumer<? super T, ? super LongConsumer, ?> mapper) {
        return mapMultiToLong(mapper.wrap());
    }

    /**
     * Equivalent of {@link Stream#peek}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param action see {@link Stream#peek}
     * @return see {@link Stream#peek}
     */
    public @NonNull ExStream<T> exPeek(@NonNull ExConsumer<? super T, ?> action) {
        return peek(action.wrap());
    }

    /**
     * Equivalent of {@link Stream#takeWhile}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param predicate see {@link Stream#takeWhile}
     * @return see {@link Stream#takeWhile}
     */
    public @NonNull ExStream<T> exTakeWhile(@NonNull ExPredicate<? super T, ?> predicate) {
        return takeWhile(predicate.wrap());
    }

    /**
     * Equivalent of {@link Stream#dropWhile}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when this method is called, but only when a <em>terminal operation</em> like {@link Stream#toList()}
     * is used on the stream.
     *
     * @param predicate see {@link Stream#dropWhile}
     * @return see {@link Stream#dropWhile}
     */
    public @NonNull ExStream<T> exDropWhile(@NonNull ExPredicate<? super T, ?> predicate) {
        return dropWhile(predicate.wrap());
    }

    /**
     * Equivalent of {@link Stream#forEach}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param action see {@link Stream#forEach}
     */
    public void exForEach(@NonNull ExConsumer<? super T, ?> action) {
        forEach(action.wrap());
    }

    /**
     * Equivalent of {@link Stream#forEachOrdered}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param action see {@link Stream#forEachOrdered}
     */
    public void exForEachOrdered(@NonNull ExConsumer<? super T, ?> action) {
        forEachOrdered(action.wrap());
    }

    /**
     * Equivalent of {@link Stream#reduce(BinaryOperator)}.
     * <p>
     * If {@code accumulator} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param accumulator see {@link Stream#reduce(BinaryOperator)}
     * @return see {@link Stream#reduce(BinaryOperator)}
     */
    public @NonNull Optional<T> exReduce(@NonNull ExBinaryOperator<T, ?> accumulator) {
        return reduce(accumulator.wrap());
    }

    /**
     * Equivalent of {@link Stream#reduce(Object, BinaryOperator)}.
     * <p>
     * If {@code accumulator} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param identity    see {@link Stream#reduce(Object, BiFunction, BinaryOperator)}
     * @param accumulator see {@link Stream#reduce(Object, BinaryOperator)}
     * @return see {@link Stream#reduce(Object, BinaryOperator)}
     */
    public T exReduce(T identity, @NonNull ExBinaryOperator<T, ?> accumulator) {
        return reduce(identity, accumulator.wrap());
    }

    /**
     * Equivalent of {@link Stream#reduce(Object, BiFunction, BinaryOperator)}.
     * <p>
     * If {@code accumulator} or {@code combiner} throw a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <U>         the element type of the new stream
     * @param identity    see {@link Stream#reduce(Object, BiFunction, BinaryOperator)}
     * @param accumulator see {@link Stream#reduce(Object, BiFunction, BinaryOperator)}
     * @param combiner    see {@link Stream#reduce(Object, BiFunction, BinaryOperator)}
     * @return see {@link Stream#reduce(Object, BiFunction, BinaryOperator)}
     */
    public <U> U exReduce(U identity, @NonNull ExBiFunction<U, ? super T, U, ?> accumulator,
                          @NonNull ExBinaryOperator<U, ?> combiner) {
        return reduce(identity, accumulator.wrap(), combiner.wrap());
    }

    /**
     * Equivalent of {@link Stream#collect(Supplier, BiConsumer, BiConsumer)}.
     * <p>
     * If {@code supplier}, {@code accumulator} or {@code combiner} throw a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <R>         the type of the mutable result container
     * @param supplier    see {@link Stream#collect(Supplier, BiConsumer, BiConsumer)}
     * @param accumulator see {@link Stream#collect(Supplier, BiConsumer, BiConsumer)}
     * @param combiner    see {@link Stream#collect(Supplier, BiConsumer, BiConsumer)}
     * @return see {@link Stream#collect(Supplier, BiConsumer, BiConsumer)}
     */
    public <R> R exCollect(@NonNull ExSupplier<R, ?> supplier, @NonNull ExBiConsumer<R, ? super T, ?> accumulator,
                           @NonNull ExBiConsumer<R, R, ?> combiner) {
        return collect(supplier.wrap(), accumulator.wrap(), combiner.wrap());
    }

}
