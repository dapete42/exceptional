package net.dapete.exceptional.stream;

import lombok.experimental.Delegate;
import net.dapete.exceptional.function.*;
import org.jspecify.annotations.NonNull;

import java.util.OptionalDouble;
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

    /* Override all methods that usually return Stream to return an ExceptionalStream */

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

    /* Implement versions of all methods from DoubleStream that use functional interfaces, using their counterparts with Exceptions instead. */

    public @NonNull ExceptionalDoubleStream exceptionalFilter(@NonNull ExceptionalDoublePredicate<?> predicate) {
        return ExceptionalDoubleStream.of(filter(predicate.wrap()));
    }

    public @NonNull ExceptionalDoubleStream exceptionalMap(@NonNull ExceptionalDoubleUnaryOperator<?> mapper) {
        return ExceptionalDoubleStream.of(map(mapper.wrap()));
    }

    public <U> @NonNull ExceptionalStream<U> exceptionalMapToObj(@NonNull ExceptionalDoubleFunction<? extends U, ?> mapper) {
        return ExceptionalStream.of(mapToObj(mapper.wrap()));
    }

    public @NonNull ExceptionalIntStream exceptionalMapToInt(@NonNull ExceptionalDoubleToIntFunction<?> mapper) {
        return ExceptionalIntStream.of(mapToInt(mapper.wrap()));
    }

    public @NonNull ExceptionalLongStream exceptionalMapToLong(@NonNull ExceptionalDoubleToLongFunction<?> mapper) {
        return ExceptionalLongStream.of(mapToLong(mapper.wrap()));
    }


    public @NonNull ExceptionalDoubleStream exceptionalFlatMap(@NonNull ExceptionalDoubleFunction<? extends DoubleStream, ?> mapper) {
        return ExceptionalDoubleStream.of(flatMap(mapper.wrap()));
    }

    public @NonNull ExceptionalDoubleStream exceptionalMapMulti(@NonNull ExceptionalDoubleMapMultiConsumer<?> mapper) {
        return ExceptionalDoubleStream.of(mapMulti(mapper.wrap()));
    }

    public @NonNull ExceptionalDoubleStream exceptionalPeek(@NonNull ExceptionalDoubleConsumer<?> action) {
        return ExceptionalDoubleStream.of(peek(action.wrap()));
    }

    public @NonNull ExceptionalDoubleStream exceptionalTakeWhile(@NonNull ExceptionalDoublePredicate<?> predicate) {
        return ExceptionalDoubleStream.of(takeWhile(predicate.wrap()));
    }

    public @NonNull ExceptionalDoubleStream exceptionalDropWhile(@NonNull ExceptionalDoublePredicate<?> predicate) {
        return ExceptionalDoubleStream.of(dropWhile(predicate.wrap()));
    }

    public void exceptionalForEach(@NonNull ExceptionalDoubleConsumer<?> action) {
        forEach(action.wrap());
    }

    public void exceptionalForEachOrdered(@NonNull ExceptionalDoubleConsumer<?> action) {
        forEachOrdered(action.wrap());
    }

    public Double exceptionalReduce(double identity, @NonNull ExceptionalDoubleBinaryOperator<?> op) {
        return reduce(identity, op.wrap());
    }

    public OptionalDouble exceptionalReduce(@NonNull ExceptionalDoubleBinaryOperator<?> op) {
        return reduce(op.wrap());
    }

    public <R> R exceptionalCollect(@NonNull ExceptionalSupplier<R, ?> supplier, @NonNull ExceptionalObjDoubleConsumer<R, ?> accumulator,
                                    @NonNull ExceptionalBiConsumer<R, R, ?> combiner) {
        return collect(supplier.wrap(), accumulator.wrap(), combiner.wrap());
    }

    public boolean exceptionalAnyMatch(@NonNull ExceptionalDoublePredicate<?> predicate) {
        return anyMatch(predicate.wrap());
    }

    public boolean exceptionalAllMatch(@NonNull ExceptionalDoublePredicate<?> predicate) {
        return allMatch(predicate.wrap());
    }

    public boolean exceptionalNoneMatch(@NonNull ExceptionalDoublePredicate<?> predicate) {
        return noneMatch(predicate.wrap());
    }

}
