package no.uib.inf319.bordtennis.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.PropertiesDao;
import no.uib.inf319.bordtennis.dao.context.PlayerDaoJpa;
import no.uib.inf319.bordtennis.model.RankingListPlayer;

public final class GenerateRankingsFile {

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
    private static final String FOOT =
            "\\end{tabular}\n"
            + "\\end{center}\n\n"
            + "\\end{document}\n";

    private static final String TEX_DIR = "/usr/share/tomcat/tabletennis/";

    private static final String TEX_FILE = "ranking.tex";

    private GenerateRankingsFile() {
    }

    public static void createTex(PropertiesDao propertiesDao)
            throws IOException {
        propertiesDao.retriveProperties();
        String inactiveLimitString = propertiesDao.getProperty("inactiveLimit");
        int inactiveLimit = Integer.parseInt(inactiveLimitString);

        Timestamp time = ServletUtil.findInactiveLimitTime(inactiveLimit);

        PlayerDao playerDao = new PlayerDaoJpa();
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

    public static void createPdf() throws IOException, InterruptedException {
        List<String> command = new ArrayList<String>();
        command.add("pdflatex");
        command.add(TEX_FILE);
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(new File(TEX_DIR));

        Process process = processBuilder.start();
        int status = process.waitFor();

        if (status != 0) {
            // Something went wrong
        }
    }
}
