package net.dapete.exceptional.stream;

import net.dapete.exceptional.ExceptionalWrapper;
import net.dapete.exceptional.function.*;
import org.jspecify.annotations.NonNull;

import java.util.OptionalInt;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public final class ActiveExceptionalIntStream<E extends Exception> {

    @FunctionalInterface
    public interface ExceptionalIntMapMultiConsumer<E extends Exception> extends Wrappable<IntStream.IntMapMultiConsumer> {

        /**
         * Replaces the given {@code value} with zero or more values by feeding the mapped
         * values to the {@code ic} consumer.
         *
         * @param value the int value coming from upstream
         * @param ic    an {@code IntConsumer} accepting the mapped values
         * @throws E possibly
         */
        // TODO ic should be an ExceptionalIntConsumer, but then wrapping does not work
        void accept(int value, @NonNull IntConsumer ic) throws E;

        @Override
        default IntStream.IntMapMultiConsumer wrap() {
            return ExceptionalWrapper.wrap(this);
        }

    }

    private final @NonNull ExceptionalIntStream exceptionalStream;

    ActiveExceptionalIntStream(@NonNull ExceptionalIntStream exceptionalStream) {
        this.exceptionalStream = exceptionalStream;
    }

    /* Implement versions of all methods from IntStream that use functional interfaces, using their counterparts with Exceptions instead. */

    public @NonNull ExceptionalIntStream filter(@NonNull ExceptionalIntPredicate<E> predicate) {
        return ExceptionalIntStream.of(exceptionalStream.filter(predicate.wrap()));
    }

    public @NonNull ExceptionalIntStream map(@NonNull ExceptionalIntUnaryOperator<E> mapper) {
        return ExceptionalIntStream.of(exceptionalStream.map(mapper.wrap()));
    }

    public <U> @NonNull ExceptionalStream<U> mapToObj(@NonNull ExceptionalIntFunction<? extends U, E> mapper) {
        return ExceptionalStream.of(exceptionalStream.mapToObj(mapper.wrap()));
    }

    public @NonNull ExceptionalDoubleStream mapToDouble(@NonNull ExceptionalIntToDoubleFunction<E> mapper) {
        return ExceptionalDoubleStream.of(exceptionalStream.mapToDouble(mapper.wrap()));
    }

    public @NonNull ExceptionalLongStream mapToLong(@NonNull ExceptionalIntToLongFunction<E> mapper) {
        return ExceptionalLongStream.of(exceptionalStream.mapToLong(mapper.wrap()));
    }

    public @NonNull ExceptionalIntStream flatMap(@NonNull ExceptionalIntFunction<? extends IntStream, E> mapper) {
        return ExceptionalIntStream.of(exceptionalStream.flatMap(mapper.wrap()));
    }

    public @NonNull ExceptionalIntStream mapMulti(@NonNull ExceptionalIntMapMultiConsumer<E> mapper) {
        return ExceptionalIntStream.of(exceptionalStream.mapMulti(mapper.wrap()));
    }

    public @NonNull ExceptionalIntStream peek(@NonNull ExceptionalIntConsumer<E> action) {
        return ExceptionalIntStream.of(exceptionalStream.peek(action.wrap()));
    }

    public @NonNull ExceptionalIntStream takeWhile(@NonNull ExceptionalIntPredicate<E> predicate) {
        return ExceptionalIntStream.of(exceptionalStream.takeWhile(predicate.wrap()));
    }

    public @NonNull ExceptionalIntStream dropWhile(@NonNull ExceptionalIntPredicate<E> predicate) {
        return ExceptionalIntStream.of(exceptionalStream.dropWhile(predicate.wrap()));
    }

    public void forEach(@NonNull ExceptionalIntConsumer<E> action) {
        exceptionalStream.forEach(action.wrap());
    }

    public void forEachOrdered(@NonNull ExceptionalIntConsumer<E> action) {
        exceptionalStream.forEachOrdered(action.wrap());
    }

    public int reduce(int identity, @NonNull ExceptionalIntBinaryOperator<E> op) {
        return exceptionalStream.reduce(identity, op.wrap());
    }

    public OptionalInt reduce(@NonNull ExceptionalIntBinaryOperator<E> op) {
        return exceptionalStream.reduce(op.wrap());
    }

    public <R> R collect(@NonNull ExceptionalSupplier<R, E> supplier, @NonNull ExceptionalObjIntConsumer<R, E> accumulator,
                         @NonNull ExceptionalBiConsumer<R, R, E> combiner) {
        return exceptionalStream.collect(supplier.wrap(), accumulator.wrap(), combiner.wrap());
    }

    public boolean anyMatch(@NonNull ExceptionalIntPredicate<E> predicate) {
        return exceptionalStream.anyMatch(predicate.wrap());
    }

    public boolean allMatch(@NonNull ExceptionalIntPredicate<E> predicate) {
        return exceptionalStream.allMatch(predicate.wrap());
    }

    public boolean noneMatch(@NonNull ExceptionalIntPredicate<E> predicate) {
        return exceptionalStream.noneMatch(predicate.wrap());
    }

}
