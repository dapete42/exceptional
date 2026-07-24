package net.dapete.exceptional.wrap;

import net.dapete.exceptional.ExException;
import net.dapete.exceptional.internal.ExUtils;

import java.util.Set;
import java.util.function.Supplier;

/**
 * Unwraps exceptions of the supplied type. All or some of these types may be identical if less than 3 are supplied.
 *
 * @param <E1> the type of the first exception to unwrap.
 * @param <E2> the type of the second exception to unwrap.
 * @param <E3> the type of the third exception to unwrap.
 */
public final class ExUnwrapper<E1 extends Exception, E2 extends Exception, E3 extends Exception> {

    private final Class<E1> exceptionClass1;
    private final Class<E2> exceptionClass2;
    private final Class<E3> exceptionClass3;

    private final Set<Class<? extends Exception>> exceptionClasses;

    ExUnwrapper(Class<E1> exceptionClass1, Class<E2> exceptionClass2, Class<E3> exceptionClass3) {
        this.exceptionClass1 = exceptionClass1;
        this.exceptionClass2 = exceptionClass2;
        this.exceptionClass3 = exceptionClass3;
        this.exceptionClasses = ExUtils.setOf3(exceptionClass1, exceptionClass2, exceptionClass3);
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
            ExUnwrap.unwrapScope(exceptionClasses, runnable);
        } catch (ExException e) {
            e.unwrap(exceptionClass1, exceptionClass2, exceptionClass3);
            // the compiler doesn't know that unwrap always throws an exception
            throw e;
        }
    }

    public <T> T unwrap(Supplier<T> supplier) throws E1, E2, E3 {
        try {
            return ExUnwrap.unwrapScope(exceptionClasses, supplier);
        } catch (ExException e) {
            e.unwrap(exceptionClass1, exceptionClass2, exceptionClass3);
            // the compiler doesn't know that unwrap always throws an exception
            throw e;
        }
    }

}
