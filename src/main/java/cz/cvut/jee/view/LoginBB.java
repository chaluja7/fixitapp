package cz.cvut.jee.view;

import cz.cvut.jee.utils.security.SecurityUtil;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Login / logout handling bean.
 *
 * @author jakubchalupa
 * @since 01.01.15
 */
@Named
@RequestScoped
public class LoginBB implements Serializable {

    @Inject
    protected SecurityUtil securityUtil;

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/logout";
    }

    public String getCurrentUsername() {
        return securityUtil.getCurrentUser().getUsername();
    }

    public long getCurrentUserId() {
        return securityUtil.getCurrentUser().getId();
    }

}
