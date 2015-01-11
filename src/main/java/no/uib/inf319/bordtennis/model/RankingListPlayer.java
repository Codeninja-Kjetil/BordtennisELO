package no.uib.inf319.bordtennis.model;

import java.sql.Timestamp;

import no.uib.inf319.bordtennis.util.ServletUtil;

/**
 * A class that encapsulate a player-object with different other information
 * about that player. All of the information counts only approved matches.
 * It is used in the rankings list.
 *
 * @author Kjetil
 */
public final class RankingListPlayer {
    /**
     * The player.
     */
    private Player player;
    /**
     * The player's current elo rating.
     */
    private Integer elo;
    /**
     * The time to the last match the player played.
     */
    private Timestamp latestMatchTime;
    /**
     * Number of matches the player has played.
     */
    private Long amountOfMatches;

    /**
     * Constructor that fills in the fields of the object with the values
     * specified in the constructor parameters.
     *
     * @param player the player
     * @param elo the player's elo rating
     * @param latestMatchTime the player's last match's time
     * @param amountOfMatches number of matches played by the player
     */
    public RankingListPlayer(final Player player, final Integer elo,
            final Timestamp latestMatchTime, final Long amountOfMatches) {
        this.player = player;
        this.elo = elo;
        this.latestMatchTime = latestMatchTime;
        this.amountOfMatches = amountOfMatches;
    }

    /**
     * Gets {@link #player}.
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets {@link #player}.
     * @param player player
     */
    public void setPlayer(final Player player) {
        this.player = player;
    }

    /**
     * Gets {@link #elo}.
     * @return elo
     */
    public Integer getElo() {
        return elo;
    }

    /**
     * Sets {@link #elo}.
     * @param elo elo
     */
    public void setElo(final Integer elo) {
        this.elo = elo;
    }

    /**
     * Gets {@link #latestMatchTime}.
     * @return latestMatchTime
     */
    public Timestamp getLatestMatchTime() {
        return latestMatchTime;
    }

    /**
     * Sets {@link #latestMatchTime}.
     * @param latestMatchTime latestMatchTime
     */
    public void setLatestMatchTime(final Timestamp latestMatchTime) {
        this.latestMatchTime = latestMatchTime;
    }

    /**
     * Gets {@link #amountOfMatches}.
     * @return amountOfMatches
     */
    public Long getAmountOfMatches() {
        return amountOfMatches;
    }

    /**
     * Sets {@link #amountOfMatches}.
     * @param amountOfMatches amountOfMatches
     */
    public void setAmountOfMatches(final Long amountOfMatches) {
        this.amountOfMatches = amountOfMatches;
    }

    /**
     * Returns a string representation of {@link #latestMatchTime}.
     *
     * @return a string representation of the latest match time
     */
    public String getLatestMatchTimeString() {
        return ServletUtil.formatDate(latestMatchTime);
    }
}
