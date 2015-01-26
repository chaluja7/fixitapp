package cz.cvut.jee.entity.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Custom username validator.
 * This is only demonstration. This constraint can be easily replaced with standard email constraint from javax.validation.
 *
 * @author jakubchalupa
 * @since 26.01.15
 */
public class CheckUsernameValidator implements ConstraintValidator<CheckUsername, String > {

    private static final String USERNAME_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private boolean checkUsername;

    @Override
    public void initialize(CheckUsername checkUsername) {
        this.checkUsername = checkUsername.value();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && (!checkUsername || Pattern.compile(USERNAME_PATTERN).matcher(s).matches());
    }
}
