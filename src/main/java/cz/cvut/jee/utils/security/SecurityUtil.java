package cz.cvut.jee.utils.security;

import cz.cvut.jee.dao.PersonDao;
import cz.cvut.jee.entity.Person;

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

    @Resource
    private SessionContext context;

    @Inject
    private PersonDao personDao;

    public Person getCurrentUser() {
        return personDao.findPersonByUsername(context.getCallerPrincipal().getName());
    }

}
