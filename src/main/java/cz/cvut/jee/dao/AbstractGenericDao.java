package cz.cvut.jee.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Generic dao defining base CRUD operations.
 *
 * @author jakubchalupa
 * @since 28.12.14
 */
public abstract class AbstractGenericDao<T> {

    @PersistenceContext
    protected EntityManager em;

    protected final Class<T> type;

    protected AbstractGenericDao(Class<T> type) {
        this.type = type;
    }

    /**
     * @param t entity to persist
     */
    public void create(T t) {
        em.persist(t);
    }

    /**
     * @param t entity to update
     * @return merged entity
     */
    public T update(T t) {
        return em.merge(t);
    }

    /**
     * @param id id of entity
     * @return entity with given id by type or null
     */
    public T find(long id) {
        return em.find(type, id);
    }

    /**
     * will remove entity from database
     * @param id id of entity to remove
     */
    public void delete(long id) {
        em.remove(em.getReference(type, id));
    }

    /**
     * @return all entities by given type
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return em.createQuery( "from " + type.getName()).getResultList();
    }

}
