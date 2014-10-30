package no.uib.inf319.bordtennis.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.uib.inf319.bordtennis.dao.MatchDao;
import no.uib.inf319.bordtennis.dao.context.MatchDaoJpa;
import no.uib.inf319.bordtennis.model.MatchWithPlayerNames;

/**
 * Servlet implementation class AdminMatchListServlet.
 */
@WebServlet("/AdminMatchList")
public final class AdminMatchListServlet extends HttpServlet {
    /**
     * The url to the Admin MatchList JSP.
     */
    public static final String ADMIN_MATCH_LIST_JSP =
            "WEB-INF/admin_matchlist.jsp";

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * DAO-object to access the database for match-data.
     */
    private MatchDao matchDao = new MatchDaoJpa();

    /*
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        List<MatchWithPlayerNames> matchlist =
                matchDao.getAllMatchesWithPlayerNames();
        request.setAttribute("matchlist", matchlist);

        request.getRequestDispatcher(ADMIN_MATCH_LIST_JSP)
                .forward(request, response);
    }

    /**
     * Changes the implementations of the DAO-objects
     * to use to access the database.
     *
     * @param matchDao the new MatchDao implementation.
     */
    public void setDaoImpl(final MatchDao matchDao) {
        this.matchDao = matchDao;
    }
}
