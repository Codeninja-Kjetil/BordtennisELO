package no.uib.inf319.bordtennis.model;

import java.sql.Timestamp;

import no.uib.inf319.bordtennis.util.ServletUtil;

public final class PendingMatch {
    private String player;
    private String opponent;
    private Timestamp time;
    private int playernumber;
    private int victornumber;
    private String score;
    private int resultid;

    public PendingMatch() {

    }

    public PendingMatch(final String player, final String opponent,
            final Timestamp time, final int playernumber,
            final int victornumber, final String score, final int resultid) {
        this.player = player;
        this.opponent = opponent;
        this.time = time;
        this.playernumber = playernumber;
        this.victornumber = victornumber;
        this.setScore(score);
        this.resultid = resultid;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(final String player) {
        this.player = player;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(final String opponent) {
        this.opponent = opponent;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(final Timestamp time) {
        this.time = time;
    }

    public int getPlayernumber() {
        return playernumber;
    }

    public void setPlayernumber(final int playernumber) {
        this.playernumber = playernumber;
    }

    public int getVictornumber() {
        return victornumber;
    }

    public void setVictornumber(final int victornumber) {
        this.victornumber = victornumber;
    }

    public int getResultid() {
        return resultid;
    }

    public void setResultid(final int resultid) {
        this.resultid = resultid;
    }

    public boolean isVictor() {
        return playernumber == victornumber;
    }

    public String getFormatTime() {
        return ServletUtil.formatDate(time);
    }

    public String getScore() {
        return score;
    }

    public void setScore(final String score) {
        this.score = score;
    }
}
