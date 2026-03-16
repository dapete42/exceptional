package net.dapete.exceptional.stream;

import lombok.experimental.Delegate;
import net.dapete.exceptional.ExException;
import net.dapete.exceptional.function.*;

import java.util.OptionalLong;
import java.util.function.*;
import java.util.stream.LongStream;

/**
 * A LongStream with additional functionality for functional interfaces that throw Exceptions.
 * <p>
 * Implements versions of all methods from LongStream that use functional interfaces, using their counterparts with Exceptions instead, e.g.
 * {@link #map(Class, ExLongUnaryOperator)} in parallel to {@link #map(LongUnaryOperator)}.
 * <p>
 * If these functional interfaces throw a checked exception, a {@link ExException} will be thrown instead.
 * This will have the original exception as its {@link ExException#getCause() cause}.
 * <p>
 * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
 * <em>stateful intermediate operation</em> is used on the stream (except if it is itself such an operation).
 */
public class ExLongStream implements LongStream {

    @Delegate
    private final LongStream stream;

    private ExLongStream(LongStream stream) {
        this.stream = stream;
    }

    /**
     * Create an instance from an existing {@code LongStream}.
     *
     * @param stream existing stream
     * @return instance from an existing {@code LongStream}
     */
    public static ExLongStream of(LongStream stream) {
        return new ExLongStream(stream);
    }

    /**
     * Returns an empty instance.
     *
     * @return an empty instance
     */
    public static ExLongStream empty() {
        return of(LongStream.empty());
    }

    /**
     * Returns an instance containing a single element.
     *
     * @param t the single element
     * @return an instance containing a single element
     */
    public static ExLongStream of(long t) {
        return of(LongStream.of(t));
    }

    /**
     * Returns an instance whose elements are the specified values.
     *
     * @param values the elements of the new stream
     * @return the new stream
     */
    public static ExLongStream of(long... values) {
        return of(LongStream.of(values));
    }

    /* Override all methods that usually return Stream to return an ExStream. */

    @Override
    public <U> ExStream<U> mapToObj(LongFunction<? extends U> mapper) {
        return ExStream.of(stream.mapToObj(mapper));
    }

    @Override
    public ExStream<Long> boxed() {
        return ExStream.of(stream.boxed());
    }

    /* Override all methods that usually return DoubleStream to return an ExDoubleStream. */

    @Override
    public ExDoubleStream mapToDouble(LongToDoubleFunction mapper) {
        return ExDoubleStream.of(stream.mapToDouble(mapper));
    }

    @Override
    public ExDoubleStream asDoubleStream() {
        return ExDoubleStream.of(stream.asDoubleStream());
    }

    /* Override all methods that usually return IntStream to return an ExIntStream. */

    @Override
    public ExIntStream mapToInt(LongToIntFunction mapper) {
        return ExIntStream.of(stream.mapToInt(mapper));
    }

    /* Override all methods that usually return LongStream to return an ExLongStream. */

    @Override
    public ExLongStream filter(LongPredicate predicate) {
        return of(stream.filter(predicate));
    }

    @Override
    public ExLongStream map(LongUnaryOperator mapper) {
        return of(stream.map(mapper));
    }

    @Override
    public ExLongStream flatMap(LongFunction<? extends LongStream> mapper) {
        return of(stream.flatMap(mapper));
    }

    @Override
    public ExLongStream distinct() {
        return of(stream.distinct());
    }

    @Override
    public ExLongStream sorted() {
        return of(stream.sorted());
    }

    @Override
    public ExLongStream peek(LongConsumer action) {
        return of(stream.peek(action));
    }

    @Override
    public ExLongStream limit(long maxSize) {
        return of(stream.limit(maxSize));
    }

    @Override
    public ExLongStream skip(long n) {
        return of(stream.skip(n));
    }

    @Override
    public ExLongStream sequential() {
        return of(stream.sequential());
    }

    @Override
    public ExLongStream parallel() {
        return of(stream.parallel());
    }

    @Override
    public ExLongStream unordered() {
        return of(stream.unordered());
    }

    @Override
    public ExLongStream onClose(Runnable closeHandler) {
        return of(stream.onClose(closeHandler));
    }

    /* Implement versions of all methods from LongStream that use functional interfaces, using their counterparts with Exceptions instead. */

    /**
     * Equivalent of {@link LongStream#filter}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <E>            The exception type thrown by {@code predicate}
     * @param exceptionClass The exception class for {@link E}
     * @param predicate      see {@link LongStream#filter}
     * @return see {@link LongStream#filter}
     */
    public <E extends Exception> ExLongStream filter(Class<E> exceptionClass, ExLongPredicate<? extends E> predicate) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return of(filter(predicate.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#map}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link LongStream#map}
     * @return see {@link LongStream#map}
     */
    public <E extends Exception> ExLongStream map(Class<E> exceptionClass, ExLongUnaryOperator<? extends E> mapper) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return of(map(mapper.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#mapToObj}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <U>            the element type of the new stream
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link LongStream#mapToObj}
     * @return see {@link LongStream#mapToObj}
     */
    public <U, E extends Exception> ExStream<U> mapToObj(Class<E> exceptionClass, ExLongFunction<? extends U, ? extends E> mapper) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return ExStream.of(mapToObj(mapper.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#mapToDouble}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link LongStream#mapToDouble}
     * @return see {@link LongStream#mapToDouble}
     */
    public <E extends Exception> ExDoubleStream mapToDouble(Class<E> exceptionClass, ExLongToDoubleFunction<? extends E> mapper) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return ExDoubleStream.of(mapToDouble(mapper.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#mapToInt}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link LongStream#mapToInt}
     * @return see {@link LongStream#mapToInt}
     */
    public <E extends Exception> ExIntStream mapToInt(Class<E> exceptionClass, ExLongToIntFunction<? extends E> mapper) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return ExIntStream.of(mapToInt(mapper.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#flatMap}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link LongStream#flatMap}
     * @return see {@link LongStream#flatMap}
     */
    public <E extends Exception> ExLongStream flatMap(Class<E> exceptionClass, ExLongFunction<? extends LongStream, ? extends E> mapper) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return of(flatMap(mapper.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#mapMulti}.
     * <p>
     * If {@code mapper} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     * <p>
     * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
     * <em>stateful intermediate operation</em> is used on the stream.
     *
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link LongStream#mapMulti}
     * @return see {@link LongStream#mapMulti}
     */
    public <E extends Exception> ExLongStream mapMulti(Class<E> exceptionClass, ExLongMapMultiConsumer<? extends E> mapper) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return of(mapMulti(mapper.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#peek}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code action}
     * @param exceptionClass The exception class for {@link E}
     * @param action         see {@link LongStream#peek}
     * @return see {@link LongStream#peek}
     */
    public <E extends Exception> ExLongStream peek(Class<E> exceptionClass, ExLongConsumer<? extends E> action) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return of(peek(action.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#takeWhile}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code predicate}
     * @param exceptionClass The exception class for {@link E}
     * @param predicate      see {@link LongStream#takeWhile}
     * @return see {@link LongStream#takeWhile}
     */
    public <E extends Exception> ExLongStream takeWhile(Class<E> exceptionClass, ExLongPredicate<? extends E> predicate) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return of(takeWhile(predicate.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#dropWhile}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param predicate see {@link LongStream#dropWhile}
     * @return see {@link LongStream#dropWhile}
     */
    public <E extends Exception> ExLongStream dropWhile(Class<E> exceptionClass, ExLongPredicate<? extends E> predicate) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return of(dropWhile(predicate.wrap()));
    }

    /**
     * Equivalent of {@link LongStream#forEach}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code action}
     * @param exceptionClass The exception class for {@link E}
     * @param action         see {@link LongStream#forEach}
     */
    public <E extends Exception> void forEach(Class<E> exceptionClass, ExLongConsumer<? extends E> action) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        forEach(action.wrap());
    }

    /**
     * Equivalent of {@link LongStream#forEachOrdered}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code action}
     * @param exceptionClass The exception class for {@link E}
     * @param action         see {@link LongStream#forEachOrdered}
     */
    public <E extends Exception> void forEachOrdered(Class<E> exceptionClass, ExLongConsumer<? extends E> action) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        forEachOrdered(action.wrap());
    }

    /**
     * Equivalent of {@link LongStream#reduce(long, LongBinaryOperator)}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code op}
     * @param exceptionClass The exception class for {@link E}
     * @param identity       see {@link LongStream#reduce(long, LongBinaryOperator)}
     * @param op             see {@link LongStream#reduce(long, LongBinaryOperator)}
     * @return see {@link LongStream#reduce(long, LongBinaryOperator)}
     */
    public <E extends Exception> Long reduce(Class<E> exceptionClass, Long identity, ExLongBinaryOperator<? extends E> op) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return reduce(identity, op.wrap());
    }

    /**
     * Equivalent of {@link LongStream#reduce(LongBinaryOperator)}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code op}
     * @param exceptionClass The exception class for {@link E}
     * @param op             see {@link LongStream#reduce(LongBinaryOperator)}
     * @return see {@link LongStream#reduce(LongBinaryOperator)}
     */
    public <E extends Exception> OptionalLong reduce(Class<E> exceptionClass, ExLongBinaryOperator<? extends E> op) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return reduce(op.wrap());
    }

    /**
     * Equivalent of {@link LongStream#collect}.
     * <p>
     * If {@code supplier}, {@code accumulator} or {@code combiner} throw a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <R>            the type of the mutable result container
     * @param <E>            The exception type thrown by {@code supplier}, {@code accumulator} or {@code combiner}
     * @param exceptionClass The exception class for {@link E}
     * @param supplier       see {@link LongStream#collect}
     * @param accumulator    see {@link LongStream#collect}
     * @param combiner       see {@link LongStream#collect}
     * @return see {@link LongStream#collect}
     */
    public <R, E extends Exception> R collect(Class<E> exceptionClass, ExSupplier<R, ? extends E> supplier,
                                              ExObjLongConsumer<R, ? extends E> accumulator, ExBiConsumer<R, R, ? extends E> combiner) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return collect(supplier.wrap(), accumulator.wrap(), combiner.wrap());
    }

    /**
     * Equivalent of {@link LongStream#anyMatch}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code predicate}
     * @param exceptionClass The exception class for {@link E}
     * @param predicate      see {@link LongStream#anyMatch}
     * @return see {@link LongStream#anyMatch}
     */
    public <E extends Exception> boolean anyMatch(Class<E> exceptionClass, ExLongPredicate<? extends E> predicate) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return anyMatch(predicate.wrap());
    }

    /**
     * Equivalent of {@link LongStream#allMatch}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code predicate}
     * @param exceptionClass The exception class for {@link E}
     * @param predicate      see {@link LongStream#allMatch}
     * @return see {@link LongStream#allMatch}
     */
    public <E extends Exception> boolean allMatch(Class<E> exceptionClass, ExLongPredicate<? extends E> predicate) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return allMatch(predicate.wrap());
    }

    /**
     * Equivalent of {@link LongStream#noneMatch}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code predicate}
     * @param exceptionClass The exception class for {@link E}
     * @param predicate      see {@link LongStream#noneMatch}
     * @return see {@link LongStream#noneMatch}
     */
    public <E extends Exception> boolean noneMatch(Class<E> exceptionClass, ExLongPredicate<? extends E> predicate) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return noneMatch(predicate.wrap());
    }

}
