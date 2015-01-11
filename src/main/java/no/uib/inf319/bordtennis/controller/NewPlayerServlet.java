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
import no.uib.inf319.bordtennis.util.InputValidator;
import no.uib.inf319.bordtennis.util.ServletUtil;
import no.uib.inf319.bordtennis.util.Sha256HashUtil;

/**
 * Servlet used to create a new user (player).
 *
 * @author Kjetil
 */
@WebServlet("/NewPlayer")
public final class NewPlayerServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The url to the NewPlayer JSP.
     */
    private static final String NEWPLAYER_JSP = "WEB-INF/newplayer.jsp";

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
            request.getRequestDispatcher(NEWPLAYER_JSP).forward(request,
                    response);
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
        String password1 = request.getParameter("pass1");
        String password2 = request.getParameter("pass2");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        if (ServletUtil.isEmptyString(username)) {
            request.setAttribute("error", "Please type in a username.");
            request.getRequestDispatcher(NEWPLAYER_JSP).forward(request,
                    response);
            return;
        }

        Player playercheck = playerDao.find(username);

        if (playercheck != null) {
            request.setAttribute("error", "That username already exists.");
            request.getRequestDispatcher(NEWPLAYER_JSP).forward(request,
                    response);
            return;
        }

        if (!InputValidator.validateUsername(username)) {
            request.setAttribute("error", "Invalid username. A valid username "
                    + "can contain only letters (A-Z, a-z), numbers (0-9), "
                    + "periods (.), hyphens (-) and underscores (_).");
            request.getRequestDispatcher(NEWPLAYER_JSP).forward(request,
                    response);
            return;
        }

        if (ServletUtil.isEmptyString(password1)) {
            request.setAttribute("error", "Please type in a password.");
            request.getRequestDispatcher(NEWPLAYER_JSP).forward(request,
                    response);
            return;
        }

        if (!password1.equals(password2)) {
            request.setAttribute("error",
                    "The password fields do not match.");
            request.getRequestDispatcher(NEWPLAYER_JSP).forward(request,
                    response);
            return;
        }

        if (ServletUtil.isEmptyString(name)) {
            request.setAttribute("error", "Please type in a name.");
            request.getRequestDispatcher(NEWPLAYER_JSP).forward(request,
                    response);
            return;
        }

        if (ServletUtil.isEmptyString(email)) {
            request.setAttribute("error", "Please type in an e-mail address.");
            request.getRequestDispatcher(NEWPLAYER_JSP).forward(request,
                    response);
            return;
        }

        if (!InputValidator.validateEmail(email)) {
            request.setAttribute("error",
                    "Please type in a valid e-mail address.");
            request.getRequestDispatcher(NEWPLAYER_JSP).forward(request,
                    response);
            return;
        }

        Player newplayer = new Player();
        newplayer.setUsername(username);
        newplayer.setPassword(Sha256HashUtil.sha256hash(password1));
        newplayer.setName(name);
        newplayer.setEmail(email);
        newplayer.setAdmin(false);
        newplayer.setPrivateprofile(false);
        newplayer.setLocked(false);

        playerDao.create(newplayer);

        session = request.getSession();
        session.setAttribute("player", newplayer);
        ServletUtil.redirect(response, "Home");
    }

}
