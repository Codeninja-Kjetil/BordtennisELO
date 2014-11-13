package no.uib.inf319.bordtennis.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import no.uib.inf319.bordtennis.dao.MatchDao;
import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.ResultDao;
import no.uib.inf319.bordtennis.model.Match;
import no.uib.inf319.bordtennis.model.Player;
import no.uib.inf319.bordtennis.util.ServletUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class AdminRemovePlayerServletTest {

    private static final String PASSWORD_HASH =
            "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";

    private AdminRemovePlayerServlet adminRemovePlayerServlet;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher removePlayerDispatcher;
    @Mock
    private RequestDispatcher errorDispatcher;
    @Mock
    private PlayerDao playerDao;
    @Mock
    private MatchDao matchDao;
    @Mock
    private ResultDao resultDao;

    private Player player;
    private Player admin;
    private Match match1;
    private Timestamp time1;
    private List<Match> matches;

    @Before
    public void setUp() throws Exception {
        adminRemovePlayerServlet = new AdminRemovePlayerServlet();
        adminRemovePlayerServlet.setDaoImpl(playerDao, matchDao, resultDao);

        admin = new Player();
        admin.setUsername("admin");
        admin.setPassword(PASSWORD_HASH);
        admin.setLocked(false);

        player = new Player();
        player.setUsername("username");
        player.setLocked(false);

        time1 = new Timestamp(0);
        match1 = new Match();
        match1.setMatchid(1);
        match1.setTime(time1);

        matches = new ArrayList<Match>();
        matches.add(match1);

        when(request.getParameter("user")).thenReturn("username");
        when(request.getParameter("password")).thenReturn("password");

        when(playerDao.find("username")).thenReturn(player);
        when(playerDao.find("admin")).thenReturn(admin);

        when(matchDao.getAllPlayerMatches(player)).thenReturn(matches);

        when(request.getRequestDispatcher(
                AdminRemovePlayerServlet.ADMIN_REMOVE_PLAYER_JSP))
                .thenReturn(removePlayerDispatcher);
        when(request.getRequestDispatcher(ServletUtil.ERRORPAGE_JSP))
                .thenReturn(errorDispatcher);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("player")).thenReturn(admin);
    }

    @Test
    public void doGetShouldForwardToErrorIfNoUsername() throws Exception {
        when(request.getParameter("user")).thenReturn(null);

        adminRemovePlayerServlet.doGet(request, response);

        verify(request).setAttribute("errormessage",
                "Please specify username in URL.");
        verify(errorDispatcher).forward(request, response);
    }

    @Test
    public void doGetShouldForwardToErrorIfInvalidUsername() throws Exception {
        when(playerDao.find("username")).thenReturn(null);

        adminRemovePlayerServlet.doGet(request, response);

        verify(request).setAttribute("errormessage",
                "No user with that username.");
        verify(errorDispatcher).forward(request, response);
    }

    @Test
    public void doGetShouldForwardToErrorIfUserIsLoggedIn() throws Exception {
        when(playerDao.find("username")).thenReturn(admin);

        adminRemovePlayerServlet.doGet(request, response);

        verify(request).setAttribute("errormessage",
                "Can't remove yourself.");
        verify(errorDispatcher).forward(request, response);
    }

    @Test
    public void doGetShouldForwardToRemovePlayerIfValidUsername()
            throws Exception {
        adminRemovePlayerServlet.doGet(request, response);

        verify(removePlayerDispatcher).forward(request, response);
    }

    @Test
    public void doGetShouldSetPlayerAttributeIfValidUsername()
            throws Exception {
        adminRemovePlayerServlet.doGet(request, response);

        verify(request).setAttribute("user", player);
    }

    @Test
    public void doPostShouldForwardToErrorIfNoUsername() throws Exception {
        when(request.getParameter("user")).thenReturn(null);

        adminRemovePlayerServlet.doPost(request, response);

        verify(request).setAttribute("errormessage",
                "Please specify username in request.");
        verify(errorDispatcher).forward(request, response);
    }

    @Test
    public void doPostShouldForwardToErrorIfInvalidUsername() throws Exception {
        when(playerDao.find("username")).thenReturn(null);

        adminRemovePlayerServlet.doPost(request, response);

        verify(request).setAttribute("errormessage",
                "No user with that username.");
        verify(errorDispatcher).forward(request, response);
    }

    @Test
    public void doPostShouldForwardToErrorIfUserIsLoggedIn() throws Exception {
        when(playerDao.find("username")).thenReturn(admin);

        adminRemovePlayerServlet.doPost(request, response);

        verify(request).setAttribute("errormessage",
                "Can't remove yourself.");
        verify(errorDispatcher).forward(request, response);
    }

    @Test
    public void doPostShouldReturnToAdminRemovePlayerIfWrongAdminPassword()
            throws Exception {
        when(request.getParameter("password")).thenReturn("wrong");

        adminRemovePlayerServlet.doPost(request, response);

        verify(removePlayerDispatcher).forward(request, response);
    }

    @Test
    public void doPostShouldSetRequestAttributesIfWrongAdminPassword()
            throws Exception {
        when(request.getParameter("password")).thenReturn("wrong");

        adminRemovePlayerServlet.doPost(request, response);

        verify(request).setAttribute("user", player);
        verify(request).setAttribute("error", "Wrong password.");
    }

    @Test
    public void doPostShouldRemovePlayerMatchesFromDbIfAllOK()
            throws Exception {
        adminRemovePlayerServlet.doPost(request, response);

        verify(matchDao).removeMulti(matches);
    }

    @Test
    public void doPostShouldRemovePlayerFromDbIfAllOK() throws Exception {
        adminRemovePlayerServlet.doPost(request, response);

        verify(playerDao).remove(player);
    }

    @Test
    public void doPostShouldRedirectToPlayerListIfAllOK() throws Exception {
        adminRemovePlayerServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_SEE_OTHER);
        verify(response).setHeader("Location", "AdminPlayerList");
    }
}
