package no.uib.inf319.bordtennis.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailException;

import no.uib.inf319.bordtennis.business.UpdateElo;
import no.uib.inf319.bordtennis.dao.MatchDao;
import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.ResultDao;
import no.uib.inf319.bordtennis.dao.context.MatchDaoJpa;
import no.uib.inf319.bordtennis.dao.context.PlayerDaoJpa;
import no.uib.inf319.bordtennis.dao.context.ResultDaoJpa;
import no.uib.inf319.bordtennis.model.Match;
import no.uib.inf319.bordtennis.model.Player;
import no.uib.inf319.bordtennis.model.Result;
import no.uib.inf319.bordtennis.util.EmailSender;
import no.uib.inf319.bordtennis.util.ServletUtil;

/**
 * Servlet that is used to approve or deny a match.
 *
 * @author Kjetil
 */
@WebServlet("/AcceptResult")
public final class AcceptResultServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Text to put in the title when forwarding to error page.
     */
    private static final String ERROR_PAGE_TITLE = "Accept Result";

    /**
     * DAO-object to access the database for player-data.
     */
    private PlayerDao playerDao = new PlayerDaoJpa();

    /**
     * DAO-object to access the database for match-data.
     */
    private MatchDao matchDao = new MatchDaoJpa();

    /**
     * DAO-object to access the database for result-data.
     */
    private ResultDao resultDao = new ResultDaoJpa();

    /*
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        HttpSession session = request.getSession(false);
        if (!ServletUtil.isLoggedIn(session)) {
            // Not logged in
            ServletUtil.redirect(response, "Home");
            return;
        }

        String resultString = request.getParameter("resultid");
        String acceptmethod = request.getParameter("method");

        if (resultString == null || acceptmethod == null
                || (!acceptmethod.equals("accept")
                && !acceptmethod.equals("deny"))) {
            // Bad request
            ServletUtil.sendToErrorPage(request, response, ERROR_PAGE_TITLE,
                    "Missing acceptmethod parameter. "
                    + "resultid = " + resultString
                    + ", method = " + acceptmethod);
            return;
        }

        int resultNumber;
        try {
            resultNumber = Integer.parseInt(resultString);
        } catch (NumberFormatException e) {
            // resultString is not a number
            ServletUtil.sendToErrorPage(request, response, ERROR_PAGE_TITLE,
                    "Resultid parameter missing or not a number.");
            return;
        }

        Result result = resultDao.find(resultNumber);
        if (result == null) {
            // No result with that id
            ServletUtil.sendToErrorPage(request, response, ERROR_PAGE_TITLE,
                    "No result with the resultid in the parameter.");
            return;
        }

        Player resultPlayer = result.getPlayer();
        Match match = result.getMatch();
        Player loggedinPlayer = (Player) session.getAttribute("player");

        if (!resultPlayer.getUsername().equals(loggedinPlayer.getUsername())) {
            // Not correct player logged in
            ServletUtil.sendToErrorPage(request, response, ERROR_PAGE_TITLE,
                    "Not correct player logged in.");
            return;
        }

        int newApproved = acceptmethod.equals("accept") ? 0 : -match
                .getApproved();
        if (result.getPlayernumber().equals(match.getApproved())) {
            match.setApproved(newApproved);
            matchDao.edit(match);
            if (acceptmethod.equals("accept")) {
                UpdateElo updateElo = new UpdateElo();
                updateElo.updateElo(match.getTime());
            } else {
                Player opponent = playerDao.getMatchOpponent(match,
                        resultPlayer);
                String emailAddress = opponent.getEmail();

                if (!ServletUtil.isEmptyString(emailAddress)) {
                    String subject = "Table Tennis - Match denied";
                    String message = String.format(
                            "%s have rejected a match you have registered. "
                            + "The match had the time %s and the score %s.",
                            resultPlayer.getName(), match.getTimeString(),
                            match.getScore());

                    try {
                        EmailSender.sendMail(subject, message, emailAddress);
                    } catch (EmailException e) {
                    }
                }
            }
        }

        ServletUtil.redirect(response, "Profile?user="
                + loggedinPlayer.getUsername());
    }

}
