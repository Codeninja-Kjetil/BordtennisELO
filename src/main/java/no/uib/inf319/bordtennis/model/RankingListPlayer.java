package no.uib.inf319.bordtennis.model;

import java.sql.Timestamp;

import no.uib.inf319.bordtennis.util.ServletUtil;

public final class RankingListPlayer {
    private Player player;
    private Integer elo;
    private Timestamp latestMatchTime;
    private Long amountOfMatches;

    public RankingListPlayer(final Player player, final Integer elo,
            final Timestamp latestMatchTime, final Long amountOfMatches) {
        this.player = player;
        this.elo = elo;
        this.latestMatchTime = latestMatchTime;
        this.amountOfMatches = amountOfMatches;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }

    public Integer getElo() {
        return elo;
    }

    public void setElo(final Integer elo) {
        this.elo = elo;
    }

    public Timestamp getLatestMatchTime() {
        return latestMatchTime;
    }

    public void setLatestMatchTime(final Timestamp latestMatchTime) {
        this.latestMatchTime = latestMatchTime;
    }

    public Long getAmountOfMatches() {
        return amountOfMatches;
    }

    public void setAmountOfMatches(final Long amountOfMatches) {
        this.amountOfMatches = amountOfMatches;
    }

    public String getLatestMatchTimeString() {
        return ServletUtil.formatDate(latestMatchTime);
    }
}
