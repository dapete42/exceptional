package net.dapete.exceptional.wrap;

import net.dapete.exceptional.ExException;
import net.dapete.exceptional.internal.ExUtils;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Unwraps exceptions of the supplied type. All or some of these types may be identical if less than 3 are supplied.
 *
 * @param <E1> the type of first exception to unwrap.
 * @param <E2> the type of second exception to unwrap.
 * @param <E3> tgs type of third exception to unwrap.
 */
public class ExUnwrapper<E1 extends Exception, E2 extends Exception, E3 extends Exception> {

    private static final ThreadLocal<@Nullable Set<Class<? extends Exception>>> activeUnwrappedExceptionsThreadLocal = new ThreadLocal<>();

    private final Class<E1> exceptionClass1;
    private final Class<E2> exceptionClass2;
    private final Class<E3> exceptionClass3;

    ExUnwrapper(Class<E1> exceptionClass1, Class<E2> exceptionClass2, Class<E3> exceptionClass3) {
        this.exceptionClass1 = exceptionClass1;
        this.exceptionClass2 = exceptionClass2;
        this.exceptionClass3 = exceptionClass3;
    }

    /**
     * Create an instance to unwrap the supplied exception class.
     *
     * @param exceptionClass the exception class.
     * @param <E>            the type of the exception class.
     * @return an instance to unwrap the supplied exception class.
     */
    public static <E extends Exception> ExUnwrapper<E, E, E> of(Class<E> exceptionClass) {
        return new ExUnwrapper<>(exceptionClass, exceptionClass, exceptionClass);
    }

    /**
     * Create an instance to unwrap the supplied exception classes.
     *
     * @param exceptionClass1 the first exception class.
     * @param exceptionClass2 the second exception class.
     * @param <E1>            the type of the first exception class.
     * @param <E2>            the type of the second exception class.
     * @return an instance to unwrap the supplied exception classes.
     */
    public static <E1 extends Exception, E2 extends Exception> ExUnwrapper<E1, E2, E2> of(Class<E1> exceptionClass1, Class<E2> exceptionClass2) {
        return new ExUnwrapper<>(exceptionClass1, exceptionClass2, exceptionClass2);
    }

    /**
     * Create an instance to unwrap the supplied exception classes.
     *
     * @param exceptionClass1 the first exception class.
     * @param exceptionClass2 the second exception class.
     * @param exceptionClass3 the third exception class.
     * @param <E1>            the type of the first exception class.
     * @param <E2>            the type of the second exception class.
     * @param <E3>            the type of the third exception class.
     * @return an instance to unwrap the supplied exception classes.
     */
    public static <E1 extends Exception, E2 extends Exception, E3 extends Exception> ExUnwrapper<E1, E2, E3> of(
            Class<E1> exceptionClass1, Class<E2> exceptionClass2, Class<E3> exceptionClass3) {
        return new ExUnwrapper<>(exceptionClass1, exceptionClass2, exceptionClass3);
    }

    public void unwrap(Runnable runnable) throws E1, E2, E3 {
        try {
            unwrapScope(ExUtils.hashSetOf(exceptionClass1, exceptionClass2, exceptionClass3), runnable);
        } catch (ExException e) {
            e.unwrap(exceptionClass1, exceptionClass2, exceptionClass3);
            // the compiler doesn't know that unwrap always throws an exception
            throw e;
        }
    }

    public <T> T unwrap(Supplier<T> supplier) throws E1, E2, E3 {
        try {
            return unwrapScope(ExUtils.hashSetOf(exceptionClass1, exceptionClass2, exceptionClass3), supplier);
        } catch (ExException e) {
            e.unwrap(exceptionClass1, exceptionClass2, exceptionClass3);
            // the compiler doesn't know that unwrap always throws an exception
            throw e;
        }
    }

    /*
     * exceptionClasses may be modified to avoid instantiating more sets.
     */
    static void unwrapScope(@SuppressWarnings("NonApiType") HashSet<Class<? extends Exception>> exceptionClasses, Runnable runnable) {
        final var previousClasses = activeUnwrappedExceptionsThreadLocal.get();
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
        final var previousClasses = activeUnwrappedExceptionsThreadLocal.get();
        try {
            addActiveUnwrappedExceptions(previousClasses, exceptionClasses);
            return supplier.get();
        } finally {
            activeUnwrappedExceptionsThreadLocal.set(previousClasses);
        }
    }

    /*
     * previousClasses is passed as a parameter to avoid calling ThreadLocal.get() multiple times.
     * exceptionClasses may be modified to avoid instantiating more sets.
     */
    private static void addActiveUnwrappedExceptions(@Nullable Set<Class<? extends Exception>> previousClasses,
                                                     @SuppressWarnings("NonApiType") HashSet<Class<? extends Exception>> exceptionClasses) {
        if (previousClasses != null) {
            exceptionClasses.addAll(previousClasses);
        }
        activeUnwrappedExceptionsThreadLocal.set(exceptionClasses);
    }

    /**
     * Check if unwrapping is currently active.
     *
     * @return {@code true} if unwrapping is active, {@code false} otherwise.
     */
    public static boolean isUnwrapActive() {
        return activeUnwrappedExceptionsThreadLocal.get() != null;
    }

    /**
     * Verify unwrapping for the supplied {@code exceptionClass} is currently active.
     *
     * @param exceptionClass exception class.
     * @throws IllegalArgumentException if unwrapping for the supplied {@code exceptionClass} is not currently active.
     */
    public static void verifyUnwrapActive(Class<? extends Exception> exceptionClass) {
        final Set<Class<? extends Exception>> active = activeUnwrappedExceptionsThreadLocal.get();
        if (active == null || active.stream().noneMatch(activeClass -> activeClass.isAssignableFrom(exceptionClass))) {
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
