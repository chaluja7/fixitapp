package cz.cvut.jee.service;

import cz.cvut.jee.dao.PersonDao;
import cz.cvut.jee.entity.Person;
import cz.cvut.jee.entity.PersonRole;
import cz.cvut.jee.utils.security.PasswordUtil;
import cz.cvut.jee.utils.security.SecurityUtil;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Person service implementation.
 *
 * @author jakubchalupa
 * @since 28.12.14
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PersonServiceImpl implements PersonService {

    @Inject
    protected PersonDao personDao;

    @Inject
    private SecurityUtil securityUtil;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @PermitAll
    public Person findPerson(long id) {
        return personDao.find(id);
    }

    @Override
    @RolesAllowed({"SUPER_ADMIN", "REGION_ADMIN", "OFFICER"})
    public Person updatePerson(Person person) {
        return personDao.update(person);
    }

    @Override
    @RolesAllowed({"SUPER_ADMIN", "REGION_ADMIN"})
    public void createPerson(Person person) {
        person.setPassword(PasswordUtil.generateHash(person.getPassword()));
        personDao.create(person);
    }

    @Override
    @DenyAll
    public void deletePerson(long id) {
        personDao.delete(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed("SUPER_ADMIN")
    public List<Person> findAll() {
        return personDao.findAll();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed({"SUPER_ADMIN", "REGION_ADMIN"})
    public List<Person> findAllFromRegion(long regionId) {
        return personDao.findAllFromRegion(regionId);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed({"SUPER_ADMIN", "REGION_ADMIN"})
    public List<Person> findAllForCurrentUser() {
        Person person = securityUtil.getCurrentUser();

        if(person.getRole().equals(PersonRole.SUPER_ADMIN)) {
            return personDao.findAll();
        } else if(person.getRole().equals(PersonRole.REGION_ADMIN)) {
            return personDao.findAllFromRegion(person.getRegion().getId());
        }

        return new ArrayList<>();
    }

    @Override
    @RolesAllowed({"SUPER_ADMIN", "REGION_ADMIN", "OFFICER"})
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Person findPersonWithAccessControl(long id) {
        Person currentUser = securityUtil.getCurrentUser();
        Person person = personDao.find(id);

        if(currentUser.getRole().equals(PersonRole.SUPER_ADMIN)) {
            return person;
        } else if(currentUser.getRole().equals(PersonRole.REGION_ADMIN) && currentUser.getRegion().equals(person.getRegion())) {
            return person;
        } else if(currentUser.getRole().equals(PersonRole.OFFICER) && currentUser.equals(person)) {
            return person;
        }

        return null;
    }
}
