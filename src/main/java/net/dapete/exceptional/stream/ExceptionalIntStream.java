package net.dapete.exceptional.stream;

import lombok.experimental.Delegate;
import org.jspecify.annotations.NonNull;

import java.util.function.*;
import java.util.stream.IntStream;

public class ExceptionalIntStream implements IntStream {

    @Delegate
    private final @NonNull IntStream stream;

    private ExceptionalIntStream(@NonNull IntStream stream) {
        this.stream = stream;
    }

    /**
     * Create an instance from an existing IntStream.
     *
     * @param stream existing stream
     * @return instance from an existing IntStream
     */
    public static @NonNull ExceptionalIntStream of(@NonNull IntStream stream) {
        return new ExceptionalIntStream(stream);
    }

    public static ExceptionalIntStream empty() {
        return of(IntStream.empty());
    }

    public static ExceptionalIntStream of(int t) {
        return of(IntStream.of(t));
    }

    public static ExceptionalIntStream of(int... values) {
        return of(IntStream.of(values));
    }

    /**
     * Return an {@link ActiveExceptionalIntStream} for the same values that allows exception of type {@link Exception}.
     *
     * @param <E> the type of exceptions thrown
     * @return an {@link ActiveExceptionalIntStream} for the same values that allows exception of type {@link Exception}
     */
    public <E extends Exception> @NonNull ActiveExceptionalIntStream<E> wrapExceptions() {
        return new ActiveExceptionalIntStream<>(this);
    }

    /**
     * Return an {@link ActiveExceptionalIntStream} for the same values that allows exceptions of type {@link E}.
     *
     * @param <E>            the type of exceptions thrown
     * @param exceptionClass the class of the type of exceptions thrown
     * @return an {@link ActiveExceptionalIntStream} for the same values that allows exceptions of type {@link E}
     */
    public <E extends Exception> @NonNull ActiveExceptionalIntStream<E> wrapExceptions(@SuppressWarnings("unused") @NonNull Class<E> exceptionClass) {
        return new ActiveExceptionalIntStream<>(this);
    }

    /* Override all methods that usually return Stream to return an ExceptionalStream. */

    @Override
    public <U> @NonNull ExceptionalStream<U> mapToObj(@NonNull IntFunction<? extends U> mapper) {
        return ExceptionalStream.of(stream.mapToObj(mapper));
    }

    @Override
    public @NonNull ExceptionalStream<Integer> boxed() {
        return ExceptionalStream.of(stream.boxed());
    }

    /* Override all methods that usually return DoubleStream to return an ExceptionalDoubleStream. */

    @Override
    public @NonNull ExceptionalDoubleStream mapToDouble(@NonNull IntToDoubleFunction mapper) {
        return ExceptionalDoubleStream.of(stream.mapToDouble(mapper));
    }

    @Override
    public @NonNull ExceptionalDoubleStream asDoubleStream() {
        return ExceptionalDoubleStream.of(stream.asDoubleStream());
    }

    /* Override all methods that usually return IntStream to return an ExceptionalIntStream. */

    @Override
    public @NonNull ExceptionalIntStream filter(@NonNull IntPredicate predicate) {
        return of(stream.filter(predicate));
    }

    @Override
    public @NonNull ExceptionalIntStream map(@NonNull IntUnaryOperator mapper) {
        return of(stream.map(mapper));
    }

    @Override
    public @NonNull ExceptionalIntStream flatMap(@NonNull IntFunction<? extends IntStream> mapper) {
        return of(stream.flatMap(mapper));
    }

    @Override
    public @NonNull ExceptionalIntStream distinct() {
        return of(stream.distinct());
    }

    @Override
    public @NonNull ExceptionalIntStream sorted() {
        return of(stream.sorted());
    }

    @Override
    public @NonNull ExceptionalIntStream peek(@NonNull IntConsumer action) {
        return of(stream.peek(action));
    }

    @Override
    public @NonNull ExceptionalIntStream limit(long maxSize) {
        return of(stream.limit(maxSize));
    }

    @Override
    public @NonNull ExceptionalIntStream skip(long n) {
        return of(stream.skip(n));
    }

    @Override
    public @NonNull ExceptionalIntStream sequential() {
        return of(stream.sequential());
    }

    @Override
    public @NonNull ExceptionalIntStream parallel() {
        return of(stream.parallel());
    }

    @Override
    public @NonNull ExceptionalIntStream unordered() {
        return of(stream.unordered());
    }

    @Override
    public @NonNull ExceptionalIntStream onClose(@NonNull Runnable closeHandler) {
        return of(stream.onClose(closeHandler));
    }

    /* Override all methods that usually return LongStream to return an ExceptionalLongStream. */

    @Override
    public @NonNull ExceptionalLongStream mapToLong(@NonNull IntToLongFunction mapper) {
        return ExceptionalLongStream.of(stream.mapToLong(mapper));
    }

    @Override
    public @NonNull ExceptionalLongStream asLongStream() {
        return ExceptionalLongStream.of(stream.asLongStream());
    }

}
