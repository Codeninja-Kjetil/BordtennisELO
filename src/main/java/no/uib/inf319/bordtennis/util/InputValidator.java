package no.uib.inf319.bordtennis.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class containing method for validating input.
 *
 * @author Kjetil
 */
public final class InputValidator {

    /**
     * Private constructor.
     */
    private InputValidator() {
    }

    /**
     * Checks if a string is a valid e-mail address.
     *
     * @param email the string to check.
     * @return <code>true</code> if a valid e-mail, <code>false</code> otherwise
     */
    public static boolean validateEmail(final String email) {
        Pattern pattern = Pattern
                .compile("(?i)^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
