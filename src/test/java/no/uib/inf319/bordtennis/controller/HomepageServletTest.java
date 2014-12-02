package no.uib.inf319.bordtennis.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.PropertiesDao;
import no.uib.inf319.bordtennis.model.RankingListPlayer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class HomepageServletTest {

    private HomepageServlet homepageServlet;

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
    @Mock
    private PropertiesDao propertiesDao;

    private List<RankingListPlayer> activePlayers;
    private List<RankingListPlayer> inactivePlayers;

    @Before
    public void setUp() throws Exception {
        homepageServlet = new HomepageServlet();
        homepageServlet.setDaoImpl(playerDao, propertiesDao);

        activePlayers = new ArrayList<RankingListPlayer>();
        inactivePlayers = new ArrayList<RankingListPlayer>();
        when(playerDao.getActiveRankingListPlayers(any(Timestamp.class))).thenReturn(activePlayers);
        when(playerDao.getInactiveRankingListPlayers(any(Timestamp.class))).thenReturn(inactivePlayers);

        when(request.getRequestDispatcher(HomepageServlet.INDEX_JSP))
                .thenReturn(dispatcher);
        when(propertiesDao.getProperty("inactiveLimit")).thenReturn("6");
    }

    @Test
    public void doGetShouldSetPlayerlistInRequestScope() throws Exception {
        homepageServlet.doGet(request, response);

        verify(request).setAttribute("activePlayers", activePlayers);
        verify(request).setAttribute("inactivePlayers", inactivePlayers);
    }

    @Test
    public void doGetShouldForwardToIndex() throws Exception {
        homepageServlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }
}
