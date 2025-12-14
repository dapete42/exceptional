package net.dapete.exceptional;

import lombok.experimental.Delegate;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * A Stream with additional functionality.
 * <p>Using {@link #exceptional()} and {@link }#exceptional(Class)}, it is possible to switch to an instance of the
 * stream-like class {@link OnlyExceptionalStream} which implements methods analogue to {@link Stream#map} , {@link Stream#filter} etc. to allow lambdas that
 * throw exceptions (from the {@link net.dapete.exceptional.function} package).
 *
 * @param <T> the type of the stream elements
 */
public final class ExceptionalStream<T> implements Stream<T> {

    @Delegate
    private final Stream<T> stream;

    private ExceptionalStream(Stream<T> stream) {
        this.stream = stream;
    }

    /**
     * Create an instance from an existing Stream.
     *
     * @param <T> the type of the stream elements
     * @return instance from an existing Stream
     */
    public static <T> ExceptionalStream<T> of(Stream<T> stream) {
        return new ExceptionalStream<>(stream);
    }

    /**
     * Create an instance from a collection.
     *
     * @param <T> the type of the stream elements
     * @return instance from a collection
     */
    public static <T> ExceptionalStream<T> of(Collection<T> collection) {
        return of(collection.stream());
    }

    /* Static methods from Stream to create streams. */

    /**
     * Returns an empty instance.
     *
     * @param <T> the type of the stream elements
     * @return an empty instance
     */
    public static <T> ExceptionalStream<T> empty() {
        return of(Stream.empty());
    }

    /**
     * Returns an instance containing a single element.
     *
     * @param <T> the type of the stream elements
     * @return an instance containing a single element
     */
    public static <T> ExceptionalStream<T> of(T t) {
        return of(Stream.of(t));
    }

    /**
     * Returns an instance containing a single element, if non-null, otherwise returns an empty instance.
     *
     * @param <T> the type of the stream elements
     * @return a stream with a single element if the specified element is non-null, otherwise an empty stream
     */
    public static <T> ExceptionalStream<T> ofNullable(T t) {
        return of(Stream.ofNullable(t));
    }

    /**
     * Returns an instance whose elements are the specified values.
     *
     * @param <T> the type of the stream elements
     * @return the new instance
     */
    @SafeVarargs
    public static <T> ExceptionalStream<T> of(T... values) {
        return of(Stream.of(values));
    }

    /**
     * Return an {@link ActiveExceptionalStream} for the same values that allows exception of type {@link Exception}.
     *
     * @return an {@link ActiveExceptionalStream} for the same values that allows exception of type {@link Exception}
     */
    public ActiveExceptionalStream<T, Exception> wrapExceptions() {
        return new ActiveExceptionalStream<>(this);
    }

    /**
     * Return an {@link ActiveExceptionalStream} for the same values that allows exceptions of type {@link E}.
     *
     * @param <E>            the type of exceptions thrown
     * @param exceptionClass the class of the type of exceptions thrown
     * @return an {@link ActiveExceptionalStream} for the same values that allows exceptions of type {@link E}
     */
    @SuppressWarnings("unused")
    public <E extends Exception> ActiveExceptionalStream<T, E> wrapExceptions(Class<E> exceptionClass) {
        return new ActiveExceptionalStream<>(this);
    }

    /* Override all methods that usually return Stream to return an ExceptionalStream. */

    @Override
    public ExceptionalStream<T> filter(Predicate<? super T> predicate) {
        return of(stream.filter(predicate));
    }

    // @inheritDocs
    @Override
    public <R> ExceptionalStream<R> map(Function<? super T, ? extends R> mapper) {
        return of(stream.map(mapper));
    }

    // @inheritDocs
    @Override
    public <R> ExceptionalStream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return of(stream.flatMap(mapper));
    }

    // @inheritDocs
    @Override
    public <R> ExceptionalStream<R> mapMulti(BiConsumer<? super T, ? super Consumer<R>> mapper) {
        return of(stream.mapMulti(mapper));
    }

    // @inheritDocs
    @Override
    public ExceptionalStream<T> distinct() {
        return of(stream.distinct());
    }

    // @inheritDocs
    @Override
    public ExceptionalStream<T> sorted() {
        return of(stream.sorted());
    }

    // @inheritDocs
    @Override
    public ExceptionalStream<T> sorted(Comparator<? super T> comparator) {
        return of(stream.sorted(comparator));
    }

    // @inheritDocs
    @Override
    public ExceptionalStream<T> peek(Consumer<? super T> action) {
        return of(stream.peek(action));
    }

    // @inheritDocs
    @Override
    public ExceptionalStream<T> limit(long maxSize) {
        return of(stream.limit(maxSize));
    }

    // @inheritDocs
    @Override
    public ExceptionalStream<T> skip(long n) {
        return of(stream.skip(n));
    }

    // @inheritDocs
    @Override
    public ExceptionalStream<T> takeWhile(Predicate<? super T> predicate) {
        return of(stream.takeWhile(predicate));
    }

    // @inheritDocs
    @Override
    public ExceptionalStream<T> dropWhile(Predicate<? super T> predicate) {
        return of(stream.dropWhile(predicate));
    }

}
