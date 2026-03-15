/**
 * This package provides utilities for handling checked exceptions in Streams.
 * <p>
 * The main component is {@link net.dapete.exceptional.stream.ExStream}, a wrapper around {@link java.util.stream.Stream} that supports equivalents to
 * operations like {@code map}, {@code filter} or {@code forEach} throwing checked exceptions, named {@code exceptionalMap}, {@code exceptionalFilter} etc.
 * <p>
 * Similarly, there are also {@link net.dapete.exceptional.stream.ExDoubleStream}, {@link net.dapete.exceptional.stream.ExIntStream} and
 * {@link net.dapete.exceptional.stream.ExLongStream}.
 */
@NullMarked
package net.dapete.exceptional.stream;

import org.jspecify.annotations.NullMarked;
