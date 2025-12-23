package net.dapete.exceptional;

import org.jspecify.annotations.NonNull;

/**
 * Exception that wraps exceptions returned by the functional interfaces in the {@link net.dapete.exceptional.function} package.
 * <p>
 * Used by {@link ExceptionalWrapper}.
 */
public class ExceptionalException extends RuntimeException {

    ExceptionalException(@NonNull Exception cause) {
        super(cause);
    }

}
