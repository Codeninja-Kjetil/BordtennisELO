package no.uib.inf319.bordtennis.model;

import java.sql.Timestamp;

import no.uib.inf319.bordtennis.util.ServletUtil;

public final class PendingMatch {
    private String player;
    private String opponent;
    private Timestamp time;
    private int playernumber;
    private int victornumber;
    private int resultid;

    public PendingMatch() {

    }

    public PendingMatch(final String player, final String opponent,
            final Timestamp time, final int playernumber,
            final int victornumber, final int resultid) {
        this.player = player;
        this.opponent = opponent;
        this.time = time;
        this.playernumber = playernumber;
        this.victornumber = victornumber;
        this.resultid = resultid;
    }

    public String getPlayer() {
        return this.player;
    }

    public void setPlayer(final String player) {
        this.player = player;
    }

    public String getOpponent() {
        return this.opponent;
    }

    public void setOpponent(final String opponent) {
        this.opponent = opponent;
    }

    public Timestamp getTime() {
        return this.time;
    }

    public void setTime(final Timestamp time) {
        this.time = time;
    }

    public int getPlayernumber() {
        return this.playernumber;
    }

    public void setPlayernumber(final int playernumber) {
        this.playernumber = playernumber;
    }

    public int getVictornumber() {
        return this.victornumber;
    }

    public void setVictornumber(final int victornumber) {
        this.victornumber = victornumber;
    }

    public int getResultid() {
        return this.resultid;
    }

    public void setResultid(final int resultid) {
        this.resultid = resultid;
    }

    public boolean isVictor() {
        return this.playernumber == this.victornumber;
    }

    public String getFormatTime() {
        return ServletUtil.formatDate(this.time);
    }
}
