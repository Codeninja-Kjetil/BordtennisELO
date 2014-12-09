package no.uib.inf319.bordtennis.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.context.PlayerDaoJpa;
import no.uib.inf319.bordtennis.model.Player;
import no.uib.inf319.bordtennis.util.ServletUtil;

/**
 * Servlet implementation class AdminLockPlayerServlet.
 */
@WebServlet("/AdminLockPlayer")
public final class AdminLockPlayerServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The url to the Admin Lock Player JSP.
     */
    public static final String ADMIN_LOCK_PLAYER_JSP =
            "WEB-INF/admin_lockplayer.jsp";

    /**
     * DAO-object to access the database for player-data.
     */
    private PlayerDao playerDao = new PlayerDaoJpa();

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
                    "Admin - Lock Player",
                    "Please specify username in request.");
            return;
        }

        Player player = playerDao.find(username);
        if (player == null) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Lock Player", "No user with that username.");
            return;
        }

        HttpSession session = request.getSession();
        Player loggedInPlayer = (Player) session.getAttribute("player");
        if (loggedInPlayer.getUsername().equals(player.getUsername())) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Lock Player", "Can't lock yourself.");
            return;
        }

        request.setAttribute("user", player);
        request.getRequestDispatcher(ADMIN_LOCK_PLAYER_JSP).forward(request,
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
                    "Admin - Lock Player",
                    "Please specify username in request.");
            return;
        }

        Player player = playerDao.find(username);
        if (player == null) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Lock Player", "No user with that username.");
            return;
        }

        HttpSession session = request.getSession();
        Player loggedInPlayer = (Player) session.getAttribute("player");
        if (loggedInPlayer.getUsername().equals(player.getUsername())) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Lock Player", "Can't lock yourself.");
            return;
        }

        player.setLocked(!player.getLocked());
        playerDao.edit(player);

        ServletUtil.redirect(response, "AdminPlayerList");
    }

    /**
     * Changes the implementations of the DAO-objects
     * to use to access the database.
     *
     * @param playerDao the new PlayerDao implementation.
     */
    public void setDaoImpl(final PlayerDao playerDao) {
        this.playerDao = playerDao;
    }
}
