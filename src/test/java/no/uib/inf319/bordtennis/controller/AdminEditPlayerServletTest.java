package no.uib.inf319.bordtennis.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.model.Player;
import no.uib.inf319.bordtennis.util.ServletUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class AdminEditPlayerServletTest {

    private AdminEditPlayerServlet adminEditPlayerServlet;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher editPlayerDispatcher;
    @Mock
    private RequestDispatcher errorDispatcher;
    @Mock
    private PlayerDao playerDao;

    private Player player;

    @Before
    public void setUp() throws Exception {
        adminEditPlayerServlet = new AdminEditPlayerServlet();
        adminEditPlayerServlet.setDaoImpl(playerDao);

        player = new Player();
        player.setUsername("username");

        when(request.getParameter("user")).thenReturn("username");

        when(playerDao.find("username")).thenReturn(player);

        when(request.getRequestDispatcher(
                AdminEditPlayerServlet.ADMIN_EDIT_PLAYER_JSP))
                .thenReturn(editPlayerDispatcher);
        when(request.getRequestDispatcher(ServletUtil.ERRORPAGE_JSP))
                .thenReturn(errorDispatcher);
    }

    @Test
    public void doGetShouldForwardToErrorIfNoUsername() throws Exception {
        when(request.getParameter("user")).thenReturn(null);

        adminEditPlayerServlet.doGet(request, response);

        verify(request).setAttribute("errormessage",
                "Please specify username in request.");
        verify(errorDispatcher).forward(request, response);
    }

    @Test
    public void doGetShouldForwardToErrorIfInvalidUsername() throws Exception {
        when(playerDao.find("username")).thenReturn(null);

        adminEditPlayerServlet.doGet(request, response);

        verify(request).setAttribute("errormessage",
                "No user with that username.");
        verify(errorDispatcher).forward(request, response);
    }

    @Test
    public void doGetShouldForwardToEditPlayerIfValidUsername()
            throws Exception {
        adminEditPlayerServlet.doGet(request, response);

        verify(editPlayerDispatcher).forward(request, response);
    }

    @Test
    public void doGetShouldSetPlayerAttributeIfValidUsername()
            throws Exception {
        adminEditPlayerServlet.doGet(request, response);

        verify(request).setAttribute("user", player);
    }

}
