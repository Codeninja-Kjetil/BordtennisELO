package no.uib.inf319.bordtennis.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailException;

import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.context.PlayerDaoJpa;
import no.uib.inf319.bordtennis.model.Player;
import no.uib.inf319.bordtennis.util.EmailSender;
import no.uib.inf319.bordtennis.util.ServletUtil;

/**
 * Servlet implementation class ChallengeServlet.
 */
@WebServlet("/Challenge")
public final class ChallengeServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The url to the Challenge JSP.
     */
    private static final String CHALLENGE_JSP = "WEB-INF/challenge.jsp";

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
            forwardWithPlayerlist(request, response, session);
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

        Player player = (Player) session.getAttribute("player");

        String opponentUsername = request.getParameter("opponent");
        String message = request.getParameter("message");

        if (ServletUtil.isEmptyString(opponentUsername)) {
            request.setAttribute("error", "Please select an opponent.");
            forwardWithPlayerlist(request, response, session);
            return;
        }
        Player opponent = playerDao.find(opponentUsername);
        if (opponent == null) {
            request.setAttribute("error", "Please select a valid opponent.");
            forwardWithPlayerlist(request, response, session);
            return;
        }

        String mailSubject = "Table Tennis - Challenge";
        String mailMessage = "You have been challenged by " + player.getName()
                + " to a table tennis match";
        if (!ServletUtil.isEmptyString(message)) {
            mailMessage += " with the following message:\n" + message;
        }
        String emailAddress = opponent.getEmail();

        if (ServletUtil.isEmptyString(emailAddress)) {
            request.setAttribute("error", "That user does not have an e-mail "
                    + "address attached to his/her account.");
            forwardWithPlayerlist(request, response, session);
            return;
        }

        try {
            EmailSender.sendMail(mailSubject, mailMessage, emailAddress);
        } catch (EmailException e) {
            request.setAttribute("error", "An error occurred when trying to "
                    + "send the challenge e-mail. Please try again later.");
            forwardWithPlayerlist(request, response, session);
            return;
        }

        ServletUtil.redirect(response, "Profile?user=" + player.getUsername());
    }

    private void forwardWithPlayerlist(final HttpServletRequest request,
            final HttpServletResponse response, final HttpSession session)
            throws ServletException, IOException {
        Player player = (Player) session.getAttribute("player");
        List<Player> playerlist = playerDao
                .getNonLockedPlayersExceptForOne(player);
        request.setAttribute("playerlist", playerlist);
        request.getRequestDispatcher(CHALLENGE_JSP).forward(request,
                response);
    }
}
