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
import no.uib.inf319.bordtennis.util.Sha256HashUtil;

/**
 * Servlet implementation class AdminEditAdminServlet.
 */
@WebServlet("/AdminEditAdmin")
public final class AdminEditAdminServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The url to the Admin Edit Admin JSP.
     */
    private static final String ADMIN_EDIT_ADMIN_JSP =
            "WEB-INF/admin_editadmin.jsp";

    /**
     * DAO-object to access the database for player-data.
     */
    private PlayerDao playerDao = new PlayerDaoJpa();

    /*
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        String username = request.getParameter("user");
        if (ServletUtil.isEmptyString(username)) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Edit Admin", "Please specify username in URL.");
            return;
        }

        Player player = playerDao.find(username);
        if (player == null) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Edit Admin", "No user with that username.");
            return;
        }

        HttpSession session = request.getSession();
        Player loggedInPlayer = (Player) session.getAttribute("player");
        if (loggedInPlayer.getUsername().equals(player.getUsername())) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Edit Admin", "Can't deadmin yourself.");
            return;
        }

        request.setAttribute("user", player);
        request.getRequestDispatcher(ADMIN_EDIT_ADMIN_JSP).forward(request,
                response);
    }

    /*
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        String username = request.getParameter("user");
        if (ServletUtil.isEmptyString(username)) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Edit Admin",
                    "Please specify username in request.");
            return;
        }

        Player player = playerDao.find(username);
        if (player == null) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Edit Admin", "No user with that username.");
            return;
        }

        HttpSession session = request.getSession();
        Player loggedInPlayer = (Player) session.getAttribute("player");
        if (loggedInPlayer.getUsername().equals(player.getUsername())) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Edit Admin", "Can't deadmin yourself.");
            return;
        }

        String password = request.getParameter("password");
        Player admin = playerDao.find(loggedInPlayer.getUsername());
        if (ServletUtil.isEmptyString(password)
                || !admin.getPassword().equals(
                        Sha256HashUtil.sha256hash(password))) {
            request.setAttribute("error", "Wrong password.");
            request.setAttribute("user", player);
            request.getRequestDispatcher(ADMIN_EDIT_ADMIN_JSP).forward(
                    request, response);
            return;
        }

        player.setAdmin(!player.getAdmin());
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
