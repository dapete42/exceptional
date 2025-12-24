package net.dapete.exceptional;

import org.jspecify.annotations.NonNull;

import java.io.Serial;

/**
 * Exception that wraps exceptions returned by the functional interfaces in the {@link net.dapete.exceptional.function} package.
 * <p>
 * Used by {@link ExceptionalWrapper}.
 */
public class ExceptionalException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8555447007140970731L;

    ExceptionalException(@NonNull Exception cause) {
        super(cause);
    }

}
