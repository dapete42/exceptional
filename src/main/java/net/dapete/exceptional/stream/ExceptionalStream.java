package net.dapete.exceptional.stream;

import lombok.experimental.Delegate;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * A Stream with additional functionality.
 * <p>
 * Using {@link #wrapExceptions()} and {@link #wrapExceptions(Class)}, it is possible to switch to an instance of the stream-like class
 * {@link ActiveExceptionalStream} which implements methods analogue to {@link Stream#map}, {@link Stream#filter} etc. to allow functional interfaces that
 * throw exceptions (from the {@link net.dapete.exceptional.function} package).
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

    /**
     * Return an {@link ActiveExceptionalStream} for the same values that allows exception of type {@link Exception}.
     *
     * @param <E> the type of exceptions thrown
     * @return an {@link ActiveExceptionalStream} for the same values that allows exception of type {@link Exception}
     */
    public <E extends Exception> @NonNull ActiveExceptionalStream<T, E> wrapExceptions() {
        return new ActiveExceptionalStream<>(this);
    }

    /**
     * Return an {@link ActiveExceptionalStream} for the same values that allows exceptions of type {@link E}.
     *
     * @param <E>            the type of exceptions thrown
     * @param exceptionClass the class of the type of exceptions thrown
     * @return an {@link ActiveExceptionalStream} for the same values that allows exceptions of type {@link E}
     */
    public <E extends Exception> @NonNull ActiveExceptionalStream<T, E> wrapExceptions(@SuppressWarnings("unused") @NonNull Class<E> exceptionClass) {
        return new ActiveExceptionalStream<>(this);
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

}
