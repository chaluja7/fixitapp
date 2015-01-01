package cz.cvut.jee.dao;

import cz.cvut.jee.entity.Person;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.persistence.TypedQuery;

/**
 * Person DAO.
 *
 * @author jakubchalupa
 * @since 28.12.14
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PersonDao extends AbstractGenericDao<Person> {

    protected PersonDao() {
        super(Person.class);
    }

    @SuppressWarnings("JpaQlInspection")
    public Person findPersonByUsername(String username) {
        TypedQuery<Person> query = em.createQuery("select p from Person p where username = :username", Person.class);
        query.setParameter("username", username);

        if(query.getResultList().size() == 1) {
            return query.getResultList().get(0);
        }

        return null;
    }
}
