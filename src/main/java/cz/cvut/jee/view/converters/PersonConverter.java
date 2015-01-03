package cz.cvut.jee.view.converters;

import cz.cvut.jee.entity.Person;
import cz.cvut.jee.service.PersonService;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Converter for person.
 *
 * @author jakubchalupa
 * @since 02.01.15
 */
@Named
@RequestScoped
public class PersonConverter implements Converter {

    @Inject
    private PersonService personService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return personService.findPerson(Long.valueOf(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        Person person = (Person) o;
        return String.valueOf(person.getId());
    }

}
