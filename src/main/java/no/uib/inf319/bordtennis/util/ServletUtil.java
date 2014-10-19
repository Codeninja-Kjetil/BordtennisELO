package no.uib.inf319.bordtennis.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import no.uib.inf319.bordtennis.model.Player;

/**
 * A utility class containing several method used by servlets.
 *
 * @author Kjetil
 */
public final class ServletUtil {

    public static final String ERRORPAGE_JSP = "/WEB-INF/errorpage.jsp";
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
     * Checks if any player is logged in in the specified session.
     *
     * @param session the session to check.
     * @return <code>true</code> if a player is logged in, <code>false</code>
     *         otherwise.
     */
    public static boolean isLoggedIn(final HttpSession session) {
        return session != null && session.getAttribute("player") != null;
    }

    /**
     * Checks if the specified player is logged in in the specified session.
     *
     * @param session the session to check.
     * @param player the player to check.
     * @return <code>true</code> if the player is logged in, <code>false</code>
     *         otherwise.
     */
    public static boolean isLoggedInPlayer(final HttpSession session,
            final Player player) {
        return session != null
                && session.getAttribute("player") != null
                && ((Player) session.getAttribute("player")).getUsername()
                        .equals(player.getUsername());
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

    /**
     * Checks if a string is empty or null.
     *
     * @param string the string to check.
     * @return <code>true</code> if the string is empty or null,
     * <code>false</code> otherwise
     */
    public static boolean isEmptyString(final String string) {
        return string == null || string.isEmpty();
    }

    /**
     * Forwards a request to the error page with a specific title and message.
     *
     * @param request the request
     * @param response the response
     * @param title the error title
     * @param message the error message
     *
     * @throws ServletException if the target resource throws this exception
     * @throws IOException if the target resource throws this exception
     */
    public static void sendToErrorPage(final ServletRequest request,
            final ServletResponse response, final String title,
            final String message) throws ServletException, IOException {
        request.setAttribute("errortitle", title);
        request.setAttribute("errormessage", message);
        request.getRequestDispatcher(ERRORPAGE_JSP).forward(request,
                response);
    }
}
