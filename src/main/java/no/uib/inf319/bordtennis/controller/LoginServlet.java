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
    private static final String LOGIN_JSP = "WEB-INF/login.jsp";

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
        String password = Sha256HashUtil.sha256hash(request
                .getParameter("pass"));

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

        PlayerDao dao = new PlayerDaoJpa();
        Player player = dao.find(username);

        if (player == null || !player.getPassword().equals(password)) {
            request.setAttribute("error",
                    "The combination of username and password is invalid.");
            request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
            return;
        }

        session = request.getSession();
        session.setAttribute("player", player);
        ServletUtil.redirect(response, "Home");

    }
}
