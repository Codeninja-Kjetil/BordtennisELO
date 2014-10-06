package no.uib.inf319.bordtennis.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * The persistent class for the result database table.
 */
@Entity
public class Result implements Serializable {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * An unique ID for a result.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    private Integer resultid;

    /**
     * The ELO-rating of the {@link #player} at the point in time after the
     * associated {@link #match} is played.
     */
    private Integer elo;

    /**
     * Tells what is the player is 'Player 1' or 'Player 2' in the
     * {@link #match}.
     */
    private Integer playernumber;

    /**
     * The match this result is associated with.
     */
    // bi-directional many-to-one association to Match
    @ManyToOne
    @JoinColumn(name = "matchid")
    private Match match;

    /**
     * The player that got this result in the associated {@link #match}.
     */
    // uni-directional many-to-one association to Player
    @ManyToOne
    @JoinColumn(name = "playerid")
    private Player player;

    /**
     * Creates an empty Result object.
     */
    public Result() {
    }

    /**
     * Gets {@link #resultid}.
     * @return resultid
     */
    public final Integer getResultid() {
        return this.resultid;
    }

    /**
     * Sets {@link #resultid}.
     * @param resultid resultid
     */
    public final void setResultid(final Integer resultid) {
        this.resultid = resultid;
    }

    /**
     * Gets {@link #elo}.
     * @return elo
     */
    public final Integer getElo() {
        return this.elo;
    }

    /**
     * Sets {@link #elo}.
     * @param elo elo
     */
    public final void setElo(final Integer elo) {
        this.elo = elo;
    }

    /**
     * Gets {@link #playernumber}.
     * @return playernumber
     */
    public final Integer getPlayernumber() {
        return this.playernumber;
    }

    /**
     * Sets {@link #playernumber}.
     * @param playernumber playernumber
     */
    public final void setPlayernumber(final Integer playernumber) {
        this.playernumber = playernumber;
    }

    /**
     * Gets {@link #match}.
     * @return match
     */
    public final Match getMatch() {
        return this.match;
    }

    /**
     * Sets {@link #match}.
     * @param match match
     */
    public final void setMatch(final Match match) {
        this.match = match;
    }

    /**
     * Gets {@link #player}.
     * @return player
     */
    public final Player getPlayer() {
        return this.player;
    }

    /**
     * Sets {@link #player}.
     * @param player player
     */
    public final void setPlayer(final Player player) {
        this.player = player;
    }
}
