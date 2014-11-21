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

    /**
     * Checks if a string is a valid username.
     * A valid username can contain only letters (A-Z, a-z), numbers (0-9),
     * periods (.), hyphens (-) and underscores (_).
     *
     * @param username the string to check.
     * @return <code>true</code> if a valid username,
     * <code>false</code> otherwise
     */
    public static boolean validateUsername(final String username) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\._-]+$");
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
