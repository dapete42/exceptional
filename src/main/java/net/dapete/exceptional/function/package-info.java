/**
 * This package contains equivalents of functional interfaces from {@link java.util.function} (and {@link java.lang.Runnable}) that can throw exceptions.
 * <p>
 * If a checked exception is thrown, a {@link net.dapete.exceptional.WrappedExceptionalException}, which is a runtime exception, will be thrown instead. This
 * will have the original exception as its {@link java.lang.Throwable#getCause() cause}.
 */
package net.dapete.exceptional.function;
