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
import no.uib.inf319.bordtennis.util.Sha256HashUtil;
import no.uib.inf319.bordtennis.util.ServletUtil;

/**
 * Servlet implementation class NewPlayerServlet.
 */
@WebServlet("/NewPlayer")
public class NewPlayerServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The url to the NewPlayer JSP.
     */
    private static final String NEWPLAYER_JSP = "WEB-INF/newplayer.jsp";

    /*
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected final void doGet(final HttpServletRequest request,
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
    protected final void doPost(final HttpServletRequest request,
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

        PlayerDao dao = new PlayerDaoJpa();
        Player playercheck = dao.find(username);

        if (playercheck != null) {
            request.setAttribute("error", "That username already exists.");
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

        dao.create(newplayer);

        session = request.getSession();
        session.setAttribute("player", newplayer);
        ServletUtil.redirect(response, "Home");
    }

}
