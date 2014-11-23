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
import no.uib.inf319.bordtennis.model.Player;
import no.uib.inf319.bordtennis.model.Result;
import no.uib.inf319.bordtennis.util.ServletUtil;

/**
 * Servlet implementation class AdminAddMatchServlet.
 */
@WebServlet("/AdminAddMatch")
public final class AdminAddMatchServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The url to the Admin Add Match JSP.
     */
    public static final String ADMIN_ADD_MATCH_JSP =
            "WEB-INF/admin_addmatch.jsp";

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
        forwardWithPlayerlist(request, response);
    }

    /*
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        String player1Username = request.getParameter("username1");
        String player2Username = request.getParameter("username2");
        String timeString = request.getParameter("time");
        String victorString = request.getParameter("victor");
        String approvedString = request.getParameter("approved");

        // Players
        if (ServletUtil.isEmptyString(player1Username)
                || ServletUtil.isEmptyString(player2Username)) {
            request.setAttribute("error", "Please select the players.");
            forwardWithPlayerlist(request, response);
            return;
        }

        if (player1Username.equals(player2Username)) {
            request.setAttribute("error",
                    "Please select two diffrent players.");
            forwardWithPlayerlist(request, response);
            return;
        }

        Player player1 = playerDao.find(player1Username);
        Player player2 = playerDao.find(player2Username);

        if (player1 == null || player2 == null) {
            request.setAttribute("error",
                    "Could not find one or both of the spesified players.");
            forwardWithPlayerlist(request, response);
            return;
        }

        // Victor
        if (!victorString.equals("1") && !victorString.equals("2")) {
            request.setAttribute("error", "Please select a valid victor.");
            forwardWithPlayerlist(request, response);
            return;
        }
        int victor = Integer.parseInt(victorString);

        // Time
        if (ServletUtil.isEmptyString(timeString)) {
            request.setAttribute("error",
                    "Please type in the time the match was played.");
            forwardWithPlayerlist(request, response);
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
            forwardWithPlayerlist(request, response);
            return;
        }

        // Approved
        if (!approvedString.equals("0") && !approvedString.equals("1")
                && !approvedString.equals("2") && !approvedString.equals("-1")
                && !approvedString.equals("-2")
                && !approvedString.equals("-3")) {
            request.setAttribute("error",
                    "Please select a valid approved value.");
            forwardWithPlayerlist(request, response);
            return;
        }
        int approved = Integer.parseInt(approvedString);

        // Create and store Match
        Match match = new Match();
        match.setTime(time);
        match.setVictor(victor);
        match.setApproved(approved);

        matchDao.create(match);

        Result result1 = new Result();
        result1.setMatch(match);
        result1.setPlayernumber(1);
        result1.setPlayer(player1);

        Result result2 = new Result();
        result2.setMatch(match);
        result2.setPlayernumber(2);
        result2.setPlayer(player2);

        resultDao.create(result1);
        resultDao.create(result2);

        if (approved == 0) {
            UpdateElo updateElo = new UpdateElo(playerDao, matchDao, resultDao);
            updateElo.updateElo(time);
        }

        ServletUtil.redirect(response, "AdminMatchList");
    }

    private void forwardWithPlayerlist(final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {
        List<Player> playerlist = playerDao.findAll();
        request.setAttribute("playerlist", playerlist);
        request.getRequestDispatcher(ADMIN_ADD_MATCH_JSP).forward(request,
                response);
    }
}
