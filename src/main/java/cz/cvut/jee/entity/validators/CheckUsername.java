package cz.cvut.jee.entity.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Username custom constraint definition.
 *
 * @author jakubchalupa
 * @since 26.01.15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy = CheckUsernameValidator.class)
public @interface CheckUsername {

    String message() default "{cz.cvut.jee.validators.CheckUsername.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean value() default true;

}
