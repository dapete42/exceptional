package net.dapete.exceptional.stream;

import net.dapete.exceptional.ExWrap;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.MissingResourceException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExStreamUtilsTest {

    @Test
    void verifyExceptionAllowed_notActive() {
        ExStreamUtils.verifyExceptionAllowed(IOException.class);
    }

    @Test
    void verifyExceptionAllowed_active() throws IOException {
        ExWrap.unwrap(IOException.class, () ->
                ExStreamUtils.verifyExceptionAllowed(IOException.class)
        );
    }

    @Test
    void verifyExceptionAllowed_activeForOtherClass() {
        final var thrown = assertThrows(IllegalArgumentException.class, () ->
                ExWrap.unwrap(IOException.class, () ->
                        ExStreamUtils.verifyExceptionAllowed(MissingResourceException.class)
                )
        );

        assertEquals("Exception java.util.MissingResourceException is not allowed here, must be included in ExWrap.unwrap(...) invocation", thrown.getMessage());
    }

    @Test
    void verifyExceptionAllowed_activeForSuperclass() throws IOException {
        ExWrap.unwrap(IOException.class, () ->
                ExStreamUtils.verifyExceptionAllowed(FileNotFoundException.class)
        );
    }

    @Test
    void verifyExceptionAllowed_activeForSubclass() {
        final var thrown = assertThrows(IllegalArgumentException.class, () ->
                ExWrap.unwrap(FileNotFoundException.class, () ->
                        ExStreamUtils.verifyExceptionAllowed(IOException.class)
                )
        );

        assertEquals("Exception java.io.IOException is not allowed here, must be included in ExWrap.unwrap(...) invocation", thrown.getMessage());
    }

}
