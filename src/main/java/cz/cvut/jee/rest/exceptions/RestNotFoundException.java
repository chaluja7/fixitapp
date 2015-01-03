package cz.cvut.jee.rest.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Not found exception for rest calls - returns 404 not found.
 *
 * @author jakubchalupa
 * @since 03.01.15
 */
public class RestNotFoundException extends WebApplicationException {

    public RestNotFoundException() {
        super(Response.status(Response.Status.NOT_FOUND).build());
    }

}
