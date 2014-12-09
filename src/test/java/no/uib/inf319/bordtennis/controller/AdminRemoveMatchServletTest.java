package no.uib.inf319.bordtennis.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import no.uib.inf319.bordtennis.dao.MatchDao;
import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.ResultDao;
import no.uib.inf319.bordtennis.model.Match;
import no.uib.inf319.bordtennis.util.ServletUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class AdminRemoveMatchServletTest {

    private AdminRemoveMatchServlet adminRemoveMatchServlet;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher removematchDispatcher;
    @Mock
    private RequestDispatcher errorDispatcher;
    @Mock
    private PlayerDao playerDao;
    @Mock
    private MatchDao matchDao;
    @Mock
    private ResultDao resultDao;

    private Match match;

    @Before
    public void setUp() throws Exception {
        adminRemoveMatchServlet = new AdminRemoveMatchServlet();
        adminRemoveMatchServlet.setDaoImpl(playerDao, matchDao, resultDao);

        match = new Match();

        when(request.getParameter("matchid")).thenReturn("1");
        when(matchDao.find(1)).thenReturn(match);

        when(request.getRequestDispatcher(
                AdminRemoveMatchServlet.ADMIN_REMOVE_MATCH_JSP))
                .thenReturn(removematchDispatcher);
        when(request.getRequestDispatcher(ServletUtil.ERRORPAGE_JSP))
                .thenReturn(errorDispatcher);
    }

    @Test
    public void doGetShouldForwardToErrorIfNoMatchid() throws Exception {
        when(request.getParameter("matchid")).thenReturn(null);

        adminRemoveMatchServlet.doGet(request, response);

        verify(request).setAttribute("errormessage",
                "Please specify matchid in request.");
        verify(errorDispatcher).forward(request, response);
    }

    @Test
    public void doGetShouldForwardToErrorIfNoIntegerMatchid() throws Exception {
        when(request.getParameter("matchid")).thenReturn("string");

        adminRemoveMatchServlet.doGet(request, response);

        verify(request).setAttribute("errormessage",
                "No match with that id.");
        verify(errorDispatcher).forward(request, response);
    }

    @Test
    public void doGetShouldForwardToErrorIfInvalidMatchid() throws Exception {
        when(matchDao.find(1)).thenReturn(null);

        adminRemoveMatchServlet.doGet(request, response);

        verify(request).setAttribute("errormessage",
                "No match with that id.");
        verify(errorDispatcher).forward(request, response);
    }

    @Test
    public void doGetShouldForwardToRemoveMatchIfValidMatchid()
            throws Exception {
        adminRemoveMatchServlet.doGet(request, response);

        verify(removematchDispatcher).forward(request, response);
    }

    @Test
    public void doGetShouldSetMatchAttributeIfValidMatchid()
            throws Exception {
        adminRemoveMatchServlet.doGet(request, response);

        verify(request).setAttribute("match", match);
    }

    @Test
    public void doPostShouldForwardToErrorIfNoMatchid() throws Exception {
        when(request.getParameter("matchid")).thenReturn(null);

        adminRemoveMatchServlet.doPost(request, response);

        verify(request).setAttribute("errormessage",
                "Please specify matchid in request.");
        verify(errorDispatcher).forward(request, response);
    }

    @Test
    public void doPostShouldForwardToErrorIfNoIntegerMatchid()
            throws Exception {
        when(request.getParameter("matchid")).thenReturn("string");

        adminRemoveMatchServlet.doPost(request, response);

        verify(request).setAttribute("errormessage",
                "No match with that id.");
        verify(errorDispatcher).forward(request, response);
    }

    @Test
    public void doPostShouldForwardToErrorIfInvalidMatchid() throws Exception {
        when(matchDao.find(1)).thenReturn(null);

        adminRemoveMatchServlet.doPost(request, response);

        verify(request).setAttribute("errormessage",
                "No match with that id.");
        verify(errorDispatcher).forward(request, response);
    }

    @Test
    public void doPostShouldRemoveMatchFromDbAllOK() throws Exception {
        adminRemoveMatchServlet.doPost(request, response);

        verify(matchDao).remove(match);
    }

    @Test
    public void doPostShouldUpdateEloIfAllOK() throws Exception {
        // TODO:
    }

    @Test
    public void doPostShouldRedirectToMatchListIfAllOK() throws Exception {
        adminRemoveMatchServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_SEE_OTHER);
        verify(response).setHeader("Location", "AdminMatchList");
    }
}
