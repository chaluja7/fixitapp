package cz.cvut.jee.rest.model.consumed;

/**
 * New incident model.
 *
 * @author jakubchalupa
 * @since 02.01.15
 */
public class NewIncidentModel {

    private String title;

    private String description;

    private long lat;

    private long lon;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getLon() {
        return lon;
    }

    public void setLon(long lon) {
        this.lon = lon;
    }
}
