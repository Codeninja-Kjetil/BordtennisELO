package no.uib.inf319.bordtennis.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import no.uib.inf319.bordtennis.business.UpdateElo;
import no.uib.inf319.bordtennis.dao.MatchDao;
import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.ResultDao;
import no.uib.inf319.bordtennis.dao.context.MatchDaoJpa;
import no.uib.inf319.bordtennis.dao.context.PlayerDaoJpa;
import no.uib.inf319.bordtennis.dao.context.ResultDaoJpa;
import no.uib.inf319.bordtennis.model.Match;
import no.uib.inf319.bordtennis.model.Player;
import no.uib.inf319.bordtennis.util.ServletUtil;
import no.uib.inf319.bordtennis.util.Sha256HashUtil;

/**
 * Servlet implementation class AdminLockPlayerServlet.
 */
@WebServlet("/AdminRemovePlayer")
public final class AdminRemovePlayerServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The url to the Remove Player JSP.
     */
    public static final String ADMIN_REMOVE_PLAYER_JSP =
            "WEB-INF/admin_removeplayer.jsp";

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
        String username = request.getParameter("user");
        if (ServletUtil.isEmptyString(username)) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Remove Player", "Please specify username in URL.");
            return;
        }

        Player player = playerDao.find(username);
        if (player == null) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Remove Player", "No user with that username.");
            return;
        }

        HttpSession session = request.getSession();
        Player loggedInPlayer = (Player) session.getAttribute("player");
        if (loggedInPlayer.getUsername().equals(player.getUsername())) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Remove Player", "Can't remove yourself.");
            return;
        }

        request.setAttribute("user", player);
        request.getRequestDispatcher(ADMIN_REMOVE_PLAYER_JSP).forward(request,
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
        String username = request.getParameter("user");
        if (ServletUtil.isEmptyString(username)) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Remove Player",
                    "Please specify username in request.");
            return;
        }

        Player player = playerDao.find(username);
        if (player == null) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Remove Player", "No user with that username.");
            return;
        }

        HttpSession session = request.getSession();
        Player loggedInPlayer = (Player) session.getAttribute("player");
        if (loggedInPlayer.getUsername().equals(player.getUsername())) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Remove Player", "Can't remove yourself.");
            return;
        }

        String password = request.getParameter("password");
        Player admin = playerDao.find(loggedInPlayer.getUsername());
        if (ServletUtil.isEmptyString(password)
                || !admin.getPassword().equals(
                        Sha256HashUtil.sha256hash(password))) {
            request.setAttribute("error", "Wrong password.");
            request.setAttribute("user", player);
            request.getRequestDispatcher(ADMIN_REMOVE_PLAYER_JSP).forward(
                    request, response);
            return;
        }

        List<Match> matches = matchDao.getAllPlayerMatches(player);
        if (!matches.isEmpty()) {
            Timestamp firstTime = matches.get(0).getTime();
            matchDao.removeMulti(matches);
            UpdateElo updateElo = new UpdateElo(playerDao, matchDao, resultDao);
            updateElo.updateElo(firstTime);
        }
        playerDao.remove(player);

        ServletUtil.redirect(response, "AdminPlayerList");
    }

    /**
     * Changes the implementations of the DAO-objects to use to access the
     * database.
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
