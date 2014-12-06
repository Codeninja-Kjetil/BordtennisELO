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
import no.uib.inf319.bordtennis.model.RankingListPlayer;
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
        List<Player> players = findAll();
        Collections.sort(players, new PlayerEloComparator());
        Collections.reverse(players);
        return players;
    }

    @Override
    public int getLatestElo(final Player player) {
        EntityManager em = factory.createEntityManager();
        try {
            TypedQuery<Integer> q = em.createQuery(
                    "SELECT r.elo "
                    + "FROM Result r JOIN r.match m "
                    + "WHERE r.player = :player "
                        + "AND m.approved = 0 "
                        + "AND m.time = "
                            + "(SELECT MAX(m2.time) "
                            + "FROM Result r2 JOIN r2.match m2 "
                            + "WHERE r2.player = :player "
                                + "AND m2.approved = 0)",
                    Integer.class);
            q.setParameter("player", player);
            Integer elo = q.getSingleResult();
            return elo;
        } catch (NoResultException e) {
            // No matches in database, use default (start) ELO-rating
            return EloRating.START_ELO;
        } finally {
            em.close();
        }
    }

    @Override
    public List<TimeAndElo> getEloOverTimeList(final Player player) {
        final EntityManager em = factory.createEntityManager();
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
        em.close();
        return res;
    }

    @Override
    public int getPreviousElo(final Player player, final Timestamp time) {
        EntityManager em = factory.createEntityManager();
        try {
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
        } finally {
            em.close();
        }
    }

    @Override
    public List<Player> getNonLockedPlayersExceptForOne(final Player player) {
        EntityManager em = factory.createEntityManager();
        TypedQuery<Player> q = em.createQuery(
                "SELECT p "
                + "FROM Player p "
                + "WHERE p <> :player "
                    + "AND p.locked = FALSE "
                + "ORDER BY p.name", Player.class);
        q.setParameter("player", player);
        List<Player> players = q.getResultList();
        em.close();
        return players;
    }

    @Override
    public List<RankingListPlayer> getActiveRankingListPlayers(
            final Timestamp time) {
        EntityManager em = factory.createEntityManager();
        TypedQuery<RankingListPlayer> q = em.createQuery(
                "SELECT NEW no.uib.inf319.bordtennis.model.RankingListPlayer("
                    + "p, r.elo, m.time, "
                        + "(SELECT COUNT(m3) "
                        + "FROM Result r3 JOIN r3.match m3 JOIN r3.player p3 "
                        + "WHERE p3 = p AND m3.approved = 0)) "
                + "FROM Result r JOIN r.match m JOIN r.player p "
                + "WHERE p.locked = FALSE "
                    + "AND m.approved = 0 "
                    + "AND m.time >= :time "
                    + "AND m.time = "
                        + "(SELECT MAX(m2.time) "
                        + "FROM Result r2 JOIN r2.match m2 "
                        + "WHERE r2.player = p "
                            + "AND m2.approved = 0) "
                + "ORDER BY r.elo DESC",
                RankingListPlayer.class);
        q.setParameter("time", time);
        List<RankingListPlayer> res = q.getResultList();
        return res;
    }

    @Override
    public List<RankingListPlayer> getInactiveRankingListPlayers(
            final Timestamp time) {
        EntityManager em = factory.createEntityManager();
        TypedQuery<RankingListPlayer> q = em.createQuery(
                "SELECT NEW no.uib.inf319.bordtennis.model.RankingListPlayer("
                    + "p, r.elo, m.time, "
                        + "(SELECT COUNT(m3) "
                        + "FROM Result r3 JOIN r3.match m3 JOIN r3.player p3 "
                        + "WHERE p3 = p AND m3.approved = 0)) "
                + "FROM Result r JOIN r.match m JOIN r.player p "
                + "WHERE p.locked = FALSE "
                    + "AND m.approved = 0 "
                    + "AND m.time < :time "
                    + "AND m.time = "
                        + "(SELECT MAX(m2.time) "
                        + "FROM Result r2 JOIN r2.match m2 "
                        + "WHERE r2.player = p "
                            + "AND m2.approved = 0) "
                + "ORDER BY r.elo DESC",
                RankingListPlayer.class);
        q.setParameter("time", time);
        List<RankingListPlayer> res = q.getResultList();
        return res;
    }

    @Override
    public List<Player> getNewPlayers() {
        EntityManager em = factory.createEntityManager();
        TypedQuery<Player> q = em.createQuery(
                "SELECT p "
                + "FROM Player p "
                + "WHERE p.locked = FALSE "
                    + "AND p NOT IN "
                        + "(SELECT p2 "
                        + "FROM Result r2 JOIN r2.player p2 JOIN r2.match m2 "
                        + "WHERE m2.approved = 0) "
                + "ORDER BY p.name ASC", Player.class);
        List<Player> players = q.getResultList();
        return players;
    }

    @Override
    public List<Player> getAdmins() {
        EntityManager em = factory.createEntityManager();
        TypedQuery<Player> q = em.createQuery(
                "SELECT p "
                + "FROM Player p "
                + "WHERE p.admin = TRUE "
                    + "AND p.locked = FALSE "
                + "ORDER BY p.name ASC", Player.class);
        List<Player> players = q.getResultList();
        return players;
    }
}
