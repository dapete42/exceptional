package net.dapete.exceptional.stream;

import net.dapete.exceptional.ExceptionalWrapper;
import net.dapete.exceptional.function.*;
import org.jspecify.annotations.NonNull;

import java.util.OptionalDouble;
import java.util.function.DoubleConsumer;
import java.util.stream.DoubleStream;

public final class ActiveExceptionalDoubleStream<E extends Exception> {

    @FunctionalInterface
    public interface ExceptionalDoubleMapMultiConsumer<E extends Exception> extends Wrappable<DoubleStream.DoubleMapMultiConsumer> {

        /**
         * Replaces the given {@code value} with zero or more values by feeding the mapped
         * values to the {@code ic} consumer.
         *
         * @param value the double value coming from upstream
         * @param ic    an {@code DoubleConsumer} accepting the mapped values
         * @throws E possibly
         */
        // TODO ic should be an ExceptionalDoubleConsumer, but then wrapping does not work
        void accept(double value, @NonNull DoubleConsumer ic) throws E;

        @Override
        default DoubleStream.DoubleMapMultiConsumer wrap() {
            return ExceptionalWrapper.wrap(this);
        }

    }

    private final @NonNull ExceptionalDoubleStream exceptionalStream;

    ActiveExceptionalDoubleStream(@NonNull ExceptionalDoubleStream exceptionalStream) {
        this.exceptionalStream = exceptionalStream;
    }

    /* Implement versions of all methods from DoubleStream that use functional interfaces, using their counterparts with Exceptions instead. */

    public @NonNull ExceptionalDoubleStream filter(@NonNull ExceptionalDoublePredicate<E> predicate) {
        return ExceptionalDoubleStream.of(exceptionalStream.filter(predicate.wrap()));
    }

    public @NonNull ExceptionalDoubleStream map(@NonNull ExceptionalDoubleUnaryOperator<E> mapper) {
        return ExceptionalDoubleStream.of(exceptionalStream.map(mapper.wrap()));
    }

    public <U> @NonNull ExceptionalStream<U> mapToObj(@NonNull ExceptionalDoubleFunction<? extends U, E> mapper) {
        return ExceptionalStream.of(exceptionalStream.mapToObj(mapper.wrap()));
    }

    public @NonNull ExceptionalIntStream mapToInt(@NonNull ExceptionalDoubleToIntFunction<E> mapper) {
        return ExceptionalIntStream.of(exceptionalStream.mapToInt(mapper.wrap()));
    }

    public @NonNull ExceptionalLongStream mapToLong(@NonNull ExceptionalDoubleToLongFunction<E> mapper) {
        return ExceptionalLongStream.of(exceptionalStream.mapToLong(mapper.wrap()));
    }


    public @NonNull ExceptionalDoubleStream flatMap(@NonNull ExceptionalDoubleFunction<? extends DoubleStream, E> mapper) {
        return ExceptionalDoubleStream.of(exceptionalStream.flatMap(mapper.wrap()));
    }

    public @NonNull ExceptionalDoubleStream mapMulti(@NonNull ExceptionalDoubleMapMultiConsumer<E> mapper) {
        return ExceptionalDoubleStream.of(exceptionalStream.mapMulti(mapper.wrap()));
    }

    public @NonNull ExceptionalDoubleStream peek(@NonNull ExceptionalDoubleConsumer<E> action) {
        return ExceptionalDoubleStream.of(exceptionalStream.peek(action.wrap()));
    }

    public @NonNull ExceptionalDoubleStream takeWhile(@NonNull ExceptionalDoublePredicate<E> predicate) {
        return ExceptionalDoubleStream.of(exceptionalStream.takeWhile(predicate.wrap()));
    }

    public @NonNull ExceptionalDoubleStream dropWhile(@NonNull ExceptionalDoublePredicate<E> predicate) {
        return ExceptionalDoubleStream.of(exceptionalStream.dropWhile(predicate.wrap()));
    }

    public void forEach(@NonNull ExceptionalDoubleConsumer<E> action) {
        exceptionalStream.forEach(action.wrap());
    }

    public void forEachOrdered(@NonNull ExceptionalDoubleConsumer<E> action) {
        exceptionalStream.forEachOrdered(action.wrap());
    }

    public Double reduce(double identity, @NonNull ExceptionalDoubleBinaryOperator<E> op) {
        return exceptionalStream.reduce(identity, op.wrap());
    }

    public OptionalDouble reduce(@NonNull ExceptionalDoubleBinaryOperator<E> op) {
        return exceptionalStream.reduce(op.wrap());
    }

    public <R> R collect(@NonNull ExceptionalSupplier<R, E> supplier, @NonNull ExceptionalObjDoubleConsumer<R, E> accumulator,
                         @NonNull ExceptionalBiConsumer<R, R, E> combiner) {
        return exceptionalStream.collect(supplier.wrap(), accumulator.wrap(), combiner.wrap());
    }

    public boolean anyMatch(@NonNull ExceptionalDoublePredicate<E> predicate) {
        return exceptionalStream.anyMatch(predicate.wrap());
    }

    public boolean allMatch(@NonNull ExceptionalDoublePredicate<E> predicate) {
        return exceptionalStream.allMatch(predicate.wrap());
    }

    public boolean noneMatch(@NonNull ExceptionalDoublePredicate<E> predicate) {
        return exceptionalStream.noneMatch(predicate.wrap());
    }

}
