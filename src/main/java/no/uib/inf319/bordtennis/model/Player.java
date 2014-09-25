package no.uib.inf319.bordtennis.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.context.PlayerDaoJpa;

/**
 * The persistent class for the player database table.
 */
@Entity
public class Player implements Serializable {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The username of the player. It is used when logging on, and is unique for
     * each player.
     */
    @Id
    private String username;

    /**
     * If the player is an administrator.
     * <code>true</code> if yes, <code>false</code> if no.
     */
    private Boolean admin;

    /**
     * The name of the player.
     */
    private String name;

    /**
     * The password used to log in. It is hashed using SHA-256 algorithm.
     * @see no.uib.inf319.bordtennis.util.Sha256HashUtil#sha256hash(String)
     */
    private String password;

    /**
     * The players email address.
     */
    private String email;

    /**
     * The ELO-rating of the player. This field is not persisted in the player
     * table of the database.
     */
    @Transient
    private Integer elo = null;

    /**
     * Creates an empty Player object.
     */
    public Player() {
    }

    /**
     * Gets {@link #username}.
     * @return username
     */
    public final String getUsername() {
        return this.username;
    }

    /**
     * Sets {@link #username}.
     * @param username username
     */
    public final void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Gets {@link #admin}.
     * @return admin
     */
    public final Boolean getAdmin() {
        return this.admin;
    }

    /**
     * Sets {@link #admin}.
     * @param admin admin
     */
    public final void setAdmin(final Boolean admin) {
        this.admin = admin;
    }

    /**
     * Gets {@link #name}.
     * @return name
     */
    public final String getName() {
        return this.name;
    }

    /**
     * Sets {@link #name}.
     * @param name name
     */
    public final void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets {@link #password}.
     * @return password
     */
    public final String getPassword() {
        return this.password;
    }

    /**
     * Sets {@link #password}.
     * @param password password
     */
    public final void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Gets {@link #email}.
     * @return email
     */
    public final String getEmail() {
        return email;
    }

    /**
     * Sets {@link #email}.
     * @param email email
     */
    public final void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Gets {@link #elo}.
     * @return elo
     */
    public final int getElo() {
        if (this.elo == null) {
            final PlayerDao dao = new PlayerDaoJpa();
            this.elo = dao.getLatestElo(this);
        }
        return this.elo;
    }
}
