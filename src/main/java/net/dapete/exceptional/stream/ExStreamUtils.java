package net.dapete.exceptional.stream;

import net.dapete.exceptional.ExWrap;

/**
 * Utility class for ExStream etc.
 */
final class ExStreamUtils {

    // Utility class with private constructor
    private ExStreamUtils() {
    }

    static void verifyExceptionAllowed(Class<? extends Exception> exceptionClass) {
        if (ExWrap.isUnwrapActive()) {
            ExWrap.verifyUnwrapActive(exceptionClass);
        }
    }

}
