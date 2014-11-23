package no.uib.inf319.bordtennis.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * The persistent class for the match database table.
 */
@Entity
@Table(name = "ttmatch")
public final class Match implements Serializable {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * An unique ID for a match.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    private Integer matchid;

    /**
     * The date and time the match was played.
     */
    private Timestamp time;

    /**
     * Which player won. Is a number between 1 and 2.
     */
    private Integer victor;

    /**
     * A number telling if the match is approved or not based on these criteria:
     * <ul>
     * <li>0 = both approved the result of the match.</li>
     * <li>1 = waiting for player 1 to approve.</li>
     * <li>2 = waiting for player 2 to approve.</li>
     * <li>-1 = player 1 did not approve.</li>
     * <li>-2 = player 2 did not approve.</li>
     * <li>-3 = an admin did not approve.</li>
     * </ul>
     */
    private Integer approved;

    /**
     * The results associated with this match. Should be only two results per
     * match.
     */
    // bi-directional many-to-one association to Result
    @OneToMany(mappedBy = "match")
    @OrderBy("playernumber")
    //@Transient
    private List<Result> results;

    /**
     * Creates an empty Match object.
     */
    public Match() {
    }

    /**
     * Gets {@link #matchid}.
     * @return matchid
     */
    public Integer getMatchid() {
        return this.matchid;
    }

    /**
     * Sets {@link #matchid}.
     * @param matchid matchid
     */
    public void setMatchid(final Integer matchid) {
        this.matchid = matchid;
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
     * Returns a string representation of {@link #time}.
     *
     * @return a string representation of the match time
     */
    public String getTimeString() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
        return dateFormat.format(time);
    }

    /**
     * Gets {@link #victor}.
     * @return victor
     */
    public Integer getVictor() {
        return this.victor;
    }

    /**
     * Sets {@link #victor}.
     * @param victor victor
     */
    public void setVictor(final Integer victor) {
        this.victor = victor;
    }

    /**
     * Gets {@link #approved}.
     * @return approved
     */
    public Integer getApproved() {
        return this.approved;
    }

    /**
     * Sets {@link #approved}.
     * @param approved approved
     */
    public void setApproved(final Integer approved) {
        this.approved = approved;
    }

    /**
     * Gets {@link #results}.
     * @return results
     */
    public List<Result> getResults() {
        return this.results;
    }

    /**
     * Sets {@link #results}.
     * @param results results
     */
    public void setResults(final List<Result> results) {
        this.results = results;
    }
}
