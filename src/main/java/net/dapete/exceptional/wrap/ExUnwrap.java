package net.dapete.exceptional.wrap;

import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public final class ExUnwrap {

    private static final ThreadLocal<@Nullable Set<Class<? extends Exception>>> activeUnwrappedExceptionsThreadLocal = new ThreadLocal<>();

    // Utility class with private constructor
    private ExUnwrap() {
    }

    static void unwrapScope(Set<Class<? extends Exception>> exceptionClasses, Runnable runnable) {
        final var previousClasses = getActiveUnwrappedExceptions();
        try {
            setActiveUnwrappedExceptions(previousClasses, exceptionClasses);
            runnable.run();
        } finally {
            setActiveUnwrappedExceptions(previousClasses);
        }
    }

    static <T> T unwrapScope(Set<Class<? extends Exception>> exceptionClasses, Supplier<T> supplier) {
        final var previousClasses = getActiveUnwrappedExceptions();
        try {
            setActiveUnwrappedExceptions(previousClasses, exceptionClasses);
            return supplier.get();
        } finally {
            setActiveUnwrappedExceptions(previousClasses);
        }
    }

    private static Set<Class<? extends Exception>> getActiveUnwrappedExceptions() {
        return Objects.requireNonNullElse(activeUnwrappedExceptionsThreadLocal.get(), Set.of());
    }

    private static void setActiveUnwrappedExceptions(Set<Class<? extends Exception>> activeUnwrappedExceptions) {
        activeUnwrappedExceptionsThreadLocal.set(activeUnwrappedExceptions);
    }

    private static void setActiveUnwrappedExceptions(Set<Class<? extends Exception>> previousClasses,
                                                     Set<Class<? extends Exception>> exceptionClasses) {
        final HashSet<Class<? extends Exception>> allClasses = new HashSet<>(previousClasses.size() + exceptionClasses.size());
        allClasses.addAll(previousClasses);
        allClasses.addAll(exceptionClasses);
        setActiveUnwrappedExceptions(allClasses);
    }

    /**
     * Check if unwrapping is currently active.
     *
     * @return {@code true} if unwrapping is active, {@code false} otherwise.
     */
    public static boolean isUnwrapActive() {
        return !getActiveUnwrappedExceptions().isEmpty();
    }

    /**
     * Verify unwrapping for the supplied {@code exceptionClass} is currently active.
     *
     * @param exceptionClass exception class.
     * @throws IllegalArgumentException if unwrapping for the supplied {@code exceptionClass} is not currently active.
     */
    public static void verifyUnwrapActive(Class<? extends Exception> exceptionClass) {
        final Set<Class<? extends Exception>> active = getActiveUnwrappedExceptions();
        if (active.stream().noneMatch(activeClass -> activeClass.isAssignableFrom(exceptionClass))) {
            throw new IllegalArgumentException("Exception %s is not allowed here, must be included in ExUnwrapper.of(...) invocation"
                    .formatted(exceptionClass.getName()));
        }
    }

    /**
     * If unwrapping is active, verify it is active for the supplied {@code exceptionClass}.
     *
     * @param exceptionClass exception class.
     * @throws IllegalArgumentException if unwrapping is active, but not for the supplied {@code exceptionClass}.
     */
    public static void verifyExceptionAllowed(Class<? extends Exception> exceptionClass) {
        if (isUnwrapActive()) {
            verifyUnwrapActive(exceptionClass);
        }
    }

}
