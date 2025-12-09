package net.dapete.exceptional;

import net.dapete.exceptional.function.*;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static net.dapete.exceptional.ExceptionalWrapper.wrap;

public final class OnlyExceptionalStream<T, E extends Exception> {

    private final ExceptionalStream<T> exceptionalStream;

    OnlyExceptionalStream(ExceptionalStream<T> exceptionalStream) {
        this.exceptionalStream = exceptionalStream;
    }

    /* Implement versions of all methods from Stream that use lambdas, using lambdas with Exceptions instead. */

    public ExceptionalStream<T> filter(ExceptionalPredicate<? super T, ? extends E> predicate) {
        return exceptionalStream.filter(wrap(predicate));
    }

    public <R> ExceptionalStream<R> map(ExceptionalFunction<? super T, ? extends R, ? extends E> mapper) {
        return exceptionalStream.map(wrap(mapper));
    }

    public IntStream mapToInt(ExceptionalToIntFunction<? super T, ? extends E> mapper) {
        return exceptionalStream.mapToInt(wrap(mapper));
    }

    public LongStream mapToLong(ExceptionalToLongFunction<? super T, ? extends E> mapper) {
        return exceptionalStream.mapToLong(wrap(mapper));
    }

    public DoubleStream mapToDouble(ExceptionalToDoubleFunction<? super T, ? extends E> mapper) {
        return exceptionalStream.mapToDouble(wrap(mapper));
    }

    public <R> ExceptionalStream<R> flatMap(ExceptionalFunction<? super T, ? extends Stream<? extends R>, ? extends E> mapper) {
        return exceptionalStream.flatMap(wrap(mapper));
    }

    public IntStream flatMapToInt(ExceptionalFunction<? super T, ? extends IntStream, ? extends E> mapper) {
        return exceptionalStream.flatMapToInt(wrap(mapper));
    }

    public LongStream flatMapToLong(ExceptionalFunction<? super T, ? extends LongStream, ? extends E> mapper) {
        return exceptionalStream.flatMapToLong(wrap(mapper));
    }

    public DoubleStream flatMapToDouble(ExceptionalFunction<? super T, ? extends DoubleStream, ? extends E> mapper) {
        return exceptionalStream.flatMapToDouble(wrap(mapper));
    }

    public <R> ExceptionalStream<R> mapMulti(ExceptionalBiConsumer<? super T, ? super Consumer<R>, ? extends E> mapper) {
        return exceptionalStream.mapMulti(wrap(mapper));
    }

    public IntStream mapMultiToInt(ExceptionalBiConsumer<? super T, ? super IntConsumer, ? extends E> mapper) {
        return exceptionalStream.mapMultiToInt(wrap(mapper));
    }

    public LongStream mapMultiToLong(ExceptionalBiConsumer<? super T, ? super LongConsumer, ? extends E> mapper) {
        return exceptionalStream.mapMultiToLong(wrap(mapper));
    }

    public DoubleStream mapMultiToDouble(ExceptionalBiConsumer<? super T, ? super DoubleConsumer, ? extends E> mapper) {
        return exceptionalStream.mapMultiToDouble(wrap(mapper));
    }

    public ExceptionalStream<T> peek(ExceptionalConsumer<? super T, ? extends E> action) {
        return exceptionalStream.peek(wrap(action));
    }

    public ExceptionalStream<T> takeWhile(ExceptionalPredicate<? super T, ? extends E> predicate) {
        return exceptionalStream.takeWhile(wrap(predicate));
    }

    public ExceptionalStream<T> dropWhile(ExceptionalPredicate<? super T, ? extends E> predicate) {
        return exceptionalStream.dropWhile(wrap(predicate));
    }

    public void forEach(ExceptionalConsumer<? super T, ? extends E> action) {
        exceptionalStream.forEach(wrap(action));
    }

    public void forEachOrdered(ExceptionalConsumer<? super T, ? extends E> action) {
        exceptionalStream.forEachOrdered(wrap(action));
    }

    public Optional<T> reduce(ExceptionalBinaryOperator<T, ? extends E> accumulator) {
        return exceptionalStream.reduce(wrap(accumulator));
    }

    public <U> U reduce(U identity, ExceptionalBiFunction<U, ? super T, U, ? extends E> accumulator, ExceptionalBinaryOperator<U, ? extends E> combiner) {
        return exceptionalStream.reduce(identity, wrap(accumulator), wrap(combiner));
    }

    public <R> R collect(ExceptionalSupplier<R, ? extends E> supplier, ExceptionalBiConsumer<R, ? super T, ? extends E> accumulator, ExceptionalBiConsumer<R, R, ? extends E> combiner) {
        return exceptionalStream.collect(wrap(supplier), wrap(accumulator), wrap(combiner));
    }

}
