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

    public final Integer getResultid() {
        return this.resultid;
    }

    public final void setResultid(final Integer resultid) {
        this.resultid = resultid;
    }

    public final Integer getElo() {
        return this.elo;
    }

    public final void setElo(final Integer elo) {
        this.elo = elo;
    }

    public final Integer getPlayernumber() {
        return this.playernumber;
    }

    public final void setPlayernumber(final Integer playernumber) {
        this.playernumber = playernumber;
    }

    public final Match getMatch() {
        return this.match;
    }

    public final void setMatch(final Match match) {
        this.match = match;
    }

    public final Player getPlayer() {
        return this.player;
    }

    public final void setPlayer(final Player player) {
        this.player = player;
    }
}
