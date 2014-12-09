package no.uib.inf319.bordtennis.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
public final class AdminLockPlayerServletTest {

    private AdminLockPlayerServlet adminLockPlayerServlet;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher lockPlayerDispatcher;
    @Mock
    private RequestDispatcher errorDispatcher;
    @Mock
    private PlayerDao playerDao;

    private Player player;
    private Player admin;

    @Before
    public void setUp() throws Exception {
        adminLockPlayerServlet = new AdminLockPlayerServlet();
        adminLockPlayerServlet.setDaoImpl(playerDao);

        admin = new Player();
        admin.setUsername("admin");
        admin.setLocked(false);

        player = new Player();
        player.setUsername("username");
        player.setLocked(false);

        when(request.getParameter("user")).thenReturn("username");
        when(playerDao.find("username")).thenReturn(player);
        when(playerDao.find("admin")).thenReturn(admin);

        when(request.getRequestDispatcher(
                AdminLockPlayerServlet.ADMIN_LOCK_PLAYER_JSP))
                .thenReturn(lockPlayerDispatcher);
        when(request.getRequestDispatcher(ServletUtil.ERRORPAGE_JSP))
                .thenReturn(errorDispatcher);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("player")).thenReturn(admin);
    }

    @Test
    public void doGetShouldForwardToErrorIfNoUsername() throws Exception {
        when(request.getParameter("user")).thenReturn(null);

        adminLockPlayerServlet.doGet(request, response);

        verify(request).setAttribute("errormessage",
                "Please specify username in request.");
        verify(errorDispatcher).forward(request, response);
    }

    @Test
    public void doGetShouldForwardToErrorIfInvalidUsername() throws Exception {
        when(playerDao.find("username")).thenReturn(null);

        adminLockPlayerServlet.doGet(request, response);

        verify(request).setAttribute("errormessage",
                "No user with that username.");
        verify(errorDispatcher).forward(request, response);
    }

    @Test
    public void doGetShouldForwardToErrorIfUserIsLoggedIn() throws Exception {
        when(playerDao.find("username")).thenReturn(admin);

        adminLockPlayerServlet.doGet(request, response);

        verify(request).setAttribute("errormessage",
                "Can't lock yourself.");
        verify(errorDispatcher).forward(request, response);
    }

    @Test
    public void doGetShouldForwardToLockPlayerIfValidUsername()
            throws Exception {
        adminLockPlayerServlet.doGet(request, response);

        verify(lockPlayerDispatcher).forward(request, response);
    }

    @Test
    public void doGetShouldSetPlayerAttributeIfValidUsername()
            throws Exception {
        adminLockPlayerServlet.doGet(request, response);

        verify(request).setAttribute("user", player);
    }

    @Test
    public void doPostShouldForwardToErrorIfNoUsername() throws Exception {
        when(request.getParameter("user")).thenReturn(null);

        adminLockPlayerServlet.doPost(request, response);

        verify(request).setAttribute("errormessage",
                "Please specify username in request.");
        verify(errorDispatcher).forward(request, response);
    }

    @Test
    public void doPostShouldForwardToErrorIfInvalidUsername() throws Exception {
        when(playerDao.find("username")).thenReturn(null);

        adminLockPlayerServlet.doPost(request, response);

        verify(request).setAttribute("errormessage",
                "No user with that username.");
        verify(errorDispatcher).forward(request, response);
    }

    @Test
    public void doPostShouldForwardToErrorIfUserIsLoggedIn() throws Exception {
        when(playerDao.find("username")).thenReturn(admin);

        adminLockPlayerServlet.doPost(request, response);

        verify(request).setAttribute("errormessage",
                "Can't lock yourself.");
        verify(errorDispatcher).forward(request, response);
    }

    @Test
    public void doPostShouldLockIfUnlockedUser() throws Exception {
        adminLockPlayerServlet.doPost(request, response);

        assertTrue(player.getLocked());
    }

    @Test
    public void doPostShouldUnlockIfLockedUser() throws Exception {
        player.setLocked(true);
        adminLockPlayerServlet.doPost(request, response);

        assertFalse(player.getLocked());
    }

    @Test
    public void doPostShouldCommitToDbIfAllOK() throws Exception {
        adminLockPlayerServlet.doPost(request, response);

        verify(playerDao).edit(player);
    }

    @Test
    public void doPostShouldRedirectToPlayerListIfAllOK() throws Exception {
        adminLockPlayerServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_SEE_OTHER);
        verify(response).setHeader("Location", "AdminPlayerList");
    }
}
