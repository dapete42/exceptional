package net.dapete.exceptional;

import lombok.experimental.Delegate;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class ExceptionalStream<T> implements Stream<T> {

    @Delegate
    private final Stream<T> stream;

    private ExceptionalStream(Stream<T> stream) {
        this.stream = stream;
    }

    public static <T> ExceptionalStream<T> of(Stream<T> stream) {
        return new ExceptionalStream<>(stream);
    }

    public static <T> ExceptionalStream<T> of(Collection<T> collection) {
        return of(collection.stream());
    }

    /* Static methods from Stream to create streams. */

    public static <T> ExceptionalStream<T> empty() {
        return of(Stream.empty());
    }

    public static <T> ExceptionalStream<T> of(T t) {
        return of(Stream.of(t));
    }

    public static <T> ExceptionalStream<T> ofNullable(T t) {
        return of(Stream.ofNullable(t));
    }

    public static <T> ExceptionalStream<T> of(T... values) {
        return of(Stream.of(values));
    }

    public OnlyExceptionalStream<T, Exception> exceptional() {
        return new OnlyExceptionalStream<>(this);
    }

    public <E extends Exception> OnlyExceptionalStream<T, E> exceptional(Class<E> exceptionClass) {
        return new OnlyExceptionalStream<>(this);
    }

    /* Override all methods that usually return Stream to return an ExceptionalStream. */

    @Override
    public ExceptionalStream<T> filter(Predicate<? super T> predicate) {
        return of(stream.filter(predicate));
    }

    @Override
    public <R> ExceptionalStream<R> map(Function<? super T, ? extends R> mapper) {
        return of(stream.map(mapper));
    }

    @Override
    public <R> ExceptionalStream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return of(stream.flatMap(mapper));
    }

    @Override
    public <R> ExceptionalStream<R> mapMulti(BiConsumer<? super T, ? super Consumer<R>> mapper) {
        return of(stream.mapMulti(mapper));
    }

    @Override
    public ExceptionalStream<T> distinct() {
        return of(stream.distinct());
    }

    @Override
    public ExceptionalStream<T> sorted() {
        return of(stream.sorted());
    }

    @Override
    public ExceptionalStream<T> sorted(Comparator<? super T> comparator) {
        return of(stream.sorted(comparator));
    }

    @Override
    public ExceptionalStream<T> peek(Consumer<? super T> action) {
        return of(stream.peek(action));
    }

    @Override
    public ExceptionalStream<T> limit(long maxSize) {
        return of(stream.limit(maxSize));
    }

    @Override
    public ExceptionalStream<T> skip(long n) {
        return of(stream.skip(n));
    }

    @Override
    public ExceptionalStream<T> takeWhile(Predicate<? super T> predicate) {
        return of(stream.takeWhile(predicate));
    }

    @Override
    public ExceptionalStream<T> dropWhile(Predicate<? super T> predicate) {
        return of(stream.dropWhile(predicate));
    }

}
