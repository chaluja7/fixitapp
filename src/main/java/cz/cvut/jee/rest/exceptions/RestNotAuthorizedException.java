package cz.cvut.jee.rest.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Unauthorized exception for rest calls - returns 401 unauthorized.
 *
 * @author jakubchalupa
 * @since 02.01.15
 */
public class RestNotAuthorizedException extends WebApplicationException {

    public RestNotAuthorizedException() {
        super(Response.status(Response.Status.UNAUTHORIZED).build());
    }

}
