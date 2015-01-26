package cz.cvut.jee.service;

import cz.cvut.jee.dao.PersonDao;
import cz.cvut.jee.entity.Comment;
import cz.cvut.jee.entity.Message;
import cz.cvut.jee.entity.Person;
import cz.cvut.jee.entity.enums.PersonRole;
import cz.cvut.jee.utils.security.PasswordUtil;
import cz.cvut.jee.utils.security.SecurityUtil;

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
    @RolesAllowed({"SUPER_ADMIN", "REGION_ADMIN"})
    public void deletePerson(long id) throws IllegalAccessException {
        Person userToDelete = personDao.find(id);
        assert(userToDelete != null);

        Person currentUser = securityUtil.getCurrentUser();
        if(currentUser.getRole().equals(PersonRole.REGION_ADMIN) && !currentUser.getRegion().equals(userToDelete.getRegion())) {
            throw new IllegalAccessException();
        }

        if(currentUser.equals(userToDelete)) {
            throw new RuntimeException("You can not delete yourself");
        }

        for(Message message : userToDelete.getMessages()) {
            message.setAuthor(null);
        }

        for(Comment comment : userToDelete.getComments()) {
            comment.setAuthor(null);
        }

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

    @Override
    @RolesAllowed({"SUPER_ADMIN", "REGION_ADMIN", "OFFICER"})
    public void changePassword(long personId, String password) throws IllegalAccessException {
        Person currentUser = securityUtil.getCurrentUser();
        Person userToChange = personDao.find(personId);
        assert(userToChange != null);

        if(currentUser.getRole().equals(PersonRole.SUPER_ADMIN) ||
            (currentUser.getRole().equals(PersonRole.REGION_ADMIN) && currentUser.getRegion().equals(userToChange.getRegion())) ||
            (currentUser.getRole().equals(PersonRole.OFFICER) && currentUser.equals(userToChange))) {

            personDao.updatePassword(personId, PasswordUtil.generateHash(password));
            return;
        }

        throw new IllegalAccessException();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @PermitAll
    public Person findPersonByUsername(String username) {
        return personDao.findPersonByUsername(username);
    }
}
