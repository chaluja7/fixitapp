package cz.cvut.jee.dao;

import cz.cvut.jee.entity.Person;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
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

    /**
     * @param username persons username
     * @return person with given username or null
     */
    @SuppressWarnings("JpaQlInspection")
    public Person findPersonByUsername(String username) {
        List<Person> personList = em.createNamedQuery("Person.findByUsername", Person.class)
                .setParameter("username", username).getResultList();

        if(personList.size() == 1) {
            return personList.get(0);
        }

        return null;
    }

    /**
     * @param regionId id of region
     * @return all persons from given region
     */
    @SuppressWarnings("JpaQlInspection")
    public List<Person> findAllFromRegion(long regionId) {
        return em.createNamedQuery("Person.findAllFromRegion", Person.class).setParameter("regionId", regionId).getResultList();
    }

    /**
     * will update password
     * @param personId id of person
     * @param password new password (already hashed)
     */
    @SuppressWarnings("JpaQlInspection")
    public void updatePassword(long personId, String password) {
        em.createNamedQuery("Person.updatePassword").setParameter("password", password).setParameter("id", personId).executeUpdate();
    }

}
