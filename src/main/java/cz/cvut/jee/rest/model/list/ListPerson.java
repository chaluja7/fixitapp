package cz.cvut.jee.rest.model.list;

/**
 * Simple region model.
 *
 * @author jakubchalupa
 * @since 02.01.15
 */
public class ListPerson {

    public long id;

    public String name;

    private String username;

    private String region;

    private String role;


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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
