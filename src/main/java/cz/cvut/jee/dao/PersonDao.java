package cz.cvut.jee.dao;

import cz.cvut.jee.entity.Person;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

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

    @SuppressWarnings("JpaQlInspection")
    public List<Person> findAllFromRegion(long regionId) {
        TypedQuery<Person> query = em.createQuery("select p from Person p where region_id = :regionId", Person.class);
        query.setParameter("regionId", regionId);

        return query.getResultList();
    }

    @SuppressWarnings("JpaQlInspection")
    public void updatePassword(long personId, String password) {
        Query query = em.createQuery("update Person set password = :password where id = :id");
        query.setParameter("id", personId);
        query.setParameter("password", password);
        query.executeUpdate();
    }

}
