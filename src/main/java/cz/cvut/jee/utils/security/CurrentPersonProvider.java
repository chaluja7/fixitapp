package cz.cvut.jee.utils.security;

import cz.cvut.jee.dao.PersonDao;
import cz.cvut.jee.entity.Person;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author jakubchalupa
 * @since 02.01.15
 */
@RequestScoped
@Deprecated
public class CurrentPersonProvider {

    @Inject
    private PersonDao personDao;

    private Person currentPerson;

    @Named
    @LoggedIn
    @Produces
    public Person getCurrentPerson() {

        if (currentPerson == null) {
            Principal principal = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getUserPrincipal();

            if (principal == null) {
                return null;
            }

            currentPerson = personDao.findPersonByUsername(principal.getName());
        }

        return currentPerson;
    }
}
