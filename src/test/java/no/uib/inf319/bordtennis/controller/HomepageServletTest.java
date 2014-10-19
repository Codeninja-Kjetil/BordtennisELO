package no.uib.inf319.bordtennis.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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

    private List<Player> playerlist;

    @Before
    public void setUp() throws Exception {
        homepageServlet = new HomepageServlet();
        homepageServlet.setDaoImpl(playerDao);

        playerlist = new ArrayList<Player>();
        when(playerDao.getEloSortedPlayerList()).thenReturn(playerlist);

        when(request.getRequestDispatcher(HomepageServlet.INDEX_JSP))
                .thenReturn(dispatcher);
    }

    @Test
    public void doGetShouldSetPlayerlistInRequestScope() throws Exception {
        homepageServlet.doGet(request, response);

        verify(request).setAttribute("players", playerlist);
    }

    @Test
    public void doGetShouldForwardToIndex() throws Exception {
        homepageServlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }
}
