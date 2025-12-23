package net.dapete.exceptional.stream;

import lombok.experimental.Delegate;
import org.jspecify.annotations.NonNull;

import java.util.function.*;
import java.util.stream.DoubleStream;

public class ExceptionalDoubleStream implements DoubleStream {

    @Delegate
    private final @NonNull DoubleStream stream;

    private ExceptionalDoubleStream(@NonNull DoubleStream stream) {
        this.stream = stream;
    }

    /**
     * Create an instance from an existing LongStream.
     *
     * @param stream existing stream
     * @return instance from an existing LongStream
     */
    public static @NonNull ExceptionalDoubleStream of(@NonNull DoubleStream stream) {
        return new ExceptionalDoubleStream(stream);
    }

    public static ExceptionalDoubleStream empty() {
        return of(DoubleStream.empty());
    }

    public static ExceptionalDoubleStream of(double t) {
        return of(DoubleStream.of(t));
    }

    public static ExceptionalDoubleStream of(double... values) {
        return of(DoubleStream.of(values));
    }

    /**
     * Return an {@link ActiveExceptionalDoubleStream} for the same values that allows exception of type {@link Exception}.
     *
     * @return an {@link ActiveExceptionalDoubleStream} for the same values that allows exception of type {@link Exception}
     */
    public @NonNull ActiveExceptionalDoubleStream<Exception> wrapExceptions() {
        return new ActiveExceptionalDoubleStream<>(this);
    }

    /**
     * Return an {@link ActiveExceptionalDoubleStream} for the same values that allows exceptions of type {@link E}.
     *
     * @param <E>            the type of exceptions thrown
     * @param exceptionClass the class of the type of exceptions thrown
     * @return an {@link ActiveExceptionalDoubleStream} for the same values that allows exceptions of type {@link E}
     */
    public <E extends Exception> @NonNull ActiveExceptionalDoubleStream<E> wrapExceptions(@SuppressWarnings("unused") Class<E> exceptionClass) {
        return new ActiveExceptionalDoubleStream<>(this);
    }

    /* Override all methods that usually return Stream to return an ExceptionalStream. */

    @Override
    public <U> @NonNull ExceptionalStream<U> mapToObj(@NonNull DoubleFunction<? extends U> mapper) {
        return ExceptionalStream.of(stream.mapToObj(mapper));
    }

    @Override
    public @NonNull ExceptionalStream<Double> boxed() {
        return ExceptionalStream.of(stream.boxed());
    }

    /* Override all methods that usually return DoubleStream to return an ExceptionalDoubleStream. */

    @Override
    public @NonNull ExceptionalDoubleStream filter(@NonNull DoublePredicate predicate) {
        return of(stream.filter(predicate));
    }

    @Override
    public @NonNull ExceptionalDoubleStream map(@NonNull DoubleUnaryOperator mapper) {
        return of(stream.map(mapper));
    }

    @Override
    public @NonNull ExceptionalDoubleStream flatMap(@NonNull DoubleFunction<? extends DoubleStream> mapper) {
        return of(stream.flatMap(mapper));
    }

    @Override
    public @NonNull ExceptionalDoubleStream distinct() {
        return of(stream.distinct());
    }

    @Override
    public @NonNull ExceptionalDoubleStream sorted() {
        return of(stream.sorted());
    }

    @Override
    public @NonNull ExceptionalDoubleStream peek(@NonNull DoubleConsumer action) {
        return of(stream.peek(action));
    }

    @Override
    public @NonNull ExceptionalDoubleStream limit(long maxSize) {
        return of(stream.limit(maxSize));
    }

    @Override
    public @NonNull ExceptionalDoubleStream skip(long n) {
        return of(stream.skip(n));
    }

    @Override
    public @NonNull ExceptionalDoubleStream sequential() {
        return of(stream.sequential());
    }

    @Override
    public @NonNull ExceptionalDoubleStream parallel() {
        return of(stream.parallel());
    }

    @Override
    public @NonNull ExceptionalDoubleStream unordered() {
        return of(stream.unordered());
    }

    @Override
    public @NonNull ExceptionalDoubleStream onClose(@NonNull Runnable closeHandler) {
        return of(stream.onClose(closeHandler));
    }

    /* Override all methods that usually return IntStream to return an ExceptionalIntStream. */

    @Override
    public @NonNull ExceptionalIntStream mapToInt(@NonNull DoubleToIntFunction mapper) {
        return ExceptionalIntStream.of(stream.mapToInt(mapper));
    }

    /* Override all methods that usually return LongStream to return an ExceptionalLongStream. */

    @Override
    public @NonNull ExceptionalLongStream mapToLong(@NonNull DoubleToLongFunction mapper) {
        return ExceptionalLongStream.of(stream.mapToLong(mapper));
    }

}
