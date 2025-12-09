package net.dapete.exceptional;

import org.jspecify.annotations.NonNull;

public class WrappedExceptionalException extends RuntimeException {

    WrappedExceptionalException(@NonNull Exception cause) {
        super(cause);
    }

}
