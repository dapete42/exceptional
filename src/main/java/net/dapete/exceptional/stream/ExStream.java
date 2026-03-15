package net.dapete.exceptional.stream;

import lombok.experimental.Delegate;
import net.dapete.exceptional.ExException;
import net.dapete.exceptional.function.*;
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
 * {@link #map(Class, ExFunction)} in parallel to {@link #map(Function)}.
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
    private final Stream<T> stream;

    private ExStream(Stream<T> stream) {
        this.stream = stream;
    }

    /**
     * Create an instance from an existing Stream.
     *
     * @param <T>    the type of the stream elements
     * @param stream existing stream
     * @return instance from an existing Stream
     */
    public static <T> ExStream<T> of(Stream<T> stream) {
        return new ExStream<>(stream);
    }

    /**
     * Create an instance from an existing DoubleStream.
     *
     * @param stream existing stream
     * @return instance from an existing Stream
     */
    public static ExStream<Double> of(DoubleStream stream) {
        return new ExStream<>(stream.boxed());
    }

    /**
     * Create an instance from an existing IntStream.
     *
     * @param stream existing stream
     * @return instance from an existing Stream
     */
    public static ExStream<Integer> of(IntStream stream) {
        return new ExStream<>(stream.boxed());
    }

    /**
     * Create an instance from an existing IntStream.
     *
     * @param stream existing stream
     * @return instance from an existing Stream
     */
    public static ExStream<Long> of(LongStream stream) {
        return new ExStream<>(stream.boxed());
    }

    /**
     * Create an instance from a collection.
     *
     * @param <T>        the type of the stream elements
     * @param collection collection
     * @return instance from a collection
     */
    public static <T> ExStream<T> of(Collection<T> collection) {
        return of(collection.stream());
    }

    /* Static methods from Stream to create streams. */

    /**
     * Returns an empty instance.
     *
     * @param <T> the type of the stream elements
     * @return an empty instance
     */
    public static <T> ExStream<T> empty() {
        return of(Stream.empty());
    }

    /**
     * Returns an instance containing a single element.
     *
     * @param <T> the type of the stream elements
     * @param t   the single element
     * @return an instance containing a single element
     */
    public static <T> ExStream<T> of(T t) {
        return of(Stream.of(t));
    }

    /**
     * Returns an instance containing a single element, if non-null, otherwise returns an empty instance.
     *
     * @param <T> the type of stream elements
     * @param t   the single element
     * @return a stream with a single element if the specified element is non-null, otherwise an empty stream
     */
    public static <T> ExStream<T> ofNullable(@Nullable T t) {
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
    public static <T> ExStream<T> of(T... values) {
        return of(Stream.of(values));
    }

    /* Override all methods that usually return Stream to return an ExStream. */

    @Override
    public ExStream<T> filter(Predicate<? super T> predicate) {
        return of(stream.filter(predicate));
    }

    @Override
    public <R> ExStream<R> map(Function<? super T, ? extends R> mapper) {
        return of(stream.map(mapper));
    }

    @Override
    public <R> ExStream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return of(stream.flatMap(mapper));
    }

    @Override
    public <R> ExStream<R> mapMulti(BiConsumer<? super T, ? super Consumer<R>> mapper) {
        return of(stream.mapMulti(mapper));
    }

    @Override
    public ExStream<T> distinct() {
        return of(stream.distinct());
    }

    @Override
    public ExStream<T> sorted() {
        return of(stream.sorted());
    }

    @Override
    public ExStream<T> sorted(Comparator<? super T> comparator) {
        return of(stream.sorted(comparator));
    }

    @Override
    public ExStream<T> peek(Consumer<? super T> action) {
        return of(stream.peek(action));
    }

    @Override
    public ExStream<T> limit(long maxSize) {
        return of(stream.limit(maxSize));
    }

    @Override
    public ExStream<T> skip(long n) {
        return of(stream.skip(n));
    }

    @Override
    public ExStream<T> takeWhile(Predicate<? super T> predicate) {
        return of(stream.takeWhile(predicate));
    }

    @Override
    public ExStream<T> dropWhile(Predicate<? super T> predicate) {
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
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param predicate      see {@link Stream#filter}
     * @return see {@link Stream#filter}
     */
    public <E extends Exception> ExStream<T> filter(@SuppressWarnings("unused") Class<E> exceptionClass, ExPredicate<? super T, ? extends E> predicate) {
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
     * @param <R>            The element type of the new stream
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link Stream#map}
     * @return see {@link Stream#map}
     */
    public <R, E extends Exception> ExStream<R> map(@SuppressWarnings("unused") Class<E> exceptionClass,
                                                    ExFunction<? super T, ? extends R, ? extends E> mapper) {
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
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link Stream#mapToDouble}
     * @return see {@link Stream#mapToDouble}
     */
    public <E extends Exception> ExDoubleStream mapToDouble(@SuppressWarnings("unused") Class<E> exceptionClass,
                                                            ExToDoubleFunction<? super T, ? extends E> mapper) {
        return mapToDouble(mapper.wrap());
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
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link Stream#mapToInt}
     * @return see {@link Stream#mapToInt}
     */
    public <E extends Exception> ExIntStream mapToInt(@SuppressWarnings("unused") Class<E> exceptionClass, ExToIntFunction<? super T, ? extends E> mapper) {
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
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link Stream#mapToLong}
     * @return see {@link Stream#mapToLong}
     */
    public <E extends Exception> ExLongStream mapToLong(@SuppressWarnings("unused") Class<E> exceptionClass, ExToLongFunction<? super T, ? extends E> mapper) {
        return mapToLong(mapper.wrap());
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
     * @param <R>            The element type of the new stream
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link Stream#flatMap}
     * @return see {@link Stream#flatMap}
     */
    public <R, E extends Exception> ExStream<R> flatMap(@SuppressWarnings("unused") Class<E> exceptionClass,
                                                        ExFunction<? super T, ? extends Stream<? extends R>, ? extends E> mapper) {
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
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link Stream#flatMapToDouble}
     * @return see {@link Stream#flatMapToDouble}
     */
    public <E extends Exception> ExDoubleStream flatMapToDouble(@SuppressWarnings("unused") Class<E> exceptionClass,
                                                                ExFunction<? super T, ? extends DoubleStream, ? extends E> mapper) {
        return flatMapToDouble(mapper.wrap());
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
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link Stream#flatMapToInt}
     * @return see {@link Stream#flatMapToInt}
     */
    public <E extends Exception> ExIntStream flatMapToInt(@SuppressWarnings("unused") Class<E> exceptionClass,
                                                          ExFunction<? super T, ? extends IntStream, ? extends E> mapper) {
        return flatMapToInt(mapper.wrap());
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
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link Stream#flatMapToLong}
     * @return see {@link Stream#flatMapToLong}
     */
    public <E extends Exception> ExLongStream flatMapToLong(@SuppressWarnings("unused") Class<E> exceptionClass,
                                                            ExFunction<? super T, ? extends LongStream, ? extends E> mapper) {
        return flatMapToLong(mapper.wrap());
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
     * @param <R>            The element type of the new stream
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link Stream#mapMulti}
     * @return see {@link Stream#mapMulti}
     */
    public <R, E extends Exception> ExStream<R> mapMulti(@SuppressWarnings("unused") Class<E> exceptionClass,
                                                         ExBiConsumer<? super T, ? super Consumer<R>, ? extends E> mapper) {
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
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link Stream#mapMultiToDouble}
     * @return see {@link Stream#mapMultiToDouble}
     */
    public <E extends Exception> ExDoubleStream mapMultiToDouble(@SuppressWarnings("unused") Class<E> exceptionClass,
                                                                 ExBiConsumer<? super T, ? super DoubleConsumer, ? extends E> mapper) {
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
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link Stream#mapMultiToInt}
     * @return see {@link Stream#mapMultiToInt}
     */
    public <E extends Exception> ExIntStream mapMultiToInt(@SuppressWarnings("unused") Class<E> exceptionClass,
                                                           ExBiConsumer<? super T, ? super IntConsumer, ? extends E> mapper) {
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
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link Stream#mapMultiToLong}
     * @return see {@link Stream#mapMultiToLong}
     */
    public <E extends Exception> ExLongStream mapMultiToLong(@SuppressWarnings("unused") Class<E> exceptionClass,
                                                             ExBiConsumer<? super T, ? super LongConsumer, ? extends E> mapper) {
        return mapMultiToLong(mapper.wrap());
    }

    /**
     * Equivalent of {@link Stream#peek}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code action}
     * @param exceptionClass The exception class for {@link E}
     * @param action         see {@link Stream#peek}
     * @return see {@link Stream#peek}
     */
    public <E extends Exception> ExStream<T> peek(@SuppressWarnings("unused") Class<E> exceptionClass, ExConsumer<? super T, ? extends E> action) {
        return peek(action.wrap());
    }

    /**
     * Equivalent of {@link Stream#takeWhile}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code predicate}
     * @param exceptionClass The exception class for {@link E}
     * @param predicate      see {@link Stream#takeWhile}
     * @return see {@link Stream#takeWhile}
     */
    public <E extends Exception> ExStream<T> takeWhile(@SuppressWarnings("unused") Class<E> exceptionClass, ExPredicate<? super T, ? extends E> predicate) {
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
     * @param <E>            The exception type thrown by {@code predicate}
     * @param exceptionClass The exception class for {@link E}
     * @param predicate      see {@link Stream#dropWhile}
     * @return see {@link Stream#dropWhile}
     */
    public <E extends Exception> ExStream<T> dropWhile(@SuppressWarnings("unused") Class<E> exceptionClass, ExPredicate<? super T, ? extends E> predicate) {
        return dropWhile(predicate.wrap());
    }

    /**
     * Equivalent of {@link Stream#forEach}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code action}
     * @param exceptionClass The exception class for {@link E}
     * @param action         see {@link Stream#forEach}
     */
    public <E extends Exception> void forEach(@SuppressWarnings("unused") Class<E> exceptionClass, ExConsumer<? super T, ? extends E> action) {
        forEach(action.wrap());
    }

    /**
     * Equivalent of {@link Stream#forEachOrdered}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code action}
     * @param exceptionClass The exception class for {@link E}
     * @param action         see {@link Stream#forEachOrdered}
     */
    public <E extends Exception> void forEachOrdered(@SuppressWarnings("unused") Class<E> exceptionClass, ExConsumer<? super T, ? extends E> action) {
        forEachOrdered(action.wrap());
    }

    /**
     * Equivalent of {@link Stream#reduce(BinaryOperator)}.
     * <p>
     * If {@code accumulator} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code accumulator}
     * @param exceptionClass The exception class for {@link E}
     * @param accumulator    see {@link Stream#reduce(BinaryOperator)}
     * @return see {@link Stream#reduce(BinaryOperator)}
     */
    public <E extends Exception> Optional<T> reduce(@SuppressWarnings("unused") Class<E> exceptionClass, ExBinaryOperator<T, ? extends E> accumulator) {
        return reduce(accumulator.wrap());
    }

    /**
     * Equivalent of {@link Stream#reduce(Object, BinaryOperator)}.
     * <p>
     * If {@code accumulator} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code accumulator}
     * @param exceptionClass The exception class for {@link E}
     * @param identity       see {@link Stream#reduce(Object, BiFunction, BinaryOperator)}
     * @param accumulator    see {@link Stream#reduce(Object, BinaryOperator)}
     * @return see {@link Stream#reduce(Object, BinaryOperator)}
     */
    public <E extends Exception> T reduce(@SuppressWarnings("unused") Class<E> exceptionClass, T identity, ExBinaryOperator<T, ? extends E> accumulator) {
        return reduce(identity, accumulator.wrap());
    }

    /**
     * Equivalent of {@link Stream#reduce(Object, BiFunction, BinaryOperator)}.
     * <p>
     * If {@code accumulator} or {@code combiner} throw a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <U>            the element type of the new stream
     * @param <E>            The exception type thrown by {@code accumulator} or {@code combiner}
     * @param exceptionClass The exception class for {@link E}
     * @param identity       see {@link Stream#reduce(Object, BiFunction, BinaryOperator)}
     * @param accumulator    see {@link Stream#reduce(Object, BiFunction, BinaryOperator)}
     * @param combiner       see {@link Stream#reduce(Object, BiFunction, BinaryOperator)}
     * @return see {@link Stream#reduce(Object, BiFunction, BinaryOperator)}
     */
    public <U, E extends Exception> U reduce(@SuppressWarnings("unused") Class<E> exceptionClass, U identity,
                                             ExBiFunction<U, ? super T, U, ? extends E> accumulator, ExBinaryOperator<U, ? extends E> combiner) {
        return reduce(identity, accumulator.wrap(), combiner.wrap());
    }

    /**
     * Equivalent of {@link Stream#collect(Supplier, BiConsumer, BiConsumer)}.
     * <p>
     * If {@code supplier}, {@code accumulator} or {@code combiner} throw a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <R>            the type of the mutable result container
     * @param <E>            The exception type thrown by {@code supplier}, {@code accumulator} or {@code combiner}
     * @param exceptionClass The exception class for {@link E}
     * @param supplier       see {@link Stream#collect(Supplier, BiConsumer, BiConsumer)}
     * @param accumulator    see {@link Stream#collect(Supplier, BiConsumer, BiConsumer)}
     * @param combiner       see {@link Stream#collect(Supplier, BiConsumer, BiConsumer)}
     * @return see {@link Stream#collect(Supplier, BiConsumer, BiConsumer)}
     */
    public <R, E extends Exception> R collect(@SuppressWarnings("unused") Class<E> exceptionClass, ExSupplier<R, ? extends E> supplier,
                                              ExBiConsumer<R, ? super T, ? extends E> accumulator, ExBiConsumer<R, R, ? extends E> combiner) {
        return collect(supplier.wrap(), accumulator.wrap(), combiner.wrap());
    }

}
