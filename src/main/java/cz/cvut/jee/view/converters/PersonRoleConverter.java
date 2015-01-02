package cz.cvut.jee.view.converters;

import cz.cvut.jee.entity.PersonRole;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

/**
 * Converter for person role.
 *
 * @author jakubchalupa
 * @since 27.11.14
 */
@Named
public class PersonRoleConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return PersonRole.valueOf(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        PersonRole personRole = (PersonRole) o;

        return personRole.name();
    }

}
