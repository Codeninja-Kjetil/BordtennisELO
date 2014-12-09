package no.uib.inf319.bordtennis.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.context.PlayerDaoJpa;
import no.uib.inf319.bordtennis.model.Player;
import no.uib.inf319.bordtennis.util.InputValidator;
import no.uib.inf319.bordtennis.util.ServletUtil;
import no.uib.inf319.bordtennis.util.Sha256HashUtil;

/**
 * Servlet implementation class AdminEditPlayerServlet.
 */
@WebServlet("/AdminEditPlayer")
public final class AdminEditPlayerServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The url to the Admin Edit Player JSP.
     */
    public static final String ADMIN_EDIT_PLAYER_JSP =
            "WEB-INF/admin_editplayer.jsp";

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
                    "Admin - Edit Player",
                    "Please specify username in request.");
            return;
        }

        Player player = playerDao.find(username);
        if (player == null) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Edit Player", "No user with that username.");
            return;
        }

        request.setAttribute("user", player);
        request.getRequestDispatcher(ADMIN_EDIT_PLAYER_JSP).forward(request,
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
                    "Admin - Edit Player",
                    "Please specify username in request.");
            return;
        }

        Player player = playerDao.find(username);
        if (player == null) {
            ServletUtil.sendToErrorPage(request, response,
                    "Admin - Edit Player", "No user with that username.");
            return;
        }

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String privateprofileString = request.getParameter("privateprofile");

        String newpassword1 = request.getParameter("newpass1");
        String newpassword2 = request.getParameter("newpass2");

        request.setAttribute("user", player);

     // New password
        if (ServletUtil.isEmptyString(newpassword1)
                != ServletUtil.isEmptyString(newpassword2)) {
            request.setAttribute("error",
                    "Please enter the new password in both fields.");
            request.getRequestDispatcher(ADMIN_EDIT_PLAYER_JSP).forward(request,
                    response);
            return;
        }

        boolean changePassword = !ServletUtil.isEmptyString(newpassword1)
                && !ServletUtil.isEmptyString(newpassword2);
        if (changePassword && !newpassword1.equals(newpassword2)) {
            request.setAttribute("error", "The password fields do not match.");
            request.getRequestDispatcher(ADMIN_EDIT_PLAYER_JSP).forward(request,
                    response);
            return;
        }

        // Name
        if (ServletUtil.isEmptyString(name)) {
            request.setAttribute("error", "Please type in a name.");
            request.getRequestDispatcher(ADMIN_EDIT_PLAYER_JSP).forward(request,
                    response);
            return;
        }

        // Email
        if (ServletUtil.isEmptyString(email)) {
            request.setAttribute("error", "Please type in an email address.");
            request.getRequestDispatcher(ADMIN_EDIT_PLAYER_JSP).forward(request,
                    response);
            return;
        }
        if (!InputValidator.validateEmail(email)) {
            request.setAttribute("error", "Please type a valid email address.");
            request.getRequestDispatcher(ADMIN_EDIT_PLAYER_JSP).forward(request,
                    response);
            return;
        }

        // Privateprofile
        if (!ServletUtil.isStringABoolean(privateprofileString)) {
            request.setAttribute("error",
                    "Invalid privateprofile field in request.");
            request.getRequestDispatcher(ADMIN_EDIT_PLAYER_JSP).forward(request,
                    response);
            return;
        }
        Boolean privateprofile = Boolean.parseBoolean(privateprofileString);

        player.setName(name);
        player.setEmail(email);
        player.setPrivateprofile(privateprofile);
        if (changePassword) {
            player.setPassword(Sha256HashUtil.sha256hash(newpassword1));
        }

        playerDao.edit(player);

        ServletUtil.redirect(response, "AdminPlayerList");
    }

    /**
     * Changes the implementations of the DAO-objects to use to access the
     * database.
     *
     * @param playerDao the new PlayerDao implementation.
     */
    public void setDaoImpl(final PlayerDao playerDao) {
        this.playerDao = playerDao;
    }
}
