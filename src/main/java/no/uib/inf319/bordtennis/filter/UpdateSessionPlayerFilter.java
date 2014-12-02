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

import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.context.PlayerDaoJpa;
import no.uib.inf319.bordtennis.model.Player;
import no.uib.inf319.bordtennis.util.ServletUtil;

/**
 * Servlet Filter implementation class UpdateSessionPlayerFilter.
 */
@WebFilter(filterName = "updateSessionPlayerFilter")
public final class UpdateSessionPlayerFilter implements Filter {

    private PlayerDao playerDao = new PlayerDaoJpa();

    /*
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(final ServletRequest request,
            final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession(false);

        if (ServletUtil.isLoggedIn(session)) {
            Player sessionPlayer = (Player) session.getAttribute("player");
            Player dbPlayer = playerDao.find(sessionPlayer.getUsername());
            session.setAttribute("player", dbPlayer);
        }

        // pass the request along the filter chain
        chain.doFilter(request, response);
    }

    /*
     * @see Filter#init(FilterConfig).
     */
    @Override
    public void init(final FilterConfig fConfig) throws ServletException {
    }

    /*
     * @see Filter#destroy().
     */
    @Override
    public void destroy() {
    }
}
