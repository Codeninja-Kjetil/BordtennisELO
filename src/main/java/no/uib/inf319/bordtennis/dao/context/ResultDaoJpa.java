package no.uib.inf319.bordtennis.dao.context;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import no.uib.inf319.bordtennis.dao.ResultDao;
import no.uib.inf319.bordtennis.model.Match;
import no.uib.inf319.bordtennis.model.Result;

/**
 * An implementation of the {@link ResultDao}-interface using JPA.
 *
 * @author Kjetil
 */
public final class ResultDaoJpa extends AbstractDaoJpa<Result> implements
        ResultDao {

    /**
     * EntityManagerFactory.
     */
    private EntityManagerFactory factory;

    /**
     * Creates a ResultDaoJpa instance.
     */
    public ResultDaoJpa() {
        super(Result.class);
        this.factory = Persistence.createEntityManagerFactory("BordtennisELO");
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.factory.createEntityManager();
    }

    @Override
    public List<Result> getResultsFromMatch(final Match match) {
        EntityManager em = factory.createEntityManager();
        TypedQuery<Result> q = em.createQuery(
                "SELECT r "
                + "FROM Result r "
                + "WHERE r.match = :match "
                + "ORDER BY r.playernumber", Result.class);
        q.setParameter("match", match);
        List<Result> results = q.getResultList();
        em.close();
        return results;
    }
}
