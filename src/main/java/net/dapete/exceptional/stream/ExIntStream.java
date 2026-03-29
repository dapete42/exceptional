package net.dapete.exceptional.stream;

import lombok.experimental.Delegate;
import net.dapete.exceptional.ExException;
import net.dapete.exceptional.function.*;

import java.util.OptionalInt;
import java.util.function.*;
import java.util.stream.IntStream;

/**
 * A IntStream with additional functionality for functional interfaces that throw Exceptions.
 * <p>
 * Implements versions of all methods from IntStream that use functional interfaces, using their counterparts with Exceptions instead, e.g.
 * {@link #map(Class, ExIntUnaryOperator)} in parallel to {@link #map(IntUnaryOperator)}.
 * <p>
 * If these functional interfaces throw a checked exception, a {@link ExException} will be thrown instead.
 * This will have the original exception as its {@link ExException#getCause() cause}.
 * <p>
 * Note that this exception will likely not be thrown when a method is called, but only when a <em>terminal operation</em> or a
 * <em>stateful intermediate operation</em> is used on the stream (except if it is itself such an operation).
 */
public class ExIntStream implements IntStream {

    @Delegate
    private final IntStream stream;

    private ExIntStream(IntStream stream) {
        this.stream = stream;
    }

    /**
     * Create an instance from an existing {@code IntStream}.
     *
     * @param stream existing stream
     * @return instance from an existing {@code IntStream}
     */
    public static ExIntStream of(IntStream stream) {
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
    public <U> ExStream<U> mapToObj(IntFunction<? extends U> mapper) {
        return ExStream.of(stream.mapToObj(mapper));
    }

    @Override
    public ExStream<Integer> boxed() {
        return ExStream.of(stream.boxed());
    }

    /* Override all methods that usually return DoubleStream to return an ExDoubleStream. */

    @Override
    public ExDoubleStream mapToDouble(IntToDoubleFunction mapper) {
        return ExDoubleStream.of(stream.mapToDouble(mapper));
    }

    @Override
    public ExDoubleStream asDoubleStream() {
        return ExDoubleStream.of(stream.asDoubleStream());
    }

    /* Override all methods that usually return IntStream to return an ExIntStream. */

    @Override
    public ExIntStream filter(IntPredicate predicate) {
        return of(stream.filter(predicate));
    }

    @Override
    public ExIntStream map(IntUnaryOperator mapper) {
        return of(stream.map(mapper));
    }

    @Override
    public ExIntStream flatMap(IntFunction<? extends IntStream> mapper) {
        return of(stream.flatMap(mapper));
    }

    @Override
    public ExIntStream distinct() {
        return of(stream.distinct());
    }

    @Override
    public ExIntStream sorted() {
        return of(stream.sorted());
    }

    @Override
    public ExIntStream peek(IntConsumer action) {
        return of(stream.peek(action));
    }

    @Override
    public ExIntStream limit(long maxSize) {
        return of(stream.limit(maxSize));
    }

    @Override
    public ExIntStream skip(long n) {
        return of(stream.skip(n));
    }

    @Override
    public ExIntStream sequential() {
        return of(stream.sequential());
    }

    @Override
    public ExIntStream parallel() {
        return of(stream.parallel());
    }

    @Override
    public ExIntStream unordered() {
        return of(stream.unordered());
    }

    @Override
    public ExIntStream onClose(Runnable closeHandler) {
        return of(stream.onClose(closeHandler));
    }

    /* Override all methods that usually return LongStream to return an ExLongStream. */

    @Override
    public ExLongStream mapToLong(IntToLongFunction mapper) {
        return ExLongStream.of(stream.mapToLong(mapper));
    }

    @Override
    public ExLongStream asLongStream() {
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
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param predicate      see {@link IntStream#filter}
     * @return see {@link IntStream#filter}
     */
    public <E extends Exception> ExIntStream filter(Class<E> exceptionClass, ExIntPredicate<? extends E> predicate) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
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
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link IntStream#map}
     * @return see {@link IntStream#map}
     */
    public <E extends Exception> ExIntStream map(Class<E> exceptionClass, ExIntUnaryOperator<? extends E> mapper) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
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
     * @param <U>            the element type of the new stream
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link IntStream#mapToObj}
     * @return see {@link IntStream#mapToObj}
     */
    public <U, E extends Exception> ExStream<U> mapToObj(Class<E> exceptionClass, ExIntFunction<? extends U, ? extends E> mapper) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
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
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link IntStream#mapToDouble}
     * @return see {@link IntStream#mapToDouble}
     */
    public <E extends Exception> ExDoubleStream mapToDouble(Class<E> exceptionClass, ExIntToDoubleFunction<? extends E> mapper) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
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
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link IntStream#mapToLong}
     * @return see {@link IntStream#mapToLong}
     */
    public <E extends Exception> ExLongStream mapToLong(Class<E> exceptionClass, ExIntToLongFunction<? extends E> mapper) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
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
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link IntStream#flatMap}
     * @return see {@link IntStream#flatMap}
     */
    public <E extends Exception> ExIntStream flatMap(Class<E> exceptionClass,
                                                     ExIntFunction<? extends IntStream, ? extends E> mapper) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
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
     * @param <E>            The exception type thrown by {@code mapper}
     * @param exceptionClass The exception class for {@link E}
     * @param mapper         see {@link IntStream#mapMulti}
     * @return see {@link IntStream#mapMulti}
     */
    public <E extends Exception> ExIntStream mapMulti(Class<E> exceptionClass, ExIntMapMultiConsumer<? extends E> mapper) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return of(mapMulti(mapper.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#peek}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code action}
     * @param exceptionClass The exception class for {@link E}
     * @param action         see {@link IntStream#peek}
     * @return see {@link IntStream#peek}
     */
    public <E extends Exception> ExIntStream peek(Class<E> exceptionClass, ExIntConsumer<? extends E> action) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return of(peek(action.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#takeWhile}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code predicate}
     * @param exceptionClass The exception class for {@link E}
     * @param predicate      see {@link IntStream#takeWhile}
     * @return see {@link IntStream#takeWhile}
     */
    public <E extends Exception> ExIntStream takeWhile(Class<E> exceptionClass, ExIntPredicate<? extends E> predicate) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return of(takeWhile(predicate.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#dropWhile}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code predicate}
     * @param exceptionClass The exception class for {@link E}
     * @param predicate      see {@link IntStream#dropWhile}
     * @return see {@link IntStream#dropWhile}
     */
    public <E extends Exception> ExIntStream dropWhile(Class<E> exceptionClass, ExIntPredicate<? extends E> predicate) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return of(dropWhile(predicate.wrap()));
    }

    /**
     * Equivalent of {@link IntStream#forEach}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code action}
     * @param exceptionClass The exception class for {@link E}
     * @param action         see {@link IntStream#forEach}
     */
    public <E extends Exception> void forEach(Class<E> exceptionClass, ExIntConsumer<? extends E> action) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        forEach(action.wrap());
    }

    /**
     * Equivalent of {@link IntStream#forEachOrdered}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code action}
     * @param exceptionClass The exception class for {@link E}
     * @param action         see {@link IntStream#forEachOrdered}
     */
    public <E extends Exception> void forEachOrdered(Class<E> exceptionClass, ExIntConsumer<? extends E> action) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        forEachOrdered(action.wrap());
    }

    /**
     * Equivalent of {@link IntStream#reduce(int, IntBinaryOperator)}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code op}
     * @param exceptionClass The exception class for {@link E}
     * @param identity       see {@link IntStream#reduce(int, IntBinaryOperator)}
     * @param op             see {@link IntStream#reduce(int, IntBinaryOperator)}
     * @return see {@link IntStream#reduce(int, IntBinaryOperator)}
     */
    public <E extends Exception> Integer reduce(Class<E> exceptionClass, Integer identity, ExIntBinaryOperator<? extends E> op) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return reduce(identity, op.wrap());
    }

    /**
     * Equivalent of {@link IntStream#reduce(IntBinaryOperator)}.
     * <p>
     * If {@code action} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code op}
     * @param exceptionClass The exception class for {@link E}
     * @param op             see {@link IntStream#reduce(IntBinaryOperator)}
     * @return see {@link IntStream#reduce(IntBinaryOperator)}
     */
    public <E extends Exception> OptionalInt reduce(Class<E> exceptionClass, ExIntBinaryOperator<? extends E> op) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return reduce(op.wrap());
    }

    /**
     * Equivalent of {@link IntStream#collect}.
     * <p>
     * If {@code supplier}, {@code accumulator} or {@code combiner} throw a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <R>            the type of the mutable result container
     * @param <E>            The exception type thrown by {@code supplier}, {@code accumulator} or {@code combiner}
     * @param exceptionClass The exception class for {@link E}
     * @param supplier       see {@link IntStream#collect}
     * @param accumulator    see {@link IntStream#collect}
     * @param combiner       see {@link IntStream#collect}
     * @return see {@link IntStream#collect}
     */
    public <R, E extends Exception> R collect(Class<E> exceptionClass, ExSupplier<R, ? extends E> supplier,
                                              ExObjIntConsumer<R, ? extends E> accumulator, ExBiConsumer<R, R, ? extends E> combiner) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return collect(supplier.wrap(), accumulator.wrap(), combiner.wrap());
    }

    /**
     * Equivalent of {@link IntStream#anyMatch}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code predicate}
     * @param exceptionClass The exception class for {@link E}
     * @param predicate      see {@link IntStream#anyMatch}
     * @return see {@link IntStream#anyMatch}
     */
    public <E extends Exception> boolean anyMatch(Class<E> exceptionClass, ExIntPredicate<? extends E> predicate) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return anyMatch(predicate.wrap());
    }

    /**
     * Equivalent of {@link IntStream#allMatch}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code predicate}
     * @param exceptionClass The exception class for {@link E}
     * @param predicate      see {@link IntStream#allMatch}
     * @return see {@link IntStream#allMatch}
     */
    public <E extends Exception> boolean allMatch(Class<E> exceptionClass, ExIntPredicate<? extends E> predicate) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return allMatch(predicate.wrap());
    }

    /**
     * Equivalent of {@link IntStream#noneMatch}.
     * <p>
     * If {@code predicate} throws a checked exception, a {@link ExException} will be thrown instead.
     * This will have the original exception as its {@link ExException#getCause() cause}.
     *
     * @param <E>            The exception type thrown by {@code predicate}
     * @param exceptionClass The exception class for {@link E}
     * @param predicate      see {@link IntStream#noneMatch}
     * @return see {@link IntStream#noneMatch}
     */
    public <E extends Exception> boolean noneMatch(Class<E> exceptionClass, ExIntPredicate<? extends E> predicate) {
        ExStreamUtils.verifyExceptionAllowed(exceptionClass);
        return noneMatch(predicate.wrap());
    }

}
