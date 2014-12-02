package no.uib.inf319.bordtennis.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.PropertiesDao;
import no.uib.inf319.bordtennis.dao.context.PlayerDaoJpa;
import no.uib.inf319.bordtennis.dao.context.PropertiesDaoFile;
import no.uib.inf319.bordtennis.model.Player;
import no.uib.inf319.bordtennis.model.RankingListPlayer;
import no.uib.inf319.bordtennis.util.ServletUtil;

/**
 * Servlet implementation class TestServlet.
 */
@WebServlet("/Home")
public final class HomepageServlet extends HttpServlet {
    /**
     * The url to the Homepage JSP.
     */
    public static final String INDEX_JSP = "WEB-INF/index.jsp";

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * DAO-object to access the database for player-data.
     */
    private PlayerDao playerDao = new PlayerDaoJpa();

    /**
     * DAO-objec to access properties.
     */
    private PropertiesDao propertiesDao = new PropertiesDaoFile();

    @Override
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        propertiesDao.retriveProperties();

        String inactiveLimitString = propertiesDao.getProperty("inactiveLimit");
        int inactiveLimit = Integer.parseInt(inactiveLimitString);

        Timestamp time = ServletUtil.findInactiveLimitTime(inactiveLimit);

        List<RankingListPlayer> activePlayers = playerDao.getActiveRankingListPlayers(time);
        request.setAttribute("activePlayers", activePlayers);
        List<RankingListPlayer> inactivePlayers = playerDao.getInactiveRankingListPlayers(time);
        request.setAttribute("inactivePlayers", inactivePlayers);
        List<Player> newPlayers = playerDao.getNewPlayers();
        request.setAttribute("newPlayers", newPlayers);

        request.getRequestDispatcher(INDEX_JSP).forward(request,
                response);
    }

    /**
     * Changes the implementations of the DAO-objects
     * to use to access the database.
     *
     * @param playerDao the new PlayerDao implementation.
     * @param propertiesDao the new PropertiesDao implementation.
     */
    public void setDaoImpl(final PlayerDao playerDao,
            final PropertiesDao propertiesDao) {
        this.playerDao = playerDao;
        this.propertiesDao = propertiesDao;
    }
}
