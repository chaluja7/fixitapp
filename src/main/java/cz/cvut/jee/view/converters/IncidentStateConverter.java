package cz.cvut.jee.view.converters;

import cz.cvut.jee.entity.IncidentState;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

/**
 * Converter for incident state.
 *
 * @author jakubchalupa
 * @since 27.11.14
 */
@Named
public class IncidentStateConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return IncidentState.valueOf(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        IncidentState incidentState = (IncidentState) o;

        return incidentState.name();
    }

}
