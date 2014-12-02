package no.uib.inf319.bordtennis.dao.context;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import no.uib.inf319.bordtennis.dao.MatchDao;
import no.uib.inf319.bordtennis.model.Match;
import no.uib.inf319.bordtennis.model.MatchWithPlayerNames;
import no.uib.inf319.bordtennis.model.PendingMatch;
import no.uib.inf319.bordtennis.model.Player;

/**
 * An implementation of the {@link MatchDao}-interface using JPA.
 *
 * @author Kjetil
 */
public final class MatchDaoJpa extends AbstractDaoJpa<Match> implements
        MatchDao {

    /**
     * EntityManagerFactory.
     */
    private final EntityManagerFactory factory;

    /**
     * Creates a MatchDaoJpa instance.
     */
    public MatchDaoJpa() {
        super(Match.class);
        factory = Persistence.createEntityManagerFactory("BordtennisELO");
    }

    @Override
    protected EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    @Override
    public List<Match> getMatchesAfter(final Timestamp time) {
        EntityManager em = factory.createEntityManager();
        TypedQuery<Match> q = em.createQuery(
                "SELECT m "
                + "FROM Match m "
                + "WHERE m.time >= :time "
                + "ORDER BY m.time ASC", Match.class);
        q.setParameter("time", time);
        List<Match> matches = q.getResultList();
        em.close();
        return matches;
    }

    @Override
    public List<PendingMatch> getPendingMatches(final Player player) {
        EntityManager em = factory.createEntityManager();
        TypedQuery<PendingMatch> q = em.createQuery(
                "SELECT NEW no.uib.inf319.bordtennis.model.PendingMatch("
                        + "r1.player.name, r2.player.name, m.time, "
                        + "r1.playernumber, m.victor, m.score, r1.resultid) "
                + "FROM Match m JOIN m.results r1 JOIN m.results r2 "
                + "WHERE r1.player = :player "
                        + "AND r1 <> r2 "
                        + "AND m.approved = r1.playernumber "
                + "ORDER BY m.time ASC", PendingMatch.class);
        q.setParameter("player", player);
        List<PendingMatch> matches = q.getResultList();
        em.close();
        return matches;
    }

    @Override
    public List<MatchWithPlayerNames> getAllMatchesWithPlayerNames() {
        EntityManager em = factory.createEntityManager();
        TypedQuery<MatchWithPlayerNames> q = em.createQuery(
                "SELECT NEW no.uib.inf319.bordtennis.model."
                        + "MatchWithPlayerNames(m, p1.name, p2.name) "
                + "FROM Match m JOIN m.results r1 JOIN m.results r2 "
                        + "JOIN r1.player p1 JOIN r2.player p2 "
                + "WHERE r1 <> r2 "
                        + "AND r1.playernumber = 1 "
                        + "AND r2.playernumber = 2 "
                + "ORDER BY m.matchid ASC",
                MatchWithPlayerNames.class);
        List<MatchWithPlayerNames> matches = q.getResultList();
        em.close();
        return matches;
    }

    @Override
    public List<Match> getAllPlayerMatches(final Player player) {
        EntityManager em = factory.createEntityManager();
        TypedQuery<Match> q = em.createQuery(
                "SELECT m "
                + "FROM Match m JOIN m.results r "
                + "WHERE r.player = :player "
                + "ORDER BY m.time ASC", Match.class);
        q.setParameter("player", player);
        List<Match> matches = q.getResultList();
        em.close();
        return matches;
    }

    @Override
    public MatchWithPlayerNames getMatchWithPlayerUsernames(
            final Integer matchid) {
        EntityManager em = factory.createEntityManager();
        try {
            TypedQuery<MatchWithPlayerNames> q = em.createQuery(
                    "SELECT NEW no.uib.inf319.bordtennis.model."
                        + "MatchWithPlayerNames(m, p1.username, p2.username) "
                    + "FROM Match m JOIN m.results r1 JOIN m.results r2 "
                            + "JOIN r1.player p1 JOIN r2.player p2 "
                    + "WHERE m.matchid = :matchid "
                            + "AND r1 <> r2 "
                            + "AND r1.playernumber = 1 "
                            + "AND r2.playernumber = 2",
                    MatchWithPlayerNames.class);
            q.setParameter("matchid", matchid);
            MatchWithPlayerNames match = q.getSingleResult();
            return match;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
