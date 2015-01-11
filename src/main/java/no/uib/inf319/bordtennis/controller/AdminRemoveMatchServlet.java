package no.uib.inf319.bordtennis.controller;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.uib.inf319.bordtennis.business.UpdateElo;
import no.uib.inf319.bordtennis.dao.MatchDao;
import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.ResultDao;
import no.uib.inf319.bordtennis.dao.context.MatchDaoJpa;
import no.uib.inf319.bordtennis.dao.context.PlayerDaoJpa;
import no.uib.inf319.bordtennis.dao.context.ResultDaoJpa;
import no.uib.inf319.bordtennis.model.Match;
import no.uib.inf319.bordtennis.util.ServletUtil;

/**
 * Servlet that removes a match.
 * Only administrator users can use this function.
 *
 * @author Kjetil
 */
@WebServlet("/AdminRemoveMatch")
public final class AdminRemoveMatchServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The url to the Admin Remove Match JSP.
     */
    public static final String ADMIN_REMOVE_MATCH_JSP =
            "WEB-INF/admin_removematch.jsp";

    /**
     * DAO-object to access the database for player-data.
     */
    private PlayerDao playerDao = new PlayerDaoJpa();
    /**
     * DAO-object to access the database for match-data.
     */
    private MatchDao matchDao = new MatchDaoJpa();
    /**
     * DAO-object to access the database for result-data.
     */
    private ResultDao resultDao = new ResultDaoJpa();

    /*
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        String matchidString = request.getParameter("matchid");
        if (ServletUtil.isEmptyString(matchidString)) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Remove Match",
                    "Please specify matchid in request.");
            return;
        }

        Integer matchid;
        try {
            matchid = Integer.parseInt(matchidString);
        } catch (NumberFormatException e) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Remove Match", "No match with that id.");
            return;
        }

        Match match = matchDao.find(matchid);
        if (match == null) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Remove Match", "No match with that id.");
            return;
        }

        request.setAttribute("match", match);
        request.getRequestDispatcher(ADMIN_REMOVE_MATCH_JSP).forward(request,
                response);
    }

    /*
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        String matchidString = request.getParameter("matchid");
        if (ServletUtil.isEmptyString(matchidString)) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Remove Match",
                    "Please specify matchid in request.");
            return;
        }

        Integer matchid;
        try {
            matchid = Integer.parseInt(matchidString);
        } catch (NumberFormatException e) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Remove Match", "No match with that id.");
            return;
        }

        Match match = matchDao.find(matchid);
        if (match == null) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Remove Match", "No match with that id.");
            return;
        }

        Timestamp time = match.getTime();
        matchDao.remove(match);
        UpdateElo updateElo = new UpdateElo(playerDao, matchDao, resultDao);
        updateElo.updateElo(time);

        ServletUtil.redirect(response, "AdminMatchList");
    }

    /**
     * Changes the implementations of the DAO-objects
     * to use to access the database.
     *
     * @param playerDao the new PlayerDao implementation.
     * @param matchDao the new MatchDao implementation.
     * @param resultDao the new ResultDao implementation.
     */
    public void setDaoImpl(final PlayerDao playerDao, final MatchDao matchDao,
            final ResultDao resultDao) {
        this.playerDao = playerDao;
        this.matchDao = matchDao;
        this.resultDao = resultDao;
    }
}
