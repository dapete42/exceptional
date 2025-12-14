package net.dapete.exceptional;

import org.jspecify.annotations.NullMarked;

/**
 * Exception that wraps exceptions returned by the lambdas in the {@link net.dapete.exceptional.function} package.
 * <p>
 * Used by {@link ExceptionalWrapper}.
 */
@NullMarked
public class WrappedExceptionalException extends RuntimeException {

    WrappedExceptionalException(Exception cause) {
        super(cause);
    }

}
