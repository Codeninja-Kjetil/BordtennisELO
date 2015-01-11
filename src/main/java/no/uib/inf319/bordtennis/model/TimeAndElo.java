package no.uib.inf319.bordtennis.model;

import java.sql.Timestamp;

/**
 * A class that encapsulate a match time and the elo rating a player had after
 * that match. It is used to make a player elo rating chart (which has time on
 * the X-axis and elo rating on the Y-axis).
 *
 * @author Kjetil
 */
public final class TimeAndElo {
    /**
     * Match time.
     */
    private Timestamp time;
    /**
     * Elo rating to a player after match.
     */
    private Integer elo;

    /**
     * Constructor that fills in the fields of the object with the values
     * specified in the constructor parameters.
     *
     * @param time Match time
     * @param elo Elo rating to a player after match
     */
    public TimeAndElo(final Timestamp time, final Integer elo) {
        this.time = time;
        this.elo = elo;
    }

    /**
     * Gets {@link #time}.
     * @return time
     */
    public Timestamp getTime() {
        return this.time;
    }

    /**
     * Sets {@link #time}.
     * @param time time
     */
    public void setTime(final Timestamp time) {
        this.time = time;
    }

    /**
     * Gets {@link #elo}.
     * @return elo
     */
    public Integer getElo() {
        return this.elo;
    }

    /**
     * Sets {@link #elo}.
     * @param elo elo
     */
    public void setElo(final Integer elo) {
        this.elo = elo;
    }

    @Override
    public String toString() {
        return "[" + this.time.getTime() + ", " + this.elo + "]";
    }
}
