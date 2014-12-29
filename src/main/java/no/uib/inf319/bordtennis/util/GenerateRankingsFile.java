package no.uib.inf319.bordtennis.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.PropertiesDao;
import no.uib.inf319.bordtennis.model.RankingListPlayer;

/**
 * Class containing static methods for creating the rankings pdf file.
 *
 * @author Kjetil
 */
public final class GenerateRankingsFile {

    /**
     * Start of the LaTeX file.
     */
    private static final String HEAD =
            "\\documentclass[a4paper]{article}\n"
            + "\\usepackage[a4paper]{geometry}\n"
            + "\\usepackage{fullpage}\n"
            + "\\usepackage[utf8]{inputenc}\n"
            + "\\usepackage{amssymb,amsmath,amsthm,stmaryrd}\n"
            + "\\usepackage{tabularx}\n\n"
            + "\\begin{document}\n"
            + "\\title{Algorithm group table tennis ranking}\n"
            + "\\author{}\n"
            + "\\maketitle\n\n"
            + "\\begin{center}\n"
            + "\\begin{tabular}{l | l | l  l | l | l}\n"
            + "\\# & Rating & Name && Last seen & Matches \\\\\n"
            + "\\hline\n";
    /**
     * End of the LaTeX file.
     */
    private static final String FOOT =
            "\\end{tabular}\n"
            + "\\end{center}\n\n"
            + "\\end{document}\n";

    /**
     * Directory containing the LaTeX file.
     */
    private static final String TEX_DIR = "/usr/share/tomcat/tabletennis/";

    /**
     * Name of LaTeX file.
     */
    private static final String TEX_FILE = "ranking.tex";

    /**
     * Private constructor.
     */
    private GenerateRankingsFile() {
    }

    /**
     * Creates a LaTeX file of the current rankings.
     *
     * @param propertiesDao propertiesDao
     * @param playerDao playerDao
     * @throws IOException IOException
     */
    public static synchronized void createTex(final PropertiesDao propertiesDao,
            final PlayerDao playerDao) throws IOException {
        propertiesDao.retriveProperties();
        String inactiveLimitString = propertiesDao.getProperty("inactiveLimit");
        int inactiveLimit = Integer.parseInt(inactiveLimitString);

        Timestamp time = ServletUtil.findInactiveLimitTime(inactiveLimit);

        List<RankingListPlayer> players =
                playerDao.getActiveRankingListPlayers(time);

        PrintWriter out = new PrintWriter(TEX_DIR + TEX_FILE, "UTF-8");
        out.print(HEAD);

        for (int i = 0; i < players.size(); i++) {
            RankingListPlayer p = players.get(i);
            out.printf(
                    "%d & %d & %s & (%s) & %td.%<tm.%<tY %<tH:%<tM & %d \\\\\n",
                    i + 1, p.getElo(), p.getPlayer().getName(),
                    p.getPlayer().getUsername(), p.getLatestMatchTime(),
                    p.getAmountOfMatches());
        }

        out.print(FOOT);
        out.flush();
        out.close();
    }

    /**
     * Creates a pdf file based on the rankings LaTeX file using pdfLaTeX.
     *
     * @return status code from pdfLaTeX
     * @throws IOException IOException
     * @throws InterruptedException InterruptedException
     */
    public static synchronized int createPdf() throws IOException,
            InterruptedException {
        List<String> command = new ArrayList<String>();
        command.add("pdflatex");
        command.add(TEX_FILE);
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(new File(TEX_DIR));

        Process process = processBuilder.start();
        int status = process.waitFor();

        return status;
    }
}
