package no.uib.inf319.bordtennis.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.PropertiesDao;
import no.uib.inf319.bordtennis.dao.context.PlayerDaoJpa;
import no.uib.inf319.bordtennis.dao.context.PropertiesDaoFile;
import no.uib.inf319.bordtennis.model.Player;
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

    private GenerateRankingsFile() {
    }

    public static void createTex() throws IOException {
        String fileName = "/usr/share/tomcat/tabletennis/ranking.tex";

        PropertiesDao propertiesDao = new PropertiesDaoFile();
        String activityLimitString = propertiesDao.getProperty("activityLimit");
        int activityLimit = Integer.parseInt(activityLimitString);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -activityLimit);
        Timestamp time = new Timestamp(cal.getTimeInMillis());

        PlayerDao playerDao = new PlayerDaoJpa();
        List<RankingListPlayer> players = playerDao.getRankingListPlayers(time);

        PrintWriter out = new PrintWriter(fileName, "UTF-8");
        out.print(HEAD);

        for (int i = 0; i < players.size(); i++) {
            RankingListPlayer p = players.get(i);
            out.printf("%d & %d & %s & (%s) & %s & %d \\\\\n",
                    i + 1, p.getElo(), p.getPlayer().getName(),
                    p.getPlayer().getUsername(), p.getLatestMatchTime(),
                    p.getAmountOfMatches());
        }

        out.print(FOOT);
        out.flush();
        out.close();
    }
}
