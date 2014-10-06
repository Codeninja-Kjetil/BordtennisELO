package no.uib.inf319.bordtennis.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import no.uib.inf319.bordtennis.model.Player;
import no.uib.inf319.bordtennis.util.ServletUtil;

/**
 * Servlet implementation class AdminServlet.
 */
@WebServlet("/Admin")
public class AdminServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /*
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected final void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        HttpSession session = request.getSession(false);

        if (!isLoggedInAdmin(session)) {
            ServletUtil.sendToErrorPage(request, response, "Admin",
                    "You are not authorized to view this page.");
        } else {
            request.getRequestDispatcher("WEB-INF/admin.jsp").forward(request,
                    response);
        }
    }

    /*
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected final void doPost(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        // TODO: Auto-generated method stub
    }

    /**
     * Checks if the logged in player (if any) is an admin.
     *
     * @param session the session to check in.
     * @return <code>true</code> if logged in player is admin,
     * <code>false</code> otherwise
     */
    private boolean isLoggedInAdmin(HttpSession session) {
        return ServletUtil.isLoggedIn(session)
                && ((Player) session.getAttribute("player")).getAdmin();
    }
}
