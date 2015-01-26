package cz.cvut.jee.view.validators;

import cz.cvut.jee.service.PersonService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ResourceBundle;

/**
 * Validator to ensure unique username in application.
 *
 * @author jakubchalupa
 * @since 26.01.15
 */
@Named
@RequestScoped
public class UniqueUsernameValidator implements Validator {

    @Inject
    private PersonService personService;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(personService.findPersonByUsername(value.toString()) != null) {
            throw new ValidatorException(new FacesMessage(ResourceBundle.getBundle("messages").getString("user.username.unique")));
        }
    }
}
