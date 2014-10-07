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
 * Servlet implementation class EditPlayerServlet.
 */
@WebServlet("/EditPlayer")
public final class EditPlayerServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The url to the NewPlayer JSP.
     */
    private static final String EDITPLAYER_JSP = "WEB-INF/editplayer.jsp";

    /*
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        HttpSession session = request.getSession(false);
        if (ServletUtil.isLoggedIn(session)) {
            ServletUtil.redirect(response, "Home");
        } else {
            request.getRequestDispatcher(EDITPLAYER_JSP).forward(request,
                    response);
        }
    }

    /*
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
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

        Player sessionPlayer = (Player) session.getAttribute("player");

        String newpassword1 = request.getParameter("newpass1");
        String newpassword2 = request.getParameter("newpass2");
        String newemail = request.getParameter("newemail");
        String oldpassword = request.getParameter("oldpass");

        if (oldpassword == null || sessionPlayer.getPassword().equals(
                        Sha256HashUtil.sha256hash(oldpassword))) {
            request.setAttribute("error",
                    "The entered password is not correct.");
            request.getRequestDispatcher(EDITPLAYER_JSP).forward(request,
                    response);
            return;
        }

        if (ServletUtil.isEmptyString(newpassword1)
                != ServletUtil.isEmptyString(newpassword2)) {
            request.setAttribute("error",
                    "Please enter the new password in both fields.");
            request.getRequestDispatcher(EDITPLAYER_JSP).forward(request,
                    response);
            return;
        }

        boolean changePassword = !ServletUtil.isEmptyString(newpassword1)
                && !ServletUtil.isEmptyString(newpassword2);
        if (changePassword && !newpassword1.equals(newpassword2)) {
            request.setAttribute("error", "The password fields do not match.");
            request.getRequestDispatcher(EDITPLAYER_JSP).forward(request,
                    response);
            return;
        }

        boolean changeEmail = !ServletUtil.isEmptyString(newemail);
        if (changeEmail && !InputValidator.validateEmail(newemail)) {
            request.setAttribute("error", "Please type a valid email address.");
            request.getRequestDispatcher(EDITPLAYER_JSP).forward(request,
                    response);
            return;
        }

        PlayerDao playerDao = new PlayerDaoJpa();
        Player newPlayer = playerDao.find(sessionPlayer.getUsername());
        if (changePassword) {
            newPlayer.setPassword(Sha256HashUtil.sha256hash(newpassword1));
        }
        if (changeEmail) {
            newPlayer.setEmail(newemail);
        }
        playerDao.edit(newPlayer);

        Player newSessionPlayer = playerDao.find(sessionPlayer.getUsername());
        session.setAttribute("player", newSessionPlayer);

        ServletUtil.redirect(response,
                "Profile?user=" + newSessionPlayer.getUsername());
    }

}
