package no.uib.inf319.bordtennis.model;

import java.sql.Timestamp;

import no.uib.inf319.bordtennis.util.ServletUtil;

public class PendingMatch {
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

    public final String getPlayer() {
        return this.player;
    }

    public final void setPlayer(final String player) {
        this.player = player;
    }

    public final String getOpponent() {
        return this.opponent;
    }

    public final void setOpponent(final String opponent) {
        this.opponent = opponent;
    }

    public final Timestamp getTime() {
        return this.time;
    }

    public final void setTime(final Timestamp time) {
        this.time = time;
    }

    public final int getPlayernumber() {
        return this.playernumber;
    }

    public final void setPlayernumber(final int playernumber) {
        this.playernumber = playernumber;
    }

    public final int getVictornumber() {
        return this.victornumber;
    }

    public final void setVictornumber(final int victornumber) {
        this.victornumber = victornumber;
    }

    public final int getResultid() {
        return this.resultid;
    }

    public final void setResultid(final int resultid) {
        this.resultid = resultid;
    }

    public final boolean isVictor() {
        return this.playernumber == this.victornumber;
    }

    public final String getFormatTime() {
        return ServletUtil.formatDate(this.time);
    }
}
