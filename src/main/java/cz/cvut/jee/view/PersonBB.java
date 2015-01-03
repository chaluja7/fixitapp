package cz.cvut.jee.view;

import cz.cvut.jee.entity.Person;
import cz.cvut.jee.entity.PersonRole;
import cz.cvut.jee.entity.Region;
import cz.cvut.jee.service.PersonService;
import cz.cvut.jee.service.RegionService;
import cz.cvut.jee.utils.security.SecurityUtil;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Backing bean for person.
 *
 * @author jakubchalupa
 * @since 29.12.14
 */
@Named
@ViewScoped
public class PersonBB implements Serializable {

    @Inject
    protected PersonService personService;

    @Inject
    protected RegionService regionService;

    @Inject
    private SecurityUtil securityUtil;

    private Long id;

    private Person person = new Person();

    public void loadPerson() {
        if(id != null) {
            person = personService.findPersonWithAccessControl(id);
            if(person == null) {
                throw new RuntimeException("Invalid id");
            }
        }
    }

    public String savePerson() {
        if(person.getRole() == null) {
            person.setRole(PersonRole.OFFICER);
        }
        personService.createPerson(person);

        if(person.getRole().equals(PersonRole.REGION_ADMIN)) {
            regionService.updateRegionAdmin(person.getRegion().getId(), person.getId());
        }

        return "user-list?dataSaved=true&faces-redirect=true";
    }

    public String updatePerson() {
        personService.updatePerson(person);

        if(securityUtil.getCurrentUser().getRole().equals(PersonRole.OFFICER)) {
            return "user-edit?id=" + id + "&dataSaved=true&faces-redirect=true";
        }
        return "user-list?dataSaved=true&faces-redirect=true";
    }

    public String changePassword() throws IllegalAccessException {
        personService.changePassword(person.getId(), person.getPassword());

        if(securityUtil.getCurrentUser().getId().equals(person.getId())) {
            return "/login?changedPassword=true&faces-redirect=true";
        }

        return "user-list?dataSaved=true&faces-redirect=true";
    }

    public String deletePerson() throws IllegalAccessException {
        personService.deletePerson(person.getId());
        return "user-list?dataSaved=true&faces-redirect=true";
    }

    public Map<String, Object> getAllRegions() {
        Map<String, Object> map = new HashMap<>();

        for(Region region : regionService.findAllForCurrentUser()) {
            map.put(region.getName(), region);
        }

        return map;
    }

    public Map<String, Object> getAllRoles() {
        Map<String, Object> map = new HashMap<>();
        for(PersonRole personRole : PersonRole.values()) {
            if(!personRole.equals(PersonRole.SUPER_ADMIN)) {
                map.put(personRole.name(), personRole);
            }
        }

        return map;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
