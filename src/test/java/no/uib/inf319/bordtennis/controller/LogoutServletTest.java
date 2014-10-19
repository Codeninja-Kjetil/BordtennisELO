package no.uib.inf319.bordtennis.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public final class LogoutServletTest {

    private LogoutServlet logoutServlet;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;

    @Before
    public void setUp() throws Exception {
        logoutServlet = new LogoutServlet();
    }

    @Test
    public void doGetShouldInvalidateSessionIfSessionExists() throws Exception {
        when(request.getSession(false)).thenReturn(session);

        logoutServlet.doGet(request, response);

        verify(session).invalidate();
    }

    @Test
    public void doGetShouldRedirectToHome() throws Exception {
        logoutServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_SEE_OTHER);
        verify(response).setHeader("Location", "Home");
    }
}
