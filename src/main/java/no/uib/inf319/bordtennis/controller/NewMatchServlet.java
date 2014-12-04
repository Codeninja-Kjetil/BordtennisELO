package no.uib.inf319.bordtennis.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailException;

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
 * Servlet implementation class NewMatchServlet.
 */
@WebServlet("/NewMatch")
public final class NewMatchServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The url to the NewMatch JSP.
     */
    private static final String NEWMATCH_JSP = "WEB-INF/newmatch.jsp";

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
        String score = request.getParameter("score");
        String timeString = request.getParameter("time");

        if (ServletUtil.isEmptyString(opponentUsername)) {
            request.setAttribute("error", "Please select an opponent.");
            forwardWithPlayerlist(request, response, session);
            return;
        }

        if (ServletUtil.isEmptyString(score)) {
            request.setAttribute("error", "Please select a score.");
            forwardWithPlayerlist(request, response, session);
            return;
        }

        if (ServletUtil.isEmptyString(timeString)) {
            request.setAttribute("error",
                    "Please type in the time the match was played.");
            forwardWithPlayerlist(request, response, session);
            return;
        }

        PlayerDao playerDao = new PlayerDaoJpa();
        Player opponent = playerDao.find(opponentUsername);
        if (opponent == null) {
            request.setAttribute("error", "Please select a valid opponent.");
            forwardWithPlayerlist(request, response, session);
            return;
        }

        if (!score.equals("3-0") && !score.equals("3-1")
                && !score.equals("3-2") && !score.equals("0-3")
                && !score.equals("1-3") && !score.equals("2-3")) {
            request.setAttribute("error", "Please select a valid score.");
            forwardWithPlayerlist(request, response, session);
            return;
        }

        int victor;
        if (score.charAt(0) == '3') {
            victor = 1;
        } else {
            victor = 2;
        }

        DateFormat dateformat = new SimpleDateFormat("dd.MM.yy HH:mm");
        Timestamp time;
        try {
            Date date = dateformat.parse(timeString);
            time = new Timestamp(date.getTime());
        } catch (ParseException e) {
            request.setAttribute("error", "Please type in a valid time. "
                    + "A valid time format is: \"dd.mm.yy hh:mm\".");
            forwardWithPlayerlist(request, response, session);
            return;
        }

        Match match = new Match();
        match.setTime(time);
        match.setVictor(victor);
        match.setScore(score);
        match.setApproved(2);

        MatchDao matchDao = new MatchDaoJpa();
        matchDao.create(match);

        Result playerResult = new Result();
        playerResult.setMatch(match);
        playerResult.setPlayernumber(1);
        playerResult.setPlayer(player);

        Result opponentResult = new Result();
        opponentResult.setMatch(match);
        opponentResult.setPlayernumber(2);
        opponentResult.setPlayer(opponent);

        ResultDao resultDao = new ResultDaoJpa();
        resultDao.create(playerResult);
        resultDao.create(opponentResult);

        // Send emails
        String playerEmail = player.getEmail();
        String opponentEmail = opponent.getEmail();

        if (playerEmail != null) {
            try {
                String message = String.format("You have registered a match "
                        + "with %s with the time %s and the score %s.",
                        opponent.getName(), match.getTimeString(),
                        match.getScore());
                EmailSender.sendMail("Table Tennis - Match registered",
                        message, playerEmail);
            } catch (EmailException e) {
                // TODO: what here?
            }
        }
        if (opponentEmail != null) {
            try {
                String message = String.format("%s has registered a match "
                        + "with you with the time %s and the score %s. "
                        + "You must log in and approve it for it to count.",
                        player.getName(), match.getTimeString(),
                        match.getScore());
                EmailSender.sendMail("Table Tennis - Match registered",
                        message, opponentEmail);
            } catch (EmailException e) {
                // TODO: what here?
            }
        }

        ServletUtil.redirect(response, "Profile?user=" + player.getUsername());
    }

    private void forwardWithPlayerlist(final HttpServletRequest request,
            final HttpServletResponse response, final HttpSession session)
            throws ServletException, IOException {
        Player player = (Player) session.getAttribute("player");
        PlayerDao playerDao = new PlayerDaoJpa();
        List<Player> playerlist = playerDao
                .getNonLockedPlayersExceptForOne(player);
        request.setAttribute("playerlist", playerlist);
        request.getRequestDispatcher(NEWMATCH_JSP).forward(request,
                response);
    }
}
