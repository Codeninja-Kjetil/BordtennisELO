package no.uib.inf319.bordtennis.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.context.PlayerDaoJpa;

/**
 * The persistent class for the player database table.
 */
@Entity
@Table(name = "player")
public final class Player implements Serializable {
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
     * The path to the profile image to the player.
     */
    private String imagepath;

    /**
     * If the player is an administrator.
     * <code>true</code> if yes, <code>false</code> if no.
     */
    private Boolean admin;

    /**
     * Field telling if the players profile is private (if other people can see
     * it) or not.
     */
    private Boolean privateprofile;

    /**
     * Tells if a user is locked or not. A locked user can't log in.
     */
    private Boolean locked;

    /**
     * The match results this player has.
     */
    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY)
    @OrderBy("resultid")
    private List<Result> results;

    /**
     * The ELO-rating of the player. This field is not persisted in the player
     * table of the database.
     */
    @Transient
    private Integer elo = null;

    /**
     * The DAO object used to get the ELO rating to the player.
     */
    @Transient
    private transient PlayerDao playerDao = new PlayerDaoJpa();

    /**
     * Creates an empty Player object.
     */
    public Player() {
    }

    /**
     * Gets {@link #username}.
     * @return username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets {@link #username}.
     * @param username username
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Gets {@link #admin}.
     * @return admin
     */
    public Boolean getAdmin() {
        return this.admin;
    }

    /**
     * Sets {@link #admin}.
     * @param admin admin
     */
    public void setAdmin(final Boolean admin) {
        this.admin = admin;
    }

    /**
     * Gets {@link #name}.
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets {@link #name}.
     * @param name name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets {@link #password}.
     * @return password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets {@link #password}.
     * @param password password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Gets {@link #email}.
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets {@link #email}.
     * @param email email
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Gets {@link #privateprofile}.
     * @return privateprofile
     */
    public Boolean getPrivateprofile() {
        return privateprofile;
    }

    /**
     * Sets {@link #privateprofile}.
     * @param privateprofile privateprofile
     */
    public void setPrivateprofile(final Boolean privateprofile) {
        this.privateprofile = privateprofile;
    }

    /**
     * Gets {@link #imagepath}.
     * @return imagepath
     */
    public String getImagepath() {
        return imagepath;
    }

    /**
     * Sets {@link #imagepath}.
     * @param imagepath imagepath
     */
    public void setImagepath(final String imagepath) {
        this.imagepath = imagepath;
    }

    /**
     * Gets {@link #locked}.
     * @return locked
     */
    public Boolean getLocked() {
        return locked;
    }

    /**
     * Sets {@link #locked}.
     * @param locked locked
     */
    public void setLocked(final Boolean locked) {
        this.locked = locked;
    }

    /**
     * Gets {@link #elo}.
     * @return elo
     */
    public int getElo() {
        if (this.elo == null) {
            this.elo = playerDao.getLatestElo(this);
        }
        return this.elo;
    }

    /**
     * Change the PlayerDAO object to an other implementation.
     * @param dao the new PlayerDAo implementation
     */
    public void setPlayerDao(final PlayerDao dao) {
        this.playerDao = dao;
    }
}
