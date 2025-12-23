/**
 * <em>Exceptional!</em> is a Java library designed to handle checked exceptions in functional interfaces and Streams.
 * <p>
 * This module provides utilities to:
 * <ul>
 *   <li>Wrap functional interfaces that throw checked exceptions into standard {@link java.util.function} interfaces.
 *   These can be found in the {@link net.dapete.exceptional.function} package.</li>
 *   <li>Use Streams with operations that can throw checked exceptions through {@link net.dapete.exceptional.stream.ExceptionalStream}
 *   and {@link net.dapete.exceptional.stream.ActiveExceptionalStream}.</li>
 * </ul>
 */
module net.dapete.exceptional {
    requires static lombok;
    requires org.jspecify;
    exports net.dapete.exceptional;
    exports net.dapete.exceptional.function;
    exports net.dapete.exceptional.stream;
}
