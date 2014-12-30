package cz.cvut.jee.view;

import cz.cvut.jee.entity.Region;
import cz.cvut.jee.service.RegionService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author jakubchalupa
 * @since 29.12.14
 */
@Named
@ViewScoped
public class RegionBB implements Serializable {

    @Inject
    protected RegionService regionService;

    private Long id;

    private Region region = new Region();

    public void loadRegion() {
        if(id != null) {
            region = regionService.findRegion(id);
        }
    }

    public String saveRegion() {
        regionService.createRegion(region);
        return "region?faces-redirect=true";
    }

    public String updateRegion() {
        regionService.updateRegion(region);
        return "region?faces-redirect=true";
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
}
