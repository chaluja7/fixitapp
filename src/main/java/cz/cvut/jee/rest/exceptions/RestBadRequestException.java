package cz.cvut.jee.rest.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Bad request exception for rest calls - returns 400 bad request.
 *
 * @author jakubchalupa
 * @since 02.01.15
 */
public class RestBadRequestException extends WebApplicationException {

    public RestBadRequestException() {
        super(Response.status(Response.Status.BAD_REQUEST).build());
    }

}
