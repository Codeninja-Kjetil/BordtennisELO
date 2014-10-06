package no.uib.inf319.bordtennis.dao.context;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import no.uib.inf319.bordtennis.dao.AbstractDao;

/**
 * An abstract implementation of the {@link AbstractDao}-interface using JPA.
 *
 * @author Kjetil
 * @param <T> the entity class
 */
public abstract class AbstractDaoJpa<T> implements AbstractDao<T> {

    /**
     * The entity class.
     */
    private final Class<T> entityClass;

    /**
     * Constructor setting the entityClass field.
     *
     * @param entityClass the entity class.
     */
    public AbstractDaoJpa(final Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Gets an EntityManager from the current persistence unit.
     *
     * @return an EntityManager.
     */
    protected abstract EntityManager getEntityManager();

    @Override
    public final T find(final Object id) {
        return getEntityManager().find(this.entityClass, id);
    }

    @Override
    public final List<T> findAll() {
        final EntityManager em = getEntityManager();
        final CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(
                this.entityClass);
        cq.select(cq.from(this.entityClass));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public final void create(final T entity) {
        final EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    @Override
    public final void edit(final T entity) {
        final EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    @Override
    public final void remove(final T entity) {
        final EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(entity));
        em.getTransaction().commit();
    }

}