package cz.cvut.jee.utils.security;

import cz.cvut.jee.entity.PersonRole;
import cz.cvut.jee.rest.exceptions.RestNotAuthorizedException;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Compu global hyper mega net super cool security advice interceptor.
 *
 * @author jakubchalupa
 * @since 02.01.15
 */
@Interceptor
@RestSecured
public class SecurityAdvice implements Serializable {

    @Inject
    private SecurityUtil securityUtil;

    @AroundInvoke
    public Object checkSecurity(InvocationContext joinPoint) throws Exception {
        if(!securityUtil.isLoggedIn()){
            throw new RestNotAuthorizedException();
        }

        PersonRole[] declaredRoles = getExpectedRoles(joinPoint.getMethod());
        if(declaredRoles.length == 0 || securityUtil.hasOneRole(declaredRoles)) {
            return joinPoint.proceed();
        }

        throw new RestNotAuthorizedException();
    }

    private PersonRole[] getExpectedRoles(Method method) {
        RestSecured secured = method.getAnnotation(RestSecured.class);
        if (secured == null) {
            secured = method.getDeclaringClass().getAnnotation(RestSecured.class);
            if (secured == null) {
                throw new RuntimeException("@RestSecured not found on method " + method.getName() + " or its declaring class " + method.getClass().getName());
            }
        }

        return secured.value();
    }
}

