package cz.cvut.jee.utils.security;

import cz.cvut.jee.entity.PersonRole;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Classifier to perform rest security.
 *
 * @author jakubchalupa
 * @since 02.01.15
 */
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RestSecured {
    @Nonbinding PersonRole[] value() default {};
}
