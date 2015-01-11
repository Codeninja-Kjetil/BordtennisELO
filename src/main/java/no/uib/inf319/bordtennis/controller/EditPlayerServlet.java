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
 * Servlet used by an user (player) to edit his/her user data.
 *
 * @author Kjetil
 */
@WebServlet("/EditPlayer")
public final class EditPlayerServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The url to the Edit Player JSP.
     */
    private static final String EDITPLAYER_JSP = "WEB-INF/editplayer.jsp";

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
        HttpSession session = request.getSession(false);
        if (!ServletUtil.isLoggedIn(session)) {
            ServletUtil.redirect(response, "Home");
        } else {
            Player sessionPlayer = (Player) session.getAttribute("player");
            Player player = playerDao.find(sessionPlayer.getUsername());
            request.setAttribute("user", player);
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
        if (!ServletUtil.isLoggedIn(session)) {
            ServletUtil.redirect(response, "Home");
            return;
        }

        Player sessionPlayer = (Player) session.getAttribute("player");
        Player newPlayer = playerDao.find(sessionPlayer.getUsername());

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String privateprofileString = request.getParameter("privateprofile");

        String newpassword1 = request.getParameter("newpass1");
        String newpassword2 = request.getParameter("newpass2");

        String oldpassword = request.getParameter("oldpass");

        request.setAttribute("user", newPlayer);

        // Old password
        if (oldpassword == null || !sessionPlayer.getPassword().equals(
                        Sha256HashUtil.sha256hash(oldpassword))) {
            request.setAttribute("error",
                    "The entered password is not correct.");
            request.getRequestDispatcher(EDITPLAYER_JSP).forward(request,
                    response);
            return;
        }

        // New password
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

        // Name
        if (ServletUtil.isEmptyString(name)) {
            request.setAttribute("error", "Please type in a name.");
            request.getRequestDispatcher(EDITPLAYER_JSP).forward(request,
                    response);
            return;
        }

        // Email
        if (ServletUtil.isEmptyString(email)) {
            request.setAttribute("error", "Please type in an email address.");
            request.getRequestDispatcher(EDITPLAYER_JSP).forward(request,
                    response);
            return;
        }
        if (!InputValidator.validateEmail(email)) {
            request.setAttribute("error", "Please type a valid email address.");
            request.getRequestDispatcher(EDITPLAYER_JSP).forward(request,
                    response);
            return;
        }

        // Privateprofile
        if (!ServletUtil.isStringABoolean(privateprofileString)) {
            request.setAttribute("error",
                    "Invalid privateprofile field in request.");
            request.getRequestDispatcher(EDITPLAYER_JSP).forward(request,
                    response);
            return;
        }
        Boolean privateprofile = Boolean.parseBoolean(privateprofileString);

        newPlayer.setName(name);
        newPlayer.setEmail(email);
        newPlayer.setPrivateprofile(privateprofile);
        if (changePassword) {
            newPlayer.setPassword(Sha256HashUtil.sha256hash(newpassword1));
        }

        playerDao.edit(newPlayer);

        Player newSessionPlayer = playerDao.find(sessionPlayer.getUsername());
        session.setAttribute("player", newSessionPlayer);

        ServletUtil.redirect(response,
                "Profile?user=" + newSessionPlayer.getUsername());
    }

}
