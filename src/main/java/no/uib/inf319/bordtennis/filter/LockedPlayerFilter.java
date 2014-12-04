package no.uib.inf319.bordtennis.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import no.uib.inf319.bordtennis.model.Player;
import no.uib.inf319.bordtennis.util.ServletUtil;

/**
 * Servlet Filter implementation class LockedPlayerFilter.
 */
@WebFilter(filterName = "lockedPlayerFilter")
public final class LockedPlayerFilter implements Filter {

    /*
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(final ServletRequest request,
            final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession(false);

        if (isLoggedInLocked(session)) {
            session.invalidate();
            ServletUtil.sendToErrorPage(request, response, "Locked",
                    "You have been logged out because your account has been "
                    + "locked.");
        } else {
            // pass the request along the filter chain
            chain.doFilter(request, response);
        }
    }

    /*
     * @see Filter#init(FilterConfig)
     */
    @Override
    public void init(final FilterConfig fConfig) throws ServletException {
    }

    /*
     * @see Filter#destroy()
     */
    @Override
    public void destroy() {
    }

    /**
     * Checks if the logged in player (if any) is locked.
     *
     * @param session the session to check in.
     * @return <code>true</code> if logged in player is locked,
     * <code>false</code> otherwise
     */
    private static boolean isLoggedInLocked(final HttpSession session) {
        return ServletUtil.isLoggedIn(session)
                && ((Player) session.getAttribute("player")).getLocked();
    }

}
