package no.uib.inf319.bordtennis.controller;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.model.Player;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class LoginServletTest {

    private static final String PASSWORD_HASH =
            "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";

    private LoginServlet loginServlet;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private PlayerDao playerDao;

    private Player player;

    @Before
    public void setUp() throws Exception {
        loginServlet = new LoginServlet();
        loginServlet.setDaoImpl(playerDao);

        player = new Player();
        player.setUsername("username");
        player.setPassword(PASSWORD_HASH);

        when(request.getSession(false)).thenReturn(session);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("player")).thenReturn(null);
        when(request.getRequestDispatcher(LoginServlet.LOGIN_JSP))
                .thenReturn(dispatcher);

        when(request.getParameter("user")).thenReturn("username");
        when(request.getParameter("pass")).thenReturn("password");

        when(playerDao.find("username")).thenReturn(player);
    }

    @Test
    public void doGetShouldRedirectToHomeIfLoggedIn() throws Exception {
        Player loggedInPlayer = new Player();
        when(session.getAttribute("player")).thenReturn(loggedInPlayer);

        loginServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_SEE_OTHER);
        verify(response).setHeader("Location", "Home");
        verify(dispatcher, never()).forward(request, response);
    }

    @Test
    public void doGetShouldForwardToLoginIfNotLoggedIn() throws Exception {
        loginServlet.doGet(request, response);

        verify(response, never()).setStatus(HttpServletResponse.SC_SEE_OTHER);
        verify(response, never()).setHeader("Location", "Home");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void doPostShouldRedirectToHomeIfLoggedIn() throws Exception {
        Player loggedInPlayer = new Player();
        when(session.getAttribute("player")).thenReturn(loggedInPlayer);

        loginServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_SEE_OTHER);
        verify(response).setHeader("Location", "Home");
        verify(dispatcher, never()).forward(request, response);
    }

    @Test
    public void doPostShouldReturnToLoginIfNoUsername() throws Exception {
        when(request.getParameter("user")).thenReturn("");

        loginServlet.doPost(request, response);

        verify(request).setAttribute("error",
                "Please type in your username.");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void doPostShouldReturnToLoginIfNoPassword() throws Exception {
        when(request.getParameter("pass")).thenReturn("");

        loginServlet.doPost(request, response);

        verify(request).setAttribute("error",
                "Please type in your password.");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void doPostShouldReturnToLoginIfNoUserWithUsername()
            throws Exception {
        when(playerDao.find("username")).thenReturn(null);

        loginServlet.doPost(request, response);

        verify(request).setAttribute("error",
                "The combination of username and password is invalid.");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void doPostShouldReturnToLoginIfWrongPassword() throws Exception {
        when(request.getParameter("pass")).thenReturn("wrongpassword");

        loginServlet.doPost(request, response);

        verify(request).setAttribute("error",
                "The combination of username and password is invalid.");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void doPostShouldPutPlayerInSessionIfAllOK() throws Exception {
        loginServlet.doPost(request, response);

        verify(session).setAttribute("player", player);
    }

    @Test
    public void doPostShouldRedirectToHomeIfAllOK() throws Exception {
        loginServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_SEE_OTHER);
        verify(response).setHeader("Location", "Home");
    }
}
