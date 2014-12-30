package cz.cvut.jee.service;

import cz.cvut.jee.dao.PersonDao;
import cz.cvut.jee.entity.Person;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;

/**
 * Person service implementation.
 *
 * @author jakubchalupa
 * @since 28.12.14
 */
@Singleton
public class PersonServiceImpl implements PersonService {

    @Inject
    protected PersonDao personDao;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Person findPerson(long id) {
        return personDao.find(id);
    }

    @Override
    public Person updatePerson(Person person) {
        return personDao.update(person);
    }

    @Override
    public void createPerson(Person person) {
        //TODO password
        personDao.create(person);
    }

    @Override
    public void deletePerson(long id) {
        personDao.delete(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Person> findAll() {
        return null;
    }
}
