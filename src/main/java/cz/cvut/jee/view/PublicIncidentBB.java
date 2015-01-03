package cz.cvut.jee.view;

import cz.cvut.jee.entity.Incident;
import cz.cvut.jee.entity.enums.IncidentState;
import cz.cvut.jee.rest.exceptions.BadGPSException;
import cz.cvut.jee.rest.googleMaps.IncidentAddressInitializer;
import cz.cvut.jee.service.IncidentService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Backing bean for public incident - view and new.
 *
 * @author jakubchalupa
 * @since 29.12.14
 */
@Named
@ViewScoped
public class PublicIncidentBB implements Serializable {

    @Inject
    protected IncidentService incidentService;

    @Inject
    protected IncidentAddressInitializer incidentAddressInitializer;

    private Long id;

    private Incident incident = new Incident();

    private double lat;

    private double lon;

    public void loadIncident() {
        if(id != null) {
            incident = incidentService.findIncidentLazyInitialized(id);
            if(incident == null) {
                throw new RuntimeException("Invalid id");
            }
        } else {
            throw new RuntimeException("Invalid id");
        }
    }

    public void handleLatLon() throws BadGPSException {
        incidentAddressInitializer.initializeIncidentWithAddressAndRegion(incident, lat, lon);
    }

    public String saveIncident() {
        incidentService.createIncident(incident);
        if(incident.getState().equals(IncidentState.NEW)) {
            return "index?id=" + incident.getId() + "&faces-redirect=true";
        }

        return "index?invalidIncidentCreated=true&faces-redirect=true";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Incident getIncident() {
        return incident;
    }

    public void setIncident(Incident incident) {
        this.incident = incident;
    }

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
