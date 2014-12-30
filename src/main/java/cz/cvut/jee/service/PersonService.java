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
    public void deletePerson(long id);

    /**
     * @return all persons
     */
    public List<Person> findAll();
}
