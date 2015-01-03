package cz.cvut.jee.utils.security;

import cz.cvut.jee.dao.PersonDao;
import cz.cvut.jee.entity.Person;
import cz.cvut.jee.entity.enums.PersonRole;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Util to retrieve currently logged user.
 *
 * @author jakubchalupa
 * @since 01.01.15
 */
@Stateless
public class SecurityUtil {

    //this looks ugly :( isn`t there a better solution? User with username "anonymous" is not our friend...
    public static final String anonymousUser = "anonymous";

    @Resource
    private SessionContext context;

    @Inject
    private PersonDao personDao;

    public Person getCurrentUser() {
        if(isLoggedIn()) {
            return personDao.findPersonByUsername(context.getCallerPrincipal().getName());
        }

        return null;
    }

    public boolean isLoggedIn() {
        return !context.getCallerPrincipal().getName().equals(anonymousUser);
    }

    public boolean hasOneRole(PersonRole[] roles) {
        if(!isLoggedIn() || roles.length == 0) {
            return false;
        }

        Person currentUser = getCurrentUser();
        for(PersonRole role : roles) {
            if(currentUser.getRole().equals(role)) {
                return true;
            }
        }

        return false;
    }

}
