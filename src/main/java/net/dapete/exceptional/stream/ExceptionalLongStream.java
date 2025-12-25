package net.dapete.exceptional.stream;

import lombok.experimental.Delegate;
import net.dapete.exceptional.function.*;
import org.jspecify.annotations.NonNull;

import java.util.OptionalLong;
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

    /* Implement versions of all methods from LongStream that use functional interfaces, using their counterparts with Exceptions instead. */

    public @NonNull ExceptionalLongStream exceptionalFilter(@NonNull ExceptionalLongPredicate<?> predicate) {
        return ExceptionalLongStream.of(filter(predicate.wrap()));
    }

    public @NonNull ExceptionalLongStream exceptionalMap(@NonNull ExceptionalLongUnaryOperator<?> mapper) {
        return ExceptionalLongStream.of(map(mapper.wrap()));
    }

    public <U> @NonNull ExceptionalStream<U> exceptionalMapToObj(@NonNull ExceptionalLongFunction<? extends U, ?> mapper) {
        return ExceptionalStream.of(mapToObj(mapper.wrap()));
    }

    public @NonNull ExceptionalIntStream exceptionalMapToInt(@NonNull ExceptionalLongToIntFunction<?> mapper) {
        return ExceptionalIntStream.of(mapToInt(mapper.wrap()));
    }

    public @NonNull ExceptionalDoubleStream exceptionalMapToDouble(@NonNull ExceptionalLongToDoubleFunction<?> mapper) {
        return ExceptionalDoubleStream.of(mapToDouble(mapper.wrap()));
    }

    public @NonNull ExceptionalLongStream exceptionalFlatMap(@NonNull ExceptionalLongFunction<? extends LongStream, ?> mapper) {
        return ExceptionalLongStream.of(flatMap(mapper.wrap()));
    }

    public @NonNull ExceptionalLongStream exceptionalMapMulti(@NonNull ExceptionalLongMapMultiConsumer<?> mapper) {
        return ExceptionalLongStream.of(mapMulti(mapper.wrap()));
    }

    public @NonNull ExceptionalLongStream exceptionalPeek(@NonNull ExceptionalLongConsumer<?> action) {
        return ExceptionalLongStream.of(peek(action.wrap()));
    }

    public @NonNull ExceptionalLongStream exceptionalTakeWhile(@NonNull ExceptionalLongPredicate<?> predicate) {
        return ExceptionalLongStream.of(takeWhile(predicate.wrap()));
    }

    public @NonNull ExceptionalLongStream exceptionalDropWhile(@NonNull ExceptionalLongPredicate<?> predicate) {
        return ExceptionalLongStream.of(dropWhile(predicate.wrap()));
    }

    public void exceptionalForEach(@NonNull ExceptionalLongConsumer<?> action) {
        forEach(action.wrap());
    }

    public void exceptionalForEachOrdered(@NonNull ExceptionalLongConsumer<?> action) {
        forEachOrdered(action.wrap());
    }

    public Long exceptionalReduce(Long identity, @NonNull ExceptionalLongBinaryOperator<?> op) {
        return reduce(identity, op.wrap());
    }

    public OptionalLong exceptionalReduce(@NonNull ExceptionalLongBinaryOperator<?> op) {
        return reduce(op.wrap());
    }

    public <R> R exceptionalCollect(@NonNull ExceptionalSupplier<R, ?> supplier, @NonNull ExceptionalObjLongConsumer<R, ?> accumulator,
                                    @NonNull ExceptionalBiConsumer<R, R, ?> combiner) {
        return collect(supplier.wrap(), accumulator.wrap(), combiner.wrap());
    }

    public boolean exceptionalAnyMatch(@NonNull ExceptionalLongPredicate<?> predicate) {
        return anyMatch(predicate.wrap());
    }

    public boolean exceptionalAllMatch(@NonNull ExceptionalLongPredicate<?> predicate) {
        return allMatch(predicate.wrap());
    }

    public boolean exceptionalNoneMatch(@NonNull ExceptionalLongPredicate<?> predicate) {
        return noneMatch(predicate.wrap());
    }

}
