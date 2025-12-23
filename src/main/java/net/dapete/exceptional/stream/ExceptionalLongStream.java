package net.dapete.exceptional.stream;

import lombok.experimental.Delegate;
import org.jspecify.annotations.NonNull;

import java.util.function.*;
import java.util.stream.LongStream;

public class ExceptionalLongStream implements LongStream {

    @Delegate
    private final @NonNull LongStream stream;

    private ExceptionalLongStream(@NonNull LongStream stream) {
        this.stream = stream;
    }

    /**
     * Create an instance from an existing LongStream.
     *
     * @param stream existing stream
     * @return instance from an existing LongStream
     */
    public static @NonNull ExceptionalLongStream of(@NonNull LongStream stream) {
        return new ExceptionalLongStream(stream);
    }

    public static ExceptionalLongStream empty() {
        return of(LongStream.empty());
    }

    public static ExceptionalLongStream of(long t) {
        return of(LongStream.of(t));
    }

    public static ExceptionalLongStream of(long... values) {
        return of(LongStream.of(values));
    }

    /**
     * Return an {@link ActiveExceptionalLongStream} for the same values that allows exception of type {@link Exception}.
     *
     * @return an {@link ActiveExceptionalLongStream} for the same values that allows exception of type {@link Exception}
     */
    public @NonNull ActiveExceptionalLongStream<Exception> wrapExceptions() {
        return new ActiveExceptionalLongStream<>(this);
    }

    /**
     * Return an {@link ActiveExceptionalLongStream} for the same values that allows exceptions of type {@link E}.
     *
     * @param <E>            the type of exceptions thrown
     * @param exceptionClass the class of the type of exceptions thrown
     * @return an {@link ActiveExceptionalLongStream} for the same values that allows exceptions of type {@link E}
     */
    public <E extends Exception> @NonNull ActiveExceptionalLongStream<E> wrapExceptions(@SuppressWarnings("unused") Class<E> exceptionClass) {
        return new ActiveExceptionalLongStream<>(this);
    }

    /* Override all methods that usually return Stream to return an ExceptionalStream. */

    @Override
    public <U> @NonNull ExceptionalStream<U> mapToObj(@NonNull LongFunction<? extends U> mapper) {
        return ExceptionalStream.of(stream.mapToObj(mapper));
    }

    @Override
    public @NonNull ExceptionalStream<Long> boxed() {
        return ExceptionalStream.of(stream.boxed());
    }

    /* Override all methods that usually return DoubleStream to return an ExceptionalDoubleStream. */

    @Override
    public @NonNull ExceptionalDoubleStream mapToDouble(@NonNull LongToDoubleFunction mapper) {
        return ExceptionalDoubleStream.of(stream.mapToDouble(mapper));
    }

    @Override
    public ExceptionalDoubleStream asDoubleStream() {
        return ExceptionalDoubleStream.of(stream.asDoubleStream());
    }

    /* Override all methods that usually return IntStream to return an ExceptionalIntStream. */

    @Override
    public @NonNull ExceptionalIntStream mapToInt(@NonNull LongToIntFunction mapper) {
        return ExceptionalIntStream.of(stream.mapToInt(mapper));
    }

    /* Override all methods that usually return LongStream to return an ExceptionalLongStream. */

    @Override
    public @NonNull ExceptionalLongStream filter(@NonNull LongPredicate predicate) {
        return of(stream.filter(predicate));
    }

    @Override
    public @NonNull ExceptionalLongStream map(@NonNull LongUnaryOperator mapper) {
        return of(stream.map(mapper));
    }

    @Override
    public @NonNull ExceptionalLongStream flatMap(@NonNull LongFunction<? extends LongStream> mapper) {
        return of(stream.flatMap(mapper));
    }

    @Override
    public @NonNull ExceptionalLongStream distinct() {
        return of(stream.distinct());
    }

    @Override
    public @NonNull ExceptionalLongStream sorted() {
        return of(stream.sorted());
    }

    @Override
    public @NonNull ExceptionalLongStream peek(@NonNull LongConsumer action) {
        return of(stream.peek(action));
    }

    @Override
    public @NonNull ExceptionalLongStream limit(long maxSize) {
        return of(stream.limit(maxSize));
    }

    @Override
    public @NonNull ExceptionalLongStream skip(long n) {
        return of(stream.skip(n));
    }

    @Override
    public @NonNull ExceptionalLongStream sequential() {
        return of(stream.sequential());
    }

    @Override
    public @NonNull ExceptionalLongStream parallel() {
        return of(stream.parallel());
    }

    @Override
    public @NonNull ExceptionalLongStream unordered() {
        return of(stream.unordered());
    }

    @Override
    public @NonNull ExceptionalLongStream onClose(@NonNull Runnable closeHandler) {
        return of(stream.onClose(closeHandler));
    }

}
