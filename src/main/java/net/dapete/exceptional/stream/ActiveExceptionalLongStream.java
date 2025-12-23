package net.dapete.exceptional.stream;

import net.dapete.exceptional.ExceptionalWrapper;
import net.dapete.exceptional.function.*;
import org.jspecify.annotations.NonNull;

import java.util.OptionalLong;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;

public final class ActiveExceptionalLongStream<E extends Exception> {

    @FunctionalInterface
    public interface ExceptionalLongMapMultiConsumer<E extends Exception> extends Wrappable<LongStream.LongMapMultiConsumer> {

        /**
         * Replaces the given {@code value} with zero or more values by feeding the mapped
         * values to the {@code ic} consumer.
         *
         * @param value the long value coming from upstream
         * @param ic    an {@code LongConsumer} accepting the mapped values
         * @throws E possibly
         */
        // TODO ic should be an ExceptionalLongConsumer, but then wrapping does not work
        void accept(long value, @NonNull LongConsumer ic) throws E;

        @Override
        default LongStream.LongMapMultiConsumer wrap() {
            return ExceptionalWrapper.wrap(this);
        }

    }

    private final @NonNull ExceptionalLongStream exceptionalStream;

    ActiveExceptionalLongStream(@NonNull ExceptionalLongStream exceptionalStream) {
        this.exceptionalStream = exceptionalStream;
    }

    /* Implement versions of all methods from LongStream that use functional interfaces, using their counterparts with Exceptions instead. */

    public @NonNull ExceptionalLongStream filter(@NonNull ExceptionalLongPredicate<E> predicate) {
        return ExceptionalLongStream.of(exceptionalStream.filter(predicate.wrap()));
    }

    public @NonNull ExceptionalLongStream map(@NonNull ExceptionalLongUnaryOperator<E> mapper) {
        return ExceptionalLongStream.of(exceptionalStream.map(mapper.wrap()));
    }

    public <U> @NonNull ExceptionalStream<U> mapToObj(@NonNull ExceptionalLongFunction<? extends U, E> mapper) {
        return ExceptionalStream.of(exceptionalStream.mapToObj(mapper.wrap()));
    }

    public @NonNull ExceptionalIntStream mapToInt(@NonNull ExceptionalLongToIntFunction<E> mapper) {
        return ExceptionalIntStream.of(exceptionalStream.mapToInt(mapper.wrap()));
    }

    public @NonNull ExceptionalDoubleStream mapToDouble(@NonNull ExceptionalLongToDoubleFunction<E> mapper) {
        return ExceptionalDoubleStream.of(exceptionalStream.mapToDouble(mapper.wrap()));
    }

    public @NonNull ExceptionalLongStream flatMap(@NonNull ExceptionalLongFunction<? extends LongStream, E> mapper) {
        return ExceptionalLongStream.of(exceptionalStream.flatMap(mapper.wrap()));
    }

    public @NonNull ExceptionalLongStream mapMulti(@NonNull ExceptionalLongMapMultiConsumer<E> mapper) {
        return ExceptionalLongStream.of(exceptionalStream.mapMulti(mapper.wrap()));
    }

    public @NonNull ExceptionalLongStream peek(@NonNull ExceptionalLongConsumer<E> action) {
        return ExceptionalLongStream.of(exceptionalStream.peek(action.wrap()));
    }

    public @NonNull ExceptionalLongStream takeWhile(@NonNull ExceptionalLongPredicate<E> predicate) {
        return ExceptionalLongStream.of(exceptionalStream.takeWhile(predicate.wrap()));
    }

    public @NonNull ExceptionalLongStream dropWhile(@NonNull ExceptionalLongPredicate<E> predicate) {
        return ExceptionalLongStream.of(exceptionalStream.dropWhile(predicate.wrap()));
    }

    public void forEach(@NonNull ExceptionalLongConsumer<E> action) {
        exceptionalStream.forEach(action.wrap());
    }

    public void forEachOrdered(@NonNull ExceptionalLongConsumer<E> action) {
        exceptionalStream.forEachOrdered(action.wrap());
    }

    public Long reduce(Long identity, @NonNull ExceptionalLongBinaryOperator<E> op) {
        return exceptionalStream.reduce(identity, op.wrap());
    }

    public OptionalLong reduce(@NonNull ExceptionalLongBinaryOperator<E> op) {
        return exceptionalStream.reduce(op.wrap());
    }

    public <R> R collect(@NonNull ExceptionalSupplier<R, E> supplier, @NonNull ExceptionalObjLongConsumer<R, E> accumulator,
                         @NonNull ExceptionalBiConsumer<R, R, E> combiner) {
        return exceptionalStream.collect(supplier.wrap(), accumulator.wrap(), combiner.wrap());
    }

    public boolean anyMatch(@NonNull ExceptionalLongPredicate<E> predicate) {
        return exceptionalStream.anyMatch(predicate.wrap());
    }

    public boolean allMatch(@NonNull ExceptionalLongPredicate<E> predicate) {
        return exceptionalStream.allMatch(predicate.wrap());
    }

    public boolean noneMatch(@NonNull ExceptionalLongPredicate<E> predicate) {
        return exceptionalStream.noneMatch(predicate.wrap());
    }

}
