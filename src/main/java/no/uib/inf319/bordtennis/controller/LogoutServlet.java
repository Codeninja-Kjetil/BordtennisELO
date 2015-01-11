package no.uib.inf319.bordtennis.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import no.uib.inf319.bordtennis.util.ServletUtil;

/**
 * Servlet that logs out the user from the application.
 *
 * @author Kjetil
 */
@WebServlet("/Logout")
public final class LogoutServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /*
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        ServletUtil.redirect(response, "Home");
    }

}
