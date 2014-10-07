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
 * Servlet implementation class NewMatchServlet.
 */
@WebServlet("/Newmatch")
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
            Player player = (Player) session.getAttribute("player");
            PlayerDao playerDao = new PlayerDaoJpa();
            List<Player> playerlist = playerDao
                    .getAllPlayersExceptForOne(player);
            request.setAttribute("playerlist", playerlist);
            request.getRequestDispatcher(NEWMATCH_JSP).forward(request,
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

        Player player = (Player) session.getAttribute("player");

        String opponentUsername = request.getParameter("opponent");
        String victorString = request.getParameter("victor");
        String timeString = request.getParameter("time");

        if (ServletUtil.isEmptyString(opponentUsername)) {
            request.setAttribute("error", "Please select an opponent.");
            request.getRequestDispatcher(NEWMATCH_JSP).forward(request,
                    response);
            return;
        }

        if (ServletUtil.isEmptyString(victorString)) {
            request.setAttribute("error", "Please select a result.");
            request.getRequestDispatcher(NEWMATCH_JSP).forward(request,
                    response);
            return;
        }

        if (ServletUtil.isEmptyString(timeString)) {
            request.setAttribute("error",
                    "Please type in the time the match was played.");
            request.getRequestDispatcher(NEWMATCH_JSP).forward(request,
                    response);
            return;
        }

        PlayerDao playerDao = new PlayerDaoJpa();
        Player opponent = playerDao.find(opponentUsername);
        if (opponent == null) {
            request.setAttribute("error", "Please select a valid opponent.");
            request.getRequestDispatcher(NEWMATCH_JSP).forward(request,
                    response);
            return;
        }

        if (!victorString.equals("1") && !victorString.equals("2")) {
            request.setAttribute("error", "Please select a valid result.");
            request.getRequestDispatcher(NEWMATCH_JSP).forward(request,
                    response);
            return;
        }
        int victor = Integer.parseInt(victorString);

        DateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        // dateformat.setLenient(false);
        Timestamp time;
        try {
            Date date = dateformat.parse(timeString);
            time = new Timestamp(date.getTime());
        } catch (ParseException e) {
            request.setAttribute("error", "Please type in a valid time. "
                    + "A valid time format is: \"dd.mm.yyyy hh.mm.ss\".");
            request.getRequestDispatcher(NEWMATCH_JSP).forward(request,
                    response);
            return;
        }

        Match match = new Match();
        match.setTime(time);
        match.setVictor(victor);
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

        ServletUtil.redirect(response, "Profile");
    }
}
