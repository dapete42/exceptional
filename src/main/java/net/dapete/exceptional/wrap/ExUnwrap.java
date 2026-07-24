package net.dapete.exceptional.wrap;

import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public final class ExUnwrap {

    private static final ThreadLocal<@Nullable Set<Class<? extends Exception>>> activeExceptionClasses = new ThreadLocal<>();

    // Utility class with private constructor
    private ExUnwrap() {
    }

    static void unwrapScope(Set<Class<? extends Exception>> exceptionClasses, Runnable runnable) {
        final var previousExceptionClasses = getActiveExceptionClasses();
        try {
            addActiveExceptionClasses(previousExceptionClasses, exceptionClasses);
            runnable.run();
        } finally {
            setActiveExceptionClasses(previousExceptionClasses);
        }
    }

    static <T> T unwrapScope(Set<Class<? extends Exception>> exceptionClasses, Supplier<T> supplier) {
        final var previousExceptionClasses = getActiveExceptionClasses();
        try {
            addActiveExceptionClasses(previousExceptionClasses, exceptionClasses);
            return supplier.get();
        } finally {
            setActiveExceptionClasses(previousExceptionClasses);
        }
    }

    private static @Nullable Set<Class<? extends Exception>> getActiveExceptionClasses() {
        return activeExceptionClasses.get();
    }

    private static void setActiveExceptionClasses(@Nullable Set<Class<? extends Exception>> exceptionClasses) {
        if (exceptionClasses == null || exceptionClasses.isEmpty()) {
            activeExceptionClasses.remove();
        } else {
            activeExceptionClasses.set(exceptionClasses);
        }
    }

    private static void addActiveExceptionClasses(@Nullable Set<Class<? extends Exception>> previousClasses,
                                                  Set<Class<? extends Exception>> exceptionClasses) {
        final HashSet<Class<? extends Exception>> allClasses;
        if (previousClasses == null) {
            allClasses = new HashSet<>(exceptionClasses);
        } else {
            allClasses = new HashSet<>(previousClasses.size() + exceptionClasses.size());
            allClasses.addAll(previousClasses);
            allClasses.addAll(exceptionClasses);
        }
        setActiveExceptionClasses(allClasses);
    }

    /**
     * Check if unwrapping is currently active.
     *
     * @return {@code true} if unwrapping is active, {@code false} otherwise.
     */
    public static boolean isUnwrapActive() {
        final var activeExceptionClasses = getActiveExceptionClasses();
        return isUnwrapActive(activeExceptionClasses);
    }

    /**
     * Verify unwrapping for the supplied {@code exceptionClass} is currently active.
     *
     * @param exceptionClass exception class.
     * @throws IllegalArgumentException if unwrapping for the supplied {@code exceptionClass} is not currently active.
     */
    public static void verifyUnwrapActive(Class<? extends Exception> exceptionClass) {
        final Set<Class<? extends Exception>> activeExceptionClasses = getActiveExceptionClasses();
        verifyUnwrapActive(activeExceptionClasses, exceptionClass);
    }

    /**
     * If unwrapping is active, verify it is active for the supplied {@code exceptionClass}.
     *
     * @param exceptionClass exception class.
     * @throws IllegalArgumentException if unwrapping is active, but not for the supplied {@code exceptionClass}.
     */
    public static void verifyExceptionAllowed(Class<? extends Exception> exceptionClass) {
        final var activeExceptionClasses = getActiveExceptionClasses();
        if (isUnwrapActive(activeExceptionClasses)) {
            verifyUnwrapActive(activeExceptionClasses, exceptionClass);
        }
    }

    private static boolean isUnwrapActive(@Nullable Set<Class<? extends Exception>> activeExceptionClasses) {
        return activeExceptionClasses != null
               && !activeExceptionClasses.isEmpty();
    }

    private static void verifyUnwrapActive(@Nullable Set<Class<? extends Exception>> activeExceptionClasses, Class<? extends Exception> exceptionClass) {
        if (activeExceptionClasses == null ||
            activeExceptionClasses.stream().noneMatch(activeClass -> activeClass.isAssignableFrom(exceptionClass))) {
            throw new IllegalArgumentException("Exception %s is not allowed here, must be included in ExUnwrapper.of(...) invocation"
                    .formatted(exceptionClass.getName()));
        }
    }

}
