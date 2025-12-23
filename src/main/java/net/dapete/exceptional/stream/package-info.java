/**
 * This package provides utilities for handling checked exceptions in Streams.
 * <p>
 * The main components are:
 * <ul>
 *   <li>{@link net.dapete.exceptional.stream.ExceptionalStream}: A wrapper around {@link java.util.stream.Stream} that provides easy transition to
 *   {@link net.dapete.exceptional.stream.ActiveExceptionalStream}.</li>
 *   <li>{@link net.dapete.exceptional.stream.ActiveExceptionalStream}: A stream-like class that supports operations (like {@code map}, {@code filter} or
 *   {@code forEach}) using functional interfaces that throw checked exceptions.</li>
 * </ul>
 */
package net.dapete.exceptional.stream;
