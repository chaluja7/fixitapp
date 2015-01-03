package cz.cvut.jee.view.validators;

import cz.cvut.jee.utils.security.PasswordUtil;
import cz.cvut.jee.utils.security.SecurityUtil;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ResourceBundle;

/**
 * Password validator for match with currently logged user - this app does not need more.
 *
 * @author jakubchalupa
 * @since 02.01.15
 */
@Named
public class OldPasswordEqualsValidator implements Validator {

    @Inject
    private SecurityUtil securityUtil;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String hashedPassword = PasswordUtil.generateHash(value.toString());

        if (!hashedPassword.equals(securityUtil.getCurrentUser().getPassword())) {
            throw new ValidatorException(new FacesMessage(ResourceBundle.getBundle("messages").getString("user.password.wrongOld")));
        }
    }
}
