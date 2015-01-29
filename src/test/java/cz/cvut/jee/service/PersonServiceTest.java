package cz.cvut.jee.service;

import cz.cvut.jee.entity.Person;
import cz.cvut.jee.entity.Region;
import cz.cvut.jee.entity.enums.PersonRole;
import cz.cvut.jee.utils.security.PasswordUtil;
import cz.cvut.jee.utils.security.SecurityUtil;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Incident service test class.
 *
 * @author jakubchalupa
 * @since 28.01.15
 */
public class PersonServiceTest extends AbstractArquillianTest {
    
    @Inject 
    private PersonService personService;
    
    @Inject 
    private SecurityUtil securityUtil;

    private Person preparePerson() {
        Person person = new Person();
        
        person.setName("Pepa");
        person.setSurname("Prvni");
        person.setUsername("poteskoste@eos.cz");
        person.setPassword("a");
        person.setRole(PersonRole.SUPER_ADMIN);
        
        return person;
    }
   


    @Test(groups = "REGION_ADMIN")
    public void testCreateFindFindByUsername() {
        Person person = preparePerson();
        personService.createPerson(person);
        
        em.clear();
        
        Person retrieved1 = personService.findPerson(person.getId());
        assertNotNull(retrieved1);
        assertEquals(person, retrieved1);
        assertEquals(person.getName(), retrieved1.getName());
        assertEquals(person.getSurname(), retrieved1.getSurname());
        assertEquals(person.getUsername(), retrieved1.getUsername());
        assertEquals(person.getPassword(), retrieved1.getPassword());
        assertEquals(person.getRole(), retrieved1.getRole());

        em.clear();
        
        Person retrieved2 = personService.findPersonByUsername(person.getUsername());
        assertNotNull(retrieved2);
        assertEquals(person, retrieved2);
        assertEquals(person.getName(), retrieved2.getName());
        assertEquals(person.getSurname(), retrieved2.getSurname());
        assertEquals(person.getUsername(), retrieved2.getUsername());
        assertEquals(person.getPassword(), retrieved2.getPassword());
        assertEquals(person.getRole(), retrieved2.getRole());
    }

    @Test(groups = "SUPER_ADMIN")
    public void testDeletePersonByUsernameSuperAdmin() throws IllegalAccessException {
        Person person = preparePerson();
        personService.createPerson(person);

        em.clear();
        
        personService.deletePerson(person.getId());

        Person retrieved = personService.findPerson(person.getId());
        assertNull(retrieved);
    }

    @Test(groups = "REGION_ADMIN")
    public void testDeletePersonByUsernameRegionAdminSuccess() throws IllegalAccessException {
        Region region = new Region();
        region.setId((long) 1);
        Person person = preparePerson();
        person.setRegion(region);
        
        personService.createPerson(person);

        em.clear();
        
        personService.deletePerson(person.getId());

        Person retrieved = personService.findPerson(person.getId());
        assertNull(retrieved);
    }

    @Test(groups = "REGION_ADMIN", expectedExceptions = IllegalAccessException.class)
    public void testDeletePersonByUsernameRegionAdminFail() throws IllegalAccessException {
        Region region = new Region();
        region.setId((long) 5);
        Person person = preparePerson();
        person.setRegion(region);

        personService.createPerson(person);

        em.clear();

        personService.deletePerson(person.getId());
    }

    @Test(groups = "SUPER_ADMIN", expectedExceptions = RuntimeException.class)
    public void testDeletePersonByUsernameSelfDelete() throws IllegalAccessException {
        personService.deletePerson(securityUtil.getCurrentUser().getId());
    }

    @Test(groups = "SUPER_ADMIN")
    public void testFindAll() {
        List<Person> people = personService.findAll();

        assertNotNull(people);
        assertEquals(people.get(0).getUsername(), "spravce@eos.cz");
        assertEquals(people.get(1).getUsername(), "region@eos.cz");
        assertEquals(people.get(2).getUsername(), "officer@eos.cz");
        
    }

    @Test(groups = "REGION_ADMIN")
    public void testFindAllFromRegion(){
        List<Person> peopleFromRegion = personService.findAllFromRegion(1);
        
        assertNotNull(peopleFromRegion);
        assertEquals(peopleFromRegion.size(), 2);
        assertEquals(peopleFromRegion.get(0).getUsername(), "region@eos.cz");
        assertEquals(peopleFromRegion.get(1).getUsername(), "officer@eos.cz");
    }

    @Test(groups = "SUPER_ADMIN")
    public void testChangePassword() throws IllegalAccessException {
        Person person = preparePerson();
        personService.createPerson(person);

        em.clear();
        
        personService.changePassword(person.getId(), "b");

        em.clear();

        Person retrieved = personService.findPerson(person.getId());
        assertNotNull(retrieved);
        assertEquals(retrieved.getPassword(), PasswordUtil.generateHash("b"));
    }

}
