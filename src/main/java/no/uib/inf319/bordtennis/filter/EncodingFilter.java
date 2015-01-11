package no.uib.inf319.bordtennis.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter the sets the request character encoding.
 *
 * @author Kjetil
 */
@WebFilter(filterName = "encodingFilter")
public class EncodingFilter implements Filter {

    /*
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public final void doFilter(final ServletRequest request,
            final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");

        // pass the request along the filter chain
        chain.doFilter(request, response);
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
}
