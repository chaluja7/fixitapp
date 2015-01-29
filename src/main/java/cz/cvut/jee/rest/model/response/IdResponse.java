package cz.cvut.jee.rest.model.response;

/**
 * Simple response - will provide id of newly created record
 *
 * @author jakubchalupa
 * @since 02.01.15
 */
public class IdResponse {

    private long id;

    public IdResponse() {

    }

    public IdResponse(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
