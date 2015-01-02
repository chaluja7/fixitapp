package cz.cvut.jee.utils.security;

import cz.cvut.jee.utils.security.exceptions.RestNotAuthorizedException;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * Compu global hyper mega net super cool security advice interceptor.
 *
 * @author jakubchalupa
 * @since 02.01.15
 */
@Interceptor
@RestSecureLogged
public class SecurityAdvice {

    @Inject
    private SecurityUtil securityUtil;

    @AroundInvoke
    public Object checkSecurity(InvocationContext joinPoint) throws Exception {
        if(!securityUtil.isLoggedIn()){
            throw new RestNotAuthorizedException();
        }

        return joinPoint.proceed();
    }
}
