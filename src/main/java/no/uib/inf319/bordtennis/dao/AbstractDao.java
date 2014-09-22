package no.uib.inf319.bordtennis.dao;

import java.util.List;

/**
 * Interface containing basic methods for retrieving and editing an entity from
 * the database.
 *
 * @author Kjetil
 * @param <T> the entity class
 */
public interface AbstractDao<T> {
    /**
     * Get one entity of type <code>T</code> with the id <code>id</code> from
     * the database.
     *
     * @param id the id of the entity to retrieve.
     * @return the entity with id <code>id</code>.
     */
    T find(Object id);

    /**
     * Get all entities of type <code>T</code> from the databse.
     *
     * @return a list of entities.
     */
    List<T> findAll();

    /**
     * Persist an entity in the database.
     *
     * @param entity the entity to persist.
     */
    void create(T entity);

    /**
     * Edit an entity in the database.
     *
     * @param entity the entity to be edited with the new data.
     */
    void edit(T entity);

    /**
     * Remove an entity from the database.
     *
     * @param entity the entity to be removed.
     */
    void remove(T entity);
}
