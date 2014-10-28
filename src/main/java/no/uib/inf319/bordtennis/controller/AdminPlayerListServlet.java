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
 * Servlet implementation class AdminPlayerList.
 */
@WebServlet("/AdminPlayerList")
public final class AdminPlayerListServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /*
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        PlayerDao playerDao = new PlayerDaoJpa();
        List<Player> playerlist = playerDao.findAll();
        request.setAttribute("playerlist", playerlist);
        request.getRequestDispatcher("/WEB-INF/admin_playerlist.jsp").forward(
                request, response);
    }
}
