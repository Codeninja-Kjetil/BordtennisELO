package no.uib.inf319.bordtennis.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * A utility class containing several method used my servlets.
 *
 * @author Kjetil
 */
public final class ServletUtil {

    /**
     * A dateformat pattern: <code>dd.MM.yyyy HH:mm</code>.
     */
    private static final String DATEFORMAT_PATTERN = "dd.MM.yyyy HH:mm";

    /**
     * A private constructor.
     */
    private ServletUtil() {
    }

    /**
     * Redirects the webbrowser to an other URL.
     *
     * @param response the response to add the redirect message to.
     * @param url the URL to redirect to
     */
    public static void redirect(final HttpServletResponse response,
            final String url) {
        response.setStatus(HttpServletResponse.SC_SEE_OTHER);
        response.setHeader("Location", url);
    }

    /**
     * Checks if any player is logged in in the spesified session.
     *
     * @param session the session to check.
     * @return <code>true</code> if a player is logged in, <code>false</code>
     *         otherwise.
     */
    public static boolean isPlayerLoggedIn(final HttpSession session) {
        return session != null && session.getAttribute("player") != null;
    }

    /**
     * Converts a date to string based on the format pattern
     * <code>dd.MM.yyyy HH:mm</code>.
     *
     * @param time the date to convert.
     * @return a string representation of the date.
     */
    public static String formatDate(final Date time) {
        DateFormat dateformat = new SimpleDateFormat(DATEFORMAT_PATTERN);
        return dateformat.format(time);
    }
}
