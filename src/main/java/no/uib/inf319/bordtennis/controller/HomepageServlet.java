package no.uib.inf319.bordtennis.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.context.PlayerDaoJpa;
import no.uib.inf319.bordtennis.model.Player;

/**
 * Servlet implementation class TestServlet.
 */
@WebServlet("/Home")
public final class HomepageServlet extends HttpServlet {
    public static final String INDEX_JSP = "WEB-INF/index.jsp";

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * DAO-object to access the database for player-data.
     */
    private PlayerDao playerDao = new PlayerDaoJpa();

    /*
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        List<Player> players = playerDao.getEloSortedPlayerList();
        request.setAttribute("players", players);

        request.getRequestDispatcher(INDEX_JSP).forward(request,
                response);
    }

    /**
     * Changes the implementations of the DAO-objects
     * to use to access the database.
     *
     * @param playerDao the new PlayerDao implementation.
     */
    public void setDaoImpl(final PlayerDao playerDao) {
        this.playerDao = playerDao;
    }
}
