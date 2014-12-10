package no.uib.inf319.bordtennis.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.PropertiesDao;
import no.uib.inf319.bordtennis.dao.context.PlayerDaoJpa;
import no.uib.inf319.bordtennis.dao.context.PropertiesDaoFile;
import no.uib.inf319.bordtennis.util.GenerateRankingsFile;
import no.uib.inf319.bordtennis.util.ServletUtil;

/**
 * Servlet implementation class DownloadRankingsServlet.
 */
@WebServlet("/DownloadRankings")
public final class DownloadRankingsServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * DAO-object to access the properties.
     */
    private PropertiesDao propertiesDao = new PropertiesDaoFile();

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
        GenerateRankingsFile.createTex(propertiesDao, playerDao);
        try {
            int status = GenerateRankingsFile.createPdf();
            if (status != 0) {
                ServletUtil.sendToErrorPage(request, response, "Rankings File",
                        "An error occured when trying to create the PDF-file.");
            }
        } catch (InterruptedException e) {
            ServletUtil.sendToErrorPage(request, response, "Rankings File",
                    e.toString());
        }

        String fileName = "/usr/share/tomcat/tabletennis/ranking.pdf";
        String fileType = "application/pdf";

        response.setContentType(fileType);
        response.setHeader("Content-disposition",
                "attachment; filename=elo.pdf");

        File file = new File(fileName);

        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(file);
        final int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int length;
        while ((length = in.read(buffer)) > 0) {
           out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
    }

}
