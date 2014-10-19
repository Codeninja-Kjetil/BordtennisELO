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
 * Servlet Filter implementation class AdminFilter.
 */
@WebFilter("/Admin/*")
public final class AdminFilter implements Filter {

    /*
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(final ServletRequest request,
            final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession(false);

        if (!isLoggedInAdmin(session)) {
            ServletUtil.sendToErrorPage(request, response, "Admin",
                    "You are not authorized to view this page.");
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
        // TODO Auto-generated method stub
    }

    /*
     * @see Filter#destroy()
     */
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * Checks if the logged in player (if any) is an admin.
     *
     * @param session the session to check in.
     * @return <code>true</code> if logged in player is admin,
     * <code>false</code> otherwise
     */
    private static boolean isLoggedInAdmin(final HttpSession session) {
        return ServletUtil.isLoggedIn(session)
                && ((Player) session.getAttribute("player")).getAdmin();
    }
}
