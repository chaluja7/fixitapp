package cz.cvut.jee.service;

import cz.cvut.jee.entity.Person;

import javax.ejb.Local;
import java.util.List;

/**
 * Service for person.
 *
 * @author jakubchalupa
 * @since 28.12.14
 */
@Local
public interface PersonService {

    /**
     * @param id person id
     * @return person with given id
     */
    public Person findPerson(long id);

    /**
     * @param person person to update
     * @return updated person
     */
    public Person updatePerson(Person person);

    /**
     * @param person person to persist
     */
    public void createPerson(Person person);

    /**
     * @param id id of person to delete
     */
    public void deletePerson(long id) throws IllegalAccessException;

    /**
     * @return all persons
     */
    public List<Person> findAll();

    /**
     * @param regionId id of region
     * @return all persons from given region
     */
    public List<Person> findAllFromRegion(long regionId);

    /**
     * @return all persons available to currently logged user
     */
    public List<Person> findAllForCurrentUser();

    /**
     * @param id person id
     * @return person with given id with access control
     */
    public Person findPersonWithAccessControl(long id);

    /**
     * @param personId id of person
     * @param password new password
     */
    public void changePassword(long personId, String password) throws IllegalAccessException;

    /**
     * @param username username of person
     * @return person with given username if exists, null otherwise
     */
    public Person findPersonByUsername(String username);
}
