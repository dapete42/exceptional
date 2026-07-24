package net.dapete.exceptional.wrap;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.MissingResourceException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExUnwrapTest {

    @Test
    void verifyExceptionAllowed_notActive() {
        ExUnwrap.verifyExceptionAllowed(IOException.class);
    }

    @Test
    void verifyExceptionAllowed_active() throws IOException {
        ExUnwrapper.of(IOException.class)
                .unwrap(() -> ExUnwrap.verifyExceptionAllowed(IOException.class));
    }

    @Test
    void verifyExceptionAllowed_activeForOtherClass() {
        final var thrown = assertThrows(IllegalArgumentException.class, () ->
                ExUnwrapper.of(IOException.class)
                        .unwrap(() -> ExUnwrap.verifyExceptionAllowed(MissingResourceException.class))
        );

        assertEquals("Exception java.util.MissingResourceException is not allowed here, must be included in ExUnwrapper.of(...) invocation", thrown.getMessage());
    }

    @Test
    void verifyExceptionAllowed_activeForSuperclass() throws IOException {
        ExUnwrapper.of(IOException.class)
                .unwrap(() -> ExUnwrap.verifyExceptionAllowed(FileNotFoundException.class));
    }

    @Test
    void verifyExceptionAllowed_activeForSubclass() {
        final var thrown = assertThrows(IllegalArgumentException.class, () ->
                ExUnwrapper.of(FileNotFoundException.class)
                        .unwrap(() ->
                                ExUnwrap.verifyExceptionAllowed(IOException.class)
                        )
        );

        assertEquals("Exception java.io.IOException is not allowed here, must be included in ExUnwrapper.of(...) invocation", thrown.getMessage());
    }

    @Test
    void verifyUnwrapActive_notActive() {
        final var thrown = assertThrows(IllegalArgumentException.class, () ->
                ExUnwrap.verifyUnwrapActive(IOException.class)
        );

        assertEquals("Exception java.io.IOException is not allowed here, must be included in ExUnwrapper.of(...) invocation", thrown.getMessage());
    }

    @Test
    void verifyUnwrapActive_active() throws IOException {
        ExUnwrapper.of(IOException.class)
                .unwrap(() -> ExUnwrap.verifyUnwrapActive(IOException.class));
    }

    @Test
    void verifyUnwrapActive_activeForOtherClass() {
        final var thrown = assertThrows(IllegalArgumentException.class, () ->
                ExUnwrapper.of(IOException.class)
                        .unwrap(() -> ExUnwrap.verifyUnwrapActive(MissingResourceException.class))
        );

        assertEquals("Exception java.util.MissingResourceException is not allowed here, must be included in ExUnwrapper.of(...) invocation", thrown.getMessage());
    }

    @Test
    void verifyUnwrapActive_activeForSuperclass() throws IOException {
        ExUnwrapper.of(IOException.class)
                .unwrap(() -> ExUnwrap.verifyUnwrapActive(FileNotFoundException.class));
    }

    @Test
    void verifyUnwrapActive_activeForSubclass() {
        final var thrown = assertThrows(IllegalArgumentException.class, () ->
                ExUnwrapper.of(FileNotFoundException.class)
                        .unwrap(() -> ExUnwrap.verifyUnwrapActive(IOException.class))
        );

        assertEquals("Exception java.io.IOException is not allowed here, must be included in ExUnwrapper.of(...) invocation", thrown.getMessage());
    }

}
