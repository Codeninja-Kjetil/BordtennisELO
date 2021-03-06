package no.uib.inf319.bordtennis.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import no.uib.inf319.bordtennis.dao.MatchDao;
import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.context.MatchDaoJpa;
import no.uib.inf319.bordtennis.dao.context.PlayerDaoJpa;
import no.uib.inf319.bordtennis.model.PendingMatch;
import no.uib.inf319.bordtennis.model.Player;
import no.uib.inf319.bordtennis.model.TimeAndElo;
import no.uib.inf319.bordtennis.util.ServletUtil;

/**
 * Servlet that views an user's profile page.
 *
 * @author Kjetil
 */
@WebServlet("/Profile")
public final class ProfileServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * DAO-object to access the database for player-data.
     */
    private PlayerDao playerDao = new PlayerDaoJpa();

    /**
     * DAO-object to access the database for match-data.
     */
    private MatchDao matchDao = new MatchDaoJpa();

    /*
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        String username = request.getParameter("user");
        if (username == null) {
            ServletUtil.sendToErrorPage(request, response, "Profil",
                "Invalid request. Please type in the username in the request.");
            return;
        }

        Player player = playerDao.find(username);
        if (player == null) {
            ServletUtil.sendToErrorPage(request, response, "Profil",
                    "No user with username " + username);
            return;
        }

        HttpSession session = request.getSession(false);
        boolean isLoggedIn = ServletUtil.isLoggedInPlayer(session, player);

        if (player.getPrivateprofile() && !isLoggedIn) {
            ServletUtil.sendToErrorPage(request, response, "Profil",
                    "The user " + username + " has a private user profile.");
            return;
        }

        request.setAttribute("profilePlayer", player);
        List<TimeAndElo> elopoints = playerDao.getEloOverTimeList(player);
        request.setAttribute("elochart", elopoints.toString());
        request.setAttribute("loggedIn", isLoggedIn);

        if (isLoggedIn) {
            List<PendingMatch> pendingmatches =
                    matchDao.getPendingMatches(player);
            request.setAttribute("pending", pendingmatches);
        }

        request.getRequestDispatcher("WEB-INF/profile.jsp").forward(request,
                response);
    }
}
