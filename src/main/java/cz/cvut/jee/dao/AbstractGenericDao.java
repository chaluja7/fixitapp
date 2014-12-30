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

    public void create(T t) {
        em.persist(t);
    }

    public T update(T t) {
        return em.merge(t);
    }

    public T find(long id) {
        return em.find(type, id);
    }

    public void delete(long id) {
        em.remove(em.getReference(type, id));
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return em.createQuery( "from " + type.getName()).getResultList();
    }

}
