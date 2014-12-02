package no.uib.inf319.bordtennis.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.uib.inf319.bordtennis.dao.PropertiesDao;
import no.uib.inf319.bordtennis.dao.context.PropertiesDaoFile;
import no.uib.inf319.bordtennis.util.ServletUtil;

/**
 * Servlet implementation class AdminEditInactiveLimitServlet.
 */
@WebServlet("/AdminEditInactiveLimit")
public final class AdminEditInactiveLimitServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The url to the AdminEditInactiveLimit JSP.
     */
    public static final String ADMIN_EDIT_INACTIVE_LIMIT_JSP =
            "WEB-INF/admin_editinactivelimit.jsp";

    /*
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        PropertiesDao propertiesDao = new PropertiesDaoFile();
        propertiesDao.retriveProperties();
        forwardToJspWithProperty(propertiesDao, request, response);
    }

    /*
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        PropertiesDao propertiesDao = new PropertiesDaoFile();
        propertiesDao.retriveProperties();
        String inactiveLimitString = request.getParameter("inactiveLimit");
        if (ServletUtil.isEmptyString(inactiveLimitString)) {
            request.setAttribute("error", "Please write a positive integer "
                    + "value to the Inactive Limit field.");
            forwardToJspWithProperty(propertiesDao, request, response);
            return;
        }

        try {
            int inactiveLimit = Integer.parseInt(inactiveLimitString);

            if (inactiveLimit <= 0) {
                request.setAttribute("error", "Please write a positive integer "
                        + "value to the Inactive Limit field.");
                forwardToJspWithProperty(propertiesDao, request, response);
                return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Please write a positive integer "
                    + "value to the Inactive Limit field.");
            forwardToJspWithProperty(propertiesDao, request, response);
            return;
        }

        propertiesDao.setProperty("inactiveLimit", inactiveLimitString);
        propertiesDao.persistProperties();
        ServletUtil.redirect(response, "Admin");
    }

    private static void forwardToJspWithProperty(
            final PropertiesDao propertiesDao,
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {
        String inactiveLimitString = propertiesDao.getProperty("inactiveLimit");
        request.setAttribute("inactiveLimit", inactiveLimitString);
        request.getRequestDispatcher(ADMIN_EDIT_INACTIVE_LIMIT_JSP).forward(
                request, response);
    }
}
