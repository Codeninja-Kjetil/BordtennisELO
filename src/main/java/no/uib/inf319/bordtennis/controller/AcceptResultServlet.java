package no.uib.inf319.bordtennis.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import no.uib.inf319.bordtennis.business.UpdateElo;
import no.uib.inf319.bordtennis.dao.MatchDao;
import no.uib.inf319.bordtennis.dao.ResultDao;
import no.uib.inf319.bordtennis.dao.context.MatchDaoJpa;
import no.uib.inf319.bordtennis.dao.context.ResultDaoJpa;
import no.uib.inf319.bordtennis.model.Match;
import no.uib.inf319.bordtennis.model.Player;
import no.uib.inf319.bordtennis.model.Result;
import no.uib.inf319.bordtennis.util.ServletUtil;

/**
 * Servlet implementation class AcceptResultServlet.
 */
@WebServlet("/Acceptresult")
public class AcceptResultServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /*
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected final void doPost(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        HttpSession session = request.getSession(false);
        if (!ServletUtil.isPlayerLoggedIn(session)) {
            // Not logged in
            ServletUtil.redirect(response, "Home");
            return;
        }

        String resultString = request.getParameter("resultid");
        String acceptmethod = request.getParameter("method");

        if (resultString == null || acceptmethod == null
                || !acceptmethod.equals("accept")
                || !acceptmethod.equals("deny")) {
            // Bad request
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        int resultNumber;
        try {
            resultNumber = Integer.parseInt(resultString);
        } catch (NumberFormatException e) {
            // resultString is not a number
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        ResultDao rdao = new ResultDaoJpa();
        Result result = rdao.find(resultNumber);
        if (result == null) {
            // No result with that id
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Player resultPlayer = result.getPlayer();
        Match match = result.getMatch();
        Player loggedinPlayer = (Player) session.getAttribute("player");

        if (!resultPlayer.getUsername().equals(loggedinPlayer.getUsername())) {
            // Not correct player logged in
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MatchDao mdao = new MatchDaoJpa();
        int newApproved = acceptmethod.equals("accept") ? 0 : -match
                .getApproved();
        if (result.getPlayernumber().equals(match.getApproved())) {
            match.setApproved(newApproved);
            mdao.edit(match);
            if (acceptmethod.equals("accept")) {
                UpdateElo.updateElo(match.getTime());
            }
        }

        ServletUtil.redirect(response, "Mypage");
    }

}
