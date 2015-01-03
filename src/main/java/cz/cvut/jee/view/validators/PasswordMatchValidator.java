package cz.cvut.jee.view.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import java.util.ResourceBundle;

/**
 * Password equals validator for new password.
 *
 * @author jakubchalupa
 * @since 02.01.15
 */
@Named
public class PasswordMatchValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String password = value.toString();

        UIInput uiInputConfirmPassword = (UIInput) component.getAttributes().get("confirmPassword");
        String confirmPassword = uiInputConfirmPassword.getSubmittedValue().toString();

        //this solves required=true
        if (password == null || password.isEmpty() || confirmPassword == null || confirmPassword.isEmpty()) {
            return;
        }

        if (!password.equals(confirmPassword)) {
            uiInputConfirmPassword.setValid(false);
            throw new ValidatorException(new FacesMessage(ResourceBundle.getBundle("messages").getString("user.passwords.matchError")));
        }
    }
}
