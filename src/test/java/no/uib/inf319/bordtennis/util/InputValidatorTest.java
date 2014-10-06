package no.uib.inf319.bordtennis.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class InputValidatorTest {

    @Test
    public final void kjetilAtUibDotNoShouldBeEmail() throws Exception {
        String email = "kjetil@uib.no";
        assertTrue(InputValidator.validateEmail(email));
    }

    @Test
    public final void danielAtIiDotUibDotNoShouldBeEmail() throws Exception {
        String email = "daniel@ii.uib.no";
        assertTrue(InputValidator.validateEmail(email));
    }

    @Test
    public final void thomasDotLarsenAtHibDotNoShouldBeEmail()
                throws Exception {
        String email = "Thomas.Larsen@hib.no";
        assertTrue(InputValidator.validateEmail(email));
    }

    @Test
    public final void markusDotUibDotNoShouldNotBeEmail() throws Exception {
        String email = "Markus.uib.no";
        assertFalse(InputValidator.validateEmail(email));
    }

    @Test
    public final void mariAtUibShouldNotBeEmail() throws Exception {
        String email = "mari@uib";
        assertFalse(InputValidator.validateEmail(email));
    }

    @Test
    public final void emptyStringShouldNotBeEmail() throws Exception {
        String email = "";
        assertFalse(InputValidator.validateEmail(email));
    }
}