package no.uib.inf319.bordtennis.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.uib.inf319.bordtennis.dao.MatchDao;
import no.uib.inf319.bordtennis.model.MatchWithPlayerNames;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class AdminMatchListServletTest {

    private AdminMatchListServlet adminMatchListServlet;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private MatchDao matchDao;

    private List<MatchWithPlayerNames> matchlist;

    @Before
    public void setUp() throws Exception {
        adminMatchListServlet = new AdminMatchListServlet();
        adminMatchListServlet.setDaoImpl(matchDao);

        matchlist = new ArrayList<MatchWithPlayerNames>();
        when(matchDao.getAllMatchesWithPlayerNames()).thenReturn(matchlist);

        when(request.getRequestDispatcher(
                AdminMatchListServlet.ADMIN_MATCH_LIST_JSP))
                .thenReturn(dispatcher);
    }

    @Test
    public void doGetShouldSetMatchListInRequest() throws Exception {
        adminMatchListServlet.doGet(request, response);

        verify(request).setAttribute("matchlist", matchlist);
    }

    @Test
    public void doGetShouldForwardToIndex() throws Exception {
        adminMatchListServlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }

}
