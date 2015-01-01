package cz.cvut.jee.rest;


import cz.cvut.jee.entity.Incident;
import cz.cvut.jee.rest.model.SimpleIncident;
import cz.cvut.jee.rest.model.list.DataTableResource;
import cz.cvut.jee.rest.model.list.ListIncident;
import cz.cvut.jee.service.IncidentService;
import cz.cvut.jee.utils.dateTime.JEEDateTimeUtils;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

/**
 * Incidents endpoint controller.
 *
 * @author jakubchalupa
 * @since 29.12.14
 */
@Path("/incidents")
public class IncidentController {

    @Inject
    protected IncidentService incidentService;

    @GET
    @Produces("application/json;charset=UTF-8")
    public List<SimpleIncident> getIncidents() {
        List<Incident> incidentList = incidentService.findAll();

        List<SimpleIncident> simpleIncidentList = new ArrayList<>();
        for(Incident incident : incidentList) {
            SimpleIncident simpleIncident = new SimpleIncident();

            simpleIncident.setId(incident.getId());
            simpleIncident.setTitle(incident.getTitle());
            simpleIncident.setLat(incident.getLatitude());
            simpleIncident.setLon(incident.getLongitude());
            simpleIncident.setState(incident.getState().name());
            simpleIncident.setTimeOfCreation(incident.getInsertedTime().toString(JEEDateTimeUtils.dateTimePattern));
            simpleIncidentList.add(simpleIncident);
        }

        return simpleIncidentList;
    }

    @GET
    @Produces("application/json;charset=UTF-8")
    @Path("/list")
    public DataTableResource<ListIncident> getIncidentsList() {
        List<ListIncident> resourceList = new ArrayList<>();

        for(Incident incident : incidentService.findAllForCurrentUser()) {
            ListIncident resource = new ListIncident();
            resource.setId(incident.getId());
            resource.setTitle(incident.getTitle());
            resource.setAddress(incident.getAddress());
            resource.setState(incident.getState().name());
            resource.setTimeOfCreation(incident.getInsertedTime().toString(JEEDateTimeUtils.dateTimePattern));

            resourceList.add(resource);
        }

        return new DataTableResource<>(resourceList);
    }




}
