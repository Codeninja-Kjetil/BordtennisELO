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

import no.uib.inf319.bordtennis.business.UpdateElo;
import no.uib.inf319.bordtennis.dao.MatchDao;
import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.ResultDao;
import no.uib.inf319.bordtennis.dao.context.MatchDaoJpa;
import no.uib.inf319.bordtennis.dao.context.PlayerDaoJpa;
import no.uib.inf319.bordtennis.dao.context.ResultDaoJpa;
import no.uib.inf319.bordtennis.model.Match;
import no.uib.inf319.bordtennis.model.MatchWithPlayerNames;
import no.uib.inf319.bordtennis.model.Player;
import no.uib.inf319.bordtennis.model.Result;
import no.uib.inf319.bordtennis.util.ServletUtil;

/**
 * Servlet implementation class AdminAddMatchServlet.
 */
@WebServlet("/AdminEditMatch")
public final class AdminEditMatchServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The url to the Admin Edit Match JSP.
     */
    public static final String ADMIN_EDIT_MATCH_JSP =
            "WEB-INF/admin_editmatch.jsp";

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
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        String matchidString = request.getParameter("matchid");
        if (ServletUtil.isEmptyString(matchidString)) {
            ServletUtil.sendToErrorPage(request, response, "Admin - Edit Match",
                    "ERROR: No matchid spesified.");
            return;
        }

        Integer matchid;
        try {
            matchid = Integer.parseInt(matchidString);
        } catch (NumberFormatException e) {
            ServletUtil.sendToErrorPage(request, response, "Admin - Edit Match",
                    "ERROR: Invalid matchid.");
            return;
        }

        MatchWithPlayerNames matchWithPlayers =
                matchDao.getMatchWithPlayerUsernames(matchid);
        if (matchWithPlayers == null) {
            ServletUtil.sendToErrorPage(request, response, "Admin - Edit Match",
                    "ERROR: No match with that matchid.");
            return;
        }

        forwardWithMatchAndPlayerlist(request, response, matchWithPlayers);
    }

    /*
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        String matchidString = request.getParameter("matchid");
        if (ServletUtil.isEmptyString(matchidString)) {
            ServletUtil.sendToErrorPage(request, response, "Admin - Edit Match",
                    "ERROR: No matchid spesified.");
            return;
        }

        Integer matchid;
        try {
            matchid = Integer.parseInt(matchidString);
        } catch (NumberFormatException e) {
            ServletUtil.sendToErrorPage(request, response, "Admin - Edit Match",
                    "ERROR: Invalid matchid.");
            return;
        }

        MatchWithPlayerNames matchWithPlayers =
                matchDao.getMatchWithPlayerUsernames(matchid);
        if (matchWithPlayers == null) {
            ServletUtil.sendToErrorPage(request, response, "Admin - Edit Match",
                    "ERROR: No match with that matchid.");
            return;
        }

        String player1Username = request.getParameter("username1");
        String player2Username = request.getParameter("username2");
        String timeString = request.getParameter("time");
        String victorString = request.getParameter("victor");
        String approvedString = request.getParameter("approved");

        // Players
        if (ServletUtil.isEmptyString(player1Username)
                || ServletUtil.isEmptyString(player2Username)) {
            request.setAttribute("error", "Please select the players.");
            forwardWithMatchAndPlayerlist(request, response, matchWithPlayers);
            return;
        }

        if (player1Username.equals(player2Username)) {
            request.setAttribute("error",
                    "Please select two diffrent players.");
            forwardWithMatchAndPlayerlist(request, response, matchWithPlayers);
            return;
        }

        Player player1 = playerDao.find(player1Username);
        Player player2 = playerDao.find(player2Username);

        if (player1 == null || player2 == null) {
            request.setAttribute("error",
                    "Could not find one or both of the spesified players.");
            forwardWithMatchAndPlayerlist(request, response, matchWithPlayers);
            return;
        }

        // Victor
        if (!victorString.equals("1") && !victorString.equals("2")) {
            request.setAttribute("error", "Please select a valid victor.");
            forwardWithMatchAndPlayerlist(request, response, matchWithPlayers);
            return;
        }
        int victor = Integer.parseInt(victorString);

        // Time
        if (ServletUtil.isEmptyString(timeString)) {
            request.setAttribute("error",
                    "Please type in the time the match was played.");
            forwardWithMatchAndPlayerlist(request, response, matchWithPlayers);
            return;
        }

        DateFormat dateformat = new SimpleDateFormat("dd.MM.yy HH:mm");
        Timestamp time;
        try {
            Date date = dateformat.parse(timeString);
            time = new Timestamp(date.getTime());
        } catch (ParseException e) {
            request.setAttribute("error", "Please type in a valid time. "
                    + "A valid time format is: \"dd.mm.yy hh:mm\".");
            forwardWithMatchAndPlayerlist(request, response, matchWithPlayers);
            return;
        }

        // Approved
        if (!approvedString.equals("0") && !approvedString.equals("1")
                && !approvedString.equals("2") && !approvedString.equals("-1")
                && !approvedString.equals("-2")
                && !approvedString.equals("-3")) {
            request.setAttribute("error",
                    "Please select a valid approved value.");
            forwardWithMatchAndPlayerlist(request, response, matchWithPlayers);
            return;
        }
        int approved = Integer.parseInt(approvedString);

        // Get Match and Results from Database
        Match match = matchDao.find(matchid);
        List<Result> results = resultDao.getResultsFromMatch(match);
        Result result1 = results.get(0);
        Result result2 = results.get(1);

        Timestamp updateTime = (match.getTime().compareTo(time) < 0
                ? match.getTime() : time);

        // Edit Match
        match.setTime(time);
        match.setVictor(victor);
        match.setApproved(approved);

        result1.setPlayer(player1);
        result2.setPlayer(player2);

        matchDao.edit(match);
        resultDao.edit(result1);
        resultDao.edit(result2);

        UpdateElo updateElo = new UpdateElo(playerDao, matchDao, resultDao);
        updateElo.updateElo(updateTime);

        ServletUtil.redirect(response, "AdminMatchList");
    }

    private void forwardWithMatchAndPlayerlist(final HttpServletRequest request,
            final HttpServletResponse response, MatchWithPlayerNames match)
            throws ServletException, IOException {
        List<Player> playerlist = playerDao.findAll();
        request.setAttribute("playerlist", playerlist);
        request.setAttribute("match", match);
        request.getRequestDispatcher(ADMIN_EDIT_MATCH_JSP).forward(request,
                response);
    }
}
