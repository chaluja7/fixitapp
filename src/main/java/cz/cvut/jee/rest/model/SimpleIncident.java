package cz.cvut.jee.rest.model;

/**
 * Simple incident model.
 *
 * @author jakubchalupa
 * @since 29.12.14
 */
public class SimpleIncident extends IncidentModel {

    private double lat;

    private double lon;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
