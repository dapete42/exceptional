package net.dapete.exceptional.stream;

import lombok.experimental.Delegate;
import net.dapete.exceptional.function.*;
import org.jspecify.annotations.NonNull;

import java.util.OptionalInt;
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

    /* Implement versions of all methods from IntStream that use functional interfaces, using their counterparts with Exceptions instead. */

    public @NonNull ExceptionalIntStream exceptionalFilter(@NonNull ExceptionalIntPredicate<?> predicate) {
        return ExceptionalIntStream.of(filter(predicate.wrap()));
    }

    public @NonNull ExceptionalIntStream exceptionalMap(@NonNull ExceptionalIntUnaryOperator<?> mapper) {
        return ExceptionalIntStream.of(map(mapper.wrap()));
    }

    public <U> @NonNull ExceptionalStream<U> exceptionalMapToObj(@NonNull ExceptionalIntFunction<? extends U, ?> mapper) {
        return ExceptionalStream.of(mapToObj(mapper.wrap()));
    }

    public @NonNull ExceptionalDoubleStream exceptionalMapToDouble(@NonNull ExceptionalIntToDoubleFunction<?> mapper) {
        return ExceptionalDoubleStream.of(mapToDouble(mapper.wrap()));
    }

    public @NonNull ExceptionalLongStream exceptionalMapToLong(@NonNull ExceptionalIntToLongFunction<?> mapper) {
        return ExceptionalLongStream.of(mapToLong(mapper.wrap()));
    }

    public @NonNull ExceptionalIntStream exceptionalFlatMap(@NonNull ExceptionalIntFunction<? extends IntStream, ?> mapper) {
        return ExceptionalIntStream.of(flatMap(mapper.wrap()));
    }

    public @NonNull ExceptionalIntStream exceptionalMapMulti(@NonNull ExceptionalIntMapMultiConsumer<?> mapper) {
        return ExceptionalIntStream.of(mapMulti(mapper.wrap()));
    }

    public @NonNull ExceptionalIntStream exceptionalPeek(@NonNull ExceptionalIntConsumer<?> action) {
        return ExceptionalIntStream.of(peek(action.wrap()));
    }

    public @NonNull ExceptionalIntStream exceptionalTakeWhile(@NonNull ExceptionalIntPredicate<?> predicate) {
        return ExceptionalIntStream.of(takeWhile(predicate.wrap()));
    }

    public @NonNull ExceptionalIntStream exceptionalDropWhile(@NonNull ExceptionalIntPredicate<?> predicate) {
        return ExceptionalIntStream.of(dropWhile(predicate.wrap()));
    }

    public void exceptionalForEach(@NonNull ExceptionalIntConsumer<?> action) {
        forEach(action.wrap());
    }

    public void exceptionalForEachOrdered(@NonNull ExceptionalIntConsumer<?> action) {
        forEachOrdered(action.wrap());
    }

    public int exceptionalReduce(int identity, @NonNull ExceptionalIntBinaryOperator<?> op) {
        return reduce(identity, op.wrap());
    }

    public OptionalInt exceptionalReduce(@NonNull ExceptionalIntBinaryOperator<?> op) {
        return reduce(op.wrap());
    }

    public <R> R exceptionalCollect(@NonNull ExceptionalSupplier<R, ?> supplier, @NonNull ExceptionalObjIntConsumer<R, ?> accumulator,
                                    @NonNull ExceptionalBiConsumer<R, R, ?> combiner) {
        return collect(supplier.wrap(), accumulator.wrap(), combiner.wrap());
    }

    public boolean exceptionalAnyMatch(@NonNull ExceptionalIntPredicate<?> predicate) {
        return anyMatch(predicate.wrap());
    }

    public boolean exceptionalAllMatch(@NonNull ExceptionalIntPredicate<?> predicate) {
        return allMatch(predicate.wrap());
    }

    public boolean exceptionalNoneMatch(@NonNull ExceptionalIntPredicate<?> predicate) {
        return noneMatch(predicate.wrap());
    }

}
