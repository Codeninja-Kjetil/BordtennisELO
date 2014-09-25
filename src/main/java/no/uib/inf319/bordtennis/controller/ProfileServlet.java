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
 * Servlet implementation class ProfileServlet.
 */
@WebServlet("/Profile")
public class ProfileServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /*
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected final void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        String username = request.getParameter("user");
        if (username == null) {
            ServletUtil.redirect(response, "Home");
            return;
        }

        PlayerDao playerDao = new PlayerDaoJpa();
        Player player = playerDao.find(username);
        if (player == null) {
            ServletUtil.redirect(response, "Home");
            return;
        }

        request.setAttribute("profilePlayer", player);
        List<TimeAndElo> elopoints = playerDao.getEloOverTimeList(player);
        request.setAttribute("elochart", elopoints.toString());

        HttpSession session = request.getSession(false);
        boolean isLoggedIn = ServletUtil.isLoggedInPlayer(session, player);
        request.setAttribute("loggedIn", isLoggedIn);
        if (isLoggedIn) {
            MatchDao mdao = new MatchDaoJpa();
            List<PendingMatch> pendingmatches = mdao.getPendingMatches(player);
            request.setAttribute("pending", pendingmatches);
        }

        request.getRequestDispatcher("WEB-INF/profile.jsp").forward(request,
                    response);
    }

}
