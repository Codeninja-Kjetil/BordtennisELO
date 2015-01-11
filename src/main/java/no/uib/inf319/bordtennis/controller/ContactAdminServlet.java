package no.uib.inf319.bordtennis.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailException;

import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.context.PlayerDaoJpa;
import no.uib.inf319.bordtennis.model.Player;
import no.uib.inf319.bordtennis.util.EmailSender;
import no.uib.inf319.bordtennis.util.ServletUtil;

/**
 * Servlet that sends a email message from a user to all administrators.
 *
 * @author Kjetil
 */
@WebServlet("/ContactAdmin")
public final class ContactAdminServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The url to the Challenge JSP.
     */
    private static final String CONTACT_ADMIN_JSP = "WEB-INF/contactadmin.jsp";

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
        HttpSession session = request.getSession(false);
        if (!ServletUtil.isLoggedIn(session)) {
            ServletUtil.redirect(response, "Home");
            return;
        }

        request.getRequestDispatcher(CONTACT_ADMIN_JSP).forward(request,
                response);
    }

    /*
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
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

        String message = request.getParameter("message");
        if (ServletUtil.isEmptyString(message)) {
            request.setAttribute("error", "Please write a message.");
            request.getRequestDispatcher(CONTACT_ADMIN_JSP).forward(request,
                    response);
            return;
        }

        Player player = (Player) session.getAttribute("player");
        List<Player> admins = playerDao.getAdmins();

        String emailSubject = "Table Tennis - Message to Admin";
        String emailMessage = String.format(
                "Username: %s\nName: %s\nE-mail: %s\nMessage:\n%s",
                player.getUsername(), player.getName(), player.getEmail(),
                message);

        List<String> adminEmails = new ArrayList<String>();
        for (Player admin : admins) {
            if (!ServletUtil.isEmptyString(admin.getEmail())) {
                adminEmails.add(admin.getEmail());
            }
        }

        try {
            EmailSender.sendMail(emailSubject, emailMessage,
                    adminEmails.toArray(new String[adminEmails.size()]));
        } catch (EmailException e) {
            request.setAttribute("error", "An error occurred when trying to "
                    + "send the e-mail. Please try again later.");
            request.getRequestDispatcher(CONTACT_ADMIN_JSP).forward(request,
                    response);
            return;
        }

        ServletUtil.redirect(response, "Profile?user=" + player.getUsername());
    }

}
