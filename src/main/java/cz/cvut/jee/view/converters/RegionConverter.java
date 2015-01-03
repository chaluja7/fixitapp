package cz.cvut.jee.view.converters;

import cz.cvut.jee.entity.Region;
import cz.cvut.jee.service.RegionService;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Converter for region.
 *
 * @author jakubchalupa
 * @since 02.01.15
 */
@Named
@RequestScoped
public class RegionConverter implements Converter {

    @Inject
    private RegionService regionService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        try {
            return regionService.findRegion(Long.valueOf(s));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        Region region = (Region) o;

        return region == null ? null : String.valueOf(region.getId());
    }

}
