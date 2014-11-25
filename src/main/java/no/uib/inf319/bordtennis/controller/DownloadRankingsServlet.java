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

import no.uib.inf319.bordtennis.util.GenerateRankingsFile;

/**
 * Servlet implementation class DownloadRankingsServlet.
 */
@WebServlet("/DownloadRankings")
public final class DownloadRankingsServlet extends HttpServlet {
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
        GenerateRankingsFile.createTex();

        String fileName = "/usr/share/tomcat/tabletennis/ranking.tex";
        String fileType = "application/x-tex";
        //String fileName = "/usr/share/tomcat/tabletennis/ranking.pdf";
        //String fileType = "application/pdf";

        response.setContentType(fileType);
        response.setHeader("Content-disposition",
                "attachment; filename=elo.tex");
        //        "attachment; filename=elo.pdf");

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
