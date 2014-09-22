package no.uib.inf319.bordtennis.dao.context;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import no.uib.inf319.bordtennis.business.EloRating;
import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.model.Player;
import no.uib.inf319.bordtennis.model.TimeAndElo;
import no.uib.inf319.bordtennis.util.PlayerEloComparator;

/**
 * An implementation of the {@link PlayerDao}-interface using JPA.
 *
 * @author Kjetil
 */
public final class PlayerDaoJpa extends AbstractDaoJpa<Player> implements
        PlayerDao {

    /**
     * EntityManagerFactory.
     */
    private final EntityManagerFactory factory;

    /**
     * Creates a PlayerDaoJpa instance.
     */
    public PlayerDaoJpa() {
        super(Player.class);
        this.factory = Persistence.createEntityManagerFactory("BordtennisELO");
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.factory.createEntityManager();
    }

    @Override
    public List<Player> getEloSortedPlayerList() {
        List<Player> players = this.findAll();
        Collections.sort(players, new PlayerEloComparator());
        Collections.reverse(players);
        return players;
    }

    @Override
    public int getLatestElo(final Player player) {
        try {
            EntityManager em = this.factory.createEntityManager();
            TypedQuery<Object[]> q = em.createQuery(
                    "SELECT r.elo, m.time "
                    + "FROM Result r, Match m "
                    + "WHERE r.player = :player "
                            + "AND r.match = m "
                            + "AND m.approved = 0 "
                    + "ORDER BY m.time DESC",
                    Object[].class);
            q.setParameter("player", player);
            q.setMaxResults(1);
            Object[] res = q.getSingleResult();
            return (Integer) res[0];
        } catch (NoResultException e) {
            // No matches in database, use default (start) ELO-rating
            return EloRating.START_ELO;
        }
    }

    @Override
    public List<TimeAndElo> getEloOverTimeList(final Player player) {
        final EntityManager em = this.factory.createEntityManager();
        final TypedQuery<TimeAndElo> q = em.createQuery(
                "SELECT NEW no.uib.inf319.bordtennis.model."
                + "TimeAndElo(m.time, r.elo) "
                + "FROM Result r, Match m "
                + "WHERE r.player = :player "
                        + "AND r.match = m "
                        + "AND m.approved = 0 "
                + "ORDER BY m.time ASC",
                TimeAndElo.class);
        q.setParameter("player", player);
        List<TimeAndElo> res = q.getResultList();
        return res;
    }

    @Override
    public int getPreviousElo(final Player player, final Timestamp time) {
        try {
            EntityManager em = this.factory.createEntityManager();
            TypedQuery<Object[]> q = em.createQuery(
                    "SELECT r.elo, m.time "
                    + "FROM Result r, Match m "
                    + "WHERE r.player = :player "
                            + "AND r.match = m "
                            + "AND m.approved = 0 "
                            + "AND m.time < :time "
                    + "ORDER BY m.time DESC", Object[].class);
            q.setParameter("player", player);
            q.setParameter("time", time);
            q.setMaxResults(1);
            Object[] res = q.getSingleResult();
            return (Integer) res[0];
        } catch (NoResultException e) {
            // No previous Elo-rating, use default (start) Elo
            return EloRating.START_ELO;
        }
    }

    @Override
    public List<Player> getAllPlayersExceptForOne(final Player player) {
        EntityManager em = factory.createEntityManager();
        TypedQuery<Player> q = em.createQuery(
                "SELECT p "
                + "FROM Player p "
                + "WHERE p <> :player "
                + "ORDER BY p.name", Player.class);
        q.setParameter("player", player);
        List<Player> players = q.getResultList();
        return players;
    }
}
