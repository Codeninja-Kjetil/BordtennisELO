package no.uib.inf319.bordtennis.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.context.PlayerDaoJpa;
import no.uib.inf319.bordtennis.model.Player;
import no.uib.inf319.bordtennis.util.ServletUtil;

/**
 * Servlet implementation class UploadFileServlet.
 */
@WebServlet("/UploadProfileImage")
@MultipartConfig
public final class UploadFileServlet extends HttpServlet {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The url to the UploadFile JSP.
     */
    private static final String UPLOADFILE_JSP = "WEB-INF/uploadfile.jsp";

    /*
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        HttpSession session = request.getSession(false);
        if (!ServletUtil.isLoggedIn(session)) {
            ServletUtil.redirect(response, "Home");
        } else {
            request.getRequestDispatcher(UPLOADFILE_JSP).forward(request,
                    response);
        }

    }

    /*
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
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
        Part filePart = request.getPart("file");

        String dirPath = getServletContext().getInitParameter("fileUpload");
        String fileName = getFileName(filePart);
        String filePath = dirPath + fileName;

        if (!isValidImageFormat(fileName)) {
            ServletUtil.sendToErrorPage(request, response,
                    "Upload Profile Image", "Not a valid image file format.");
        }

        OutputStream out = null;
        InputStream filecontent = null;

        try {
            out = new FileOutputStream(new File(filePath));
            filecontent = filePart.getInputStream();

            int read = 0;

            final int kiloByteSize = 1024;
            byte[] bytes = new byte[kiloByteSize];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (FileNotFoundException fne) {
            ServletUtil.sendToErrorPage(request, response,
                    "Upload Profile Image", "Failed to upload image. "
                            + "<br/> ERROR: " + fne.getMessage());
            return;
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
        }

        String imagePath = getServletContext().getInitParameter("imageWebDir")
                + fileName;

        Player logedinPlayer = (Player) session.getAttribute("player");

        PlayerDao playerDao = new PlayerDaoJpa();
        Player player = playerDao.find(logedinPlayer.getUsername());
        player.setImagepath(imagePath);
        playerDao.edit(player);

        session.setAttribute("player", player);

        ServletUtil.redirect(response, "Profile?user=" + player.getUsername());
    }

    /**
     * Gets the filename from a file-"part" from a
     * <code>multipart/form-data</code> POST request.
     *
     * @param part the file "part"
     * @return filename of the file
     */
    private String getFileName(final Part part) {
        String partHeader = part.getHeader("content-disposition");
        for (String content : partHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim()
                        .replace("\"", "");
            }
        }
        return null;
    }

    /**
     * Get the extension of a filename.
     * @param filename the filename
     * @return extension of the filename
     */
    private String getExtensionFromFileName(final String filename) {
        String[] fileparts = filename.split("//.");
        return fileparts[fileparts.length - 1];
    }

    /**
     * Checks if a file is of a valid image file format.
     * The valid file formats is:
     * <ul>
     * <li>.png</li>
     * <li>.jpg/.jpeg</li>
     * <li>.bmp</li>
     * <li>.gif</li>
     * </ul>
     *
     * @param filename the filename to check
     * @return <code>true</code> if it's a valid image file format,
     * <code>false</code> otherwise
     */
    private boolean isValidImageFormat(final String filename) {
        String extension = getExtensionFromFileName(filename);
        return extension.equalsIgnoreCase("png")
                || extension.equalsIgnoreCase("jpg")
                || extension.equalsIgnoreCase("jpeg")
                || extension.equalsIgnoreCase("bmp")
                || extension.equalsIgnoreCase("gif");
    }
}
