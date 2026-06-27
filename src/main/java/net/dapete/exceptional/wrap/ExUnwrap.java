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

    /*
     * exceptionClasses may be modified to avoid instantiating more sets.
     */
    static void unwrapScope(@SuppressWarnings("NonApiType") HashSet<Class<? extends Exception>> exceptionClasses, Runnable runnable) {
        final var previousClasses = getActiveUnwrappedExceptions();
        try {
            addActiveUnwrappedExceptions(previousClasses, exceptionClasses);
            runnable.run();
        } finally {
            activeUnwrappedExceptionsThreadLocal.set(previousClasses);
        }
    }

    /*
     * exceptionClasses may be modified to avoid instantiating more sets.
     */
    static <T> T unwrapScope(@SuppressWarnings("NonApiType") HashSet<Class<? extends Exception>> exceptionClasses, Supplier<T> supplier) {
        final var previousClasses = getActiveUnwrappedExceptions();
        try {
            addActiveUnwrappedExceptions(previousClasses, exceptionClasses);
            return supplier.get();
        } finally {
            setActiveUnwrappedExceptions(previousClasses);
        }
    }

    /*
     * previousClasses is passed as a parameter to avoid calling ThreadLocal.get() multiple times.
     * exceptionClasses may be modified to avoid instantiating more sets.
     */
    private static void addActiveUnwrappedExceptions(Set<Class<? extends Exception>> previousClasses,
                                                     @SuppressWarnings("NonApiType") HashSet<Class<? extends Exception>> exceptionClasses) {
        exceptionClasses.addAll(previousClasses);
        setActiveUnwrappedExceptions(exceptionClasses);
    }

    private static Set<Class<? extends Exception>> getActiveUnwrappedExceptions() {
        return Objects.requireNonNullElse(activeUnwrappedExceptionsThreadLocal.get(), Set.of());
    }

    private static void setActiveUnwrappedExceptions(Set<Class<? extends Exception>> activeUnwrappedExceptions) {
        activeUnwrappedExceptionsThreadLocal.set(activeUnwrappedExceptions);
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
