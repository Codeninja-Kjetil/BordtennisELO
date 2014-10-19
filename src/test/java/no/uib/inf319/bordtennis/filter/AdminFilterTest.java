package no.uib.inf319.bordtennis.filter;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import no.uib.inf319.bordtennis.model.Player;
import no.uib.inf319.bordtennis.util.ServletUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class AdminFilterTest {

    private AdminFilter adminFilter;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private FilterChain chain;
    @Mock
    private RequestDispatcher dispatcher;

    private Player normalPlayer;
    private Player adminPlayer;

    @Before
    public void setUp() throws Exception {
        adminFilter = new AdminFilter();

        normalPlayer = new Player();
        normalPlayer.setAdmin(false);
        adminPlayer = new Player();
        adminPlayer.setAdmin(true);

        when(request.getRequestDispatcher(ServletUtil.ERRORPAGE_JSP))
                .thenReturn(dispatcher);
    }

    @Test
    public void noSessionShouldBeForwardedToErrorPage() throws Exception {
        when(request.getSession(false)).thenReturn(null);

        adminFilter.doFilter(request, response, chain);

        verify(chain, never()).doFilter(request, response);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void noPlayerInSessionShouldBeForwardedToErrorPage()
            throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("player")).thenReturn(null);

        adminFilter.doFilter(request, response, chain);

        verify(chain, never()).doFilter(request, response);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void normalPlayerInSessionShouldBeForwardedToErrorPage()
            throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("player")).thenReturn(normalPlayer);

        adminFilter.doFilter(request, response, chain);

        verify(chain, never()).doFilter(request, response);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void adminPlayerInSessionShouldBePassedAlongFilterChain()
            throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("player")).thenReturn(adminPlayer);

        adminFilter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(dispatcher, never()).forward(request, response);
    }
}
