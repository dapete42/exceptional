/**
 * This package provides utilities for handling checked exceptions in functional interfaces and Streams.
 * <p>
 * The main components are:
 * <ul>
 *   <li>{@link net.dapete.exceptional.ExceptionalWrapper}: Utility class to wrap functional interfaces that throw checked exceptions into standard
 *   {@link java.util.function} interfaces by wrapping exceptions in a runtime exception.</li>
 *   <li>{@link net.dapete.exceptional.ExceptionalStream}: A wrapper around {@link java.util.stream.Stream} that provides easy transition to
 *   {@link net.dapete.exceptional.ActiveExceptionalStream}.</li>
 *   <li>{@link net.dapete.exceptional.ActiveExceptionalStream}: A stream-like class that supports operations (like {@code map}, {@code filter} or
 *   {@code forEach}) using functional interfaces that throw checked exceptions.</li>
 * </ul>
 */
package net.dapete.exceptional;
