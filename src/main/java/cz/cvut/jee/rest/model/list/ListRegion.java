package cz.cvut.jee.rest.model.list;

/**
 * Simple region model.
 *
 * @author jakubchalupa
 * @since 02.01.15
 */
public class ListRegion {

    private long id;

    private String name;

    private String admin;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
