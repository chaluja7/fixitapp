package cz.cvut.jee.view;

import cz.cvut.jee.entity.Person;
import cz.cvut.jee.entity.Region;
import cz.cvut.jee.service.PersonService;
import cz.cvut.jee.service.RegionService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Backing bean for region.
 *
 * @author jakubchalupa
 * @since 29.12.14
 */
@Named
@ViewScoped
public class RegionBB implements Serializable {

    @Inject
    protected RegionService regionService;

    @Inject
    protected PersonService personService;

    private Long id;

    private Region region = new Region();

    private Person regionAdmin = new Person();

    public void loadRegion() {
        if(id != null) {
            region = regionService.findRegionWithAccessControl(id);
            if(region == null) {
                throw new RuntimeException("Invalid id");
            }

            regionAdmin = regionService.findRegionAdmin(id);
        }
    }

    public String saveRegion() {
        regionService.createRegion(region);
        return "region-list?dataSaved=true&faces-redirect=true";
    }

    public String updateRegion() {
        regionService.updateRegion(region);
        if(regionAdmin != null) {
            regionService.updateRegionAdmin(region.getId(), regionAdmin.getId());
        }

        return "region-list?dataSaved=true&faces-redirect=true";
    }

    public Map<String, Object> getAllPersonsFromRegion() {
        Map<String, Object> map = new HashMap<>();
        for(Person person : personService.findAllFromRegion(id)) {
            map.put(person.getUsername(), person);
        }

        return map;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Person getRegionAdmin() {
        return regionAdmin;
    }

    public void setRegionAdmin(Person regionAdmin) {
        this.regionAdmin = regionAdmin;
    }
}
