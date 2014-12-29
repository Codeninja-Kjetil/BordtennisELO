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
import no.uib.inf319.bordtennis.util.Sha256HashUtil;
import no.uib.inf319.bordtennis.util.ServletUtil;

/**
 * Servlet implementation class LoginServlet.
 */
@WebServlet("/Login")
public final class LoginServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The url to the Login JSP.
     */
    public static final String LOGIN_JSP = "WEB-INF/login.jsp";

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
        HttpSession session = request.getSession(false);
        if (ServletUtil.isLoggedIn(session)) {
            ServletUtil.redirect(response, "Home");
        } else {
            request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
        }
    }

    /*
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        HttpSession session = request.getSession(false);
        if (ServletUtil.isLoggedIn(session)) {
            ServletUtil.redirect(response, "Home");
            return;
        }

        String username = request.getParameter("user");
        String password = request.getParameter("pass");

        if (ServletUtil.isEmptyString(username)) {
            request.setAttribute("error", "Please type in your username.");
            request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
            return;
        }

        if (ServletUtil.isEmptyString(password)) {
            request.setAttribute("error", "Please type in your password.");
            request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
            return;
        }

        String hashedPassword = Sha256HashUtil.sha256hash(password);
        Player player = playerDao.find(username);

        if (player == null || !player.getPassword().equals(hashedPassword)) {
            request.setAttribute("error",
                    "The combination of username and password is invalid.");
            request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
            return;
        }

        if (player.getLocked()) {
            request.setAttribute("error",
                    "That user is locked and can't log in.");
            request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
            return;
        }

        session = request.getSession();
        session.setAttribute("player", player);
        ServletUtil.redirect(response, "Home");
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
