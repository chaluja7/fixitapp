package cz.cvut.jee.rest.model;

/**
 * Common incident model parent.
 *
 * @author jakubchalupa
 * @since 01.01.15
 */
public abstract class IncidentModel {

    private long id;

    private String title;

    private String state;

    private String timeOfCreation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(String timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }
}
