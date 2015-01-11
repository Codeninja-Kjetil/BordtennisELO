package no.uib.inf319.bordtennis.model;

import java.sql.Timestamp;

import no.uib.inf319.bordtennis.util.ServletUtil;

/**
 * A class that encapsulate different information about a match.
 * It is used in the Pending Matches list.
 *
 * @author Kjetil
 */
public final class PendingMatch {
    /**
     * The name of one of the players that played the match.
     */
    private String player;
    /**
     * The name of the other player that played the match.
     */
    private String opponent;
    /**
     * The time the match was played.
     *
     * @see Match#time
     */
    private Timestamp time;
    /**
     * {@link #player}'s playernumber.
     *
     * @see Result#playernumber
     */
    private int playernumber;
    /**
     * Which player won.
     *
     * @see Match#victor
     */
    private int victornumber;
    /**
     * The match score.
     *
     * @see Match#score
     */
    private String score;
    /**
     * The id of {@link #player} result linked to the match.
     *
     * @see Result#resultid
     */
    private int resultid;

    /**
     * Constructor that fills in the fields of the object with the values
     * specified in the constructor parameters.
     *
     * @param player name of player
     * @param opponent name of opponent
     * @param time the match time
     * @param playernumber {@link #player}'s playernumber for this match
     * @param victornumber the match victornumber
     * @param score the match score
     * @param resultid {@link #player}'s matchid for this match
     */
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

    /**
     * Gets {@link #player}.
     * @return player
     */
    public String getPlayer() {
        return player;
    }

    /**
     * Sets {@link #player}.
     * @param player player
     */
    public void setPlayer(final String player) {
        this.player = player;
    }

    /**
     * Gets {@link #opponent}.
     * @return opponent
     */
    public String getOpponent() {
        return opponent;
    }

    /**
     * Sets {@link #opponent}.
     * @param opponent opponent
     */
    public void setOpponent(final String opponent) {
        this.opponent = opponent;
    }

    /**
     * Gets {@link #time}.
     * @return time
     */
    public Timestamp getTime() {
        return time;
    }

    /**
     * Sets {@link #time}.
     * @param time time
     */
    public void setTime(final Timestamp time) {
        this.time = time;
    }

    /**
     * Gets {@link #playernumber}.
     * @return playernumber
     */
    public int getPlayernumber() {
        return playernumber;
    }

    /**
     * Sets {@link #playernumber}.
     * @param playernumber playernumber
     */
    public void setPlayernumber(final int playernumber) {
        this.playernumber = playernumber;
    }

    /**
     * Gets {@link #victornumber}.
     * @return victornumber
     */
    public int getVictornumber() {
        return victornumber;
    }

    /**
     * Sets {@link #victornumber}.
     * @param victornumber victornumber
     */
    public void setVictornumber(final int victornumber) {
        this.victornumber = victornumber;
    }

    /**
     * Gets {@link #resultid}.
     * @return resultid
     */
    public int getResultid() {
        return resultid;
    }

    /**
     * Sets {@link #resultid}.
     * @param resultid resultid
     */
    public void setResultid(final int resultid) {
        this.resultid = resultid;
    }

    /**
     * Gets {@link #score}.
     * @return score
     */
    public String getScore() {
        return score;
    }

    /**
     * Sets {@link #score}.
     * @param score score
     */
    public void setScore(final String score) {
        this.score = score;
    }

    /**
     * Calculates if {@link #player} is the victor of the match.
     *
     * @return <code>true</code> if {@link #player} is the victor of the match,
     * <code>false</code> otherwise
     */
    public boolean isVictor() {
        return playernumber == victornumber;
    }

    /**
     * Returns a string representation of {@link #time}.
     *
     * @return a string representation of the match time
     */
    public String getFormatTime() {
        return ServletUtil.formatDate(time);
    }
}
