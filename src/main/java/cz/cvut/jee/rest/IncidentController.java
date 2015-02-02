package cz.cvut.jee.rest;


import cz.cvut.jee.entity.Incident;
import cz.cvut.jee.entity.Message;
import cz.cvut.jee.entity.enums.IncidentState;
import cz.cvut.jee.rest.exceptions.BadGPSException;
import cz.cvut.jee.rest.exceptions.RestBadRequestException;
import cz.cvut.jee.rest.exceptions.RestNotFoundException;
import cz.cvut.jee.rest.googleMaps.IncidentAddressInitializer;
import cz.cvut.jee.rest.model.ComplexIncidentModel;
import cz.cvut.jee.rest.model.IncidentModel;
import cz.cvut.jee.rest.model.MessageModel;
import cz.cvut.jee.rest.model.SimpleIncidentModel;
import cz.cvut.jee.rest.model.consumed.NewIncidentModel;
import cz.cvut.jee.rest.model.list.DataTableResource;
import cz.cvut.jee.rest.model.list.ListIncident;
import cz.cvut.jee.rest.model.response.IdResponse;
import cz.cvut.jee.service.IncidentService;
import cz.cvut.jee.utils.dateTime.JEEDateTimeUtils;
import cz.cvut.jee.utils.security.RestSecured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
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

    @Inject
    protected IncidentAddressInitializer incidentAddressInitializer;

    /**
     * /incidents endpoint
     * @param forMap true if wanted new and in_progress incidents only
     * @return incidents
     */
    @GET
    @Produces("application/json;charset=UTF-8")
    public List<SimpleIncidentModel> getIncidents(@QueryParam("forMap") boolean forMap) {
        List<Incident> incidentList;
        if(forMap) {
            incidentList = incidentService.findAll(IncidentState.NEW, IncidentState.IN_PROGRESS);
        } else {
            incidentList = incidentService.findAll();
        }

        List<SimpleIncidentModel> modelList = new ArrayList<>();
        for(Incident incident : incidentList) {
            SimpleIncidentModel model = new SimpleIncidentModel();
            fillIncidentModel(incident, model);
            model.setLat(incident.getLatitude());
            model.setLon(incident.getLongitude());

            modelList.add(model);
        }

        return modelList;
    }

    /**
     * /incidents/{id} endpoint
     * @param id id of incident
     * @return incident with given id or null
     */
    @GET
    @Path("/{id}")
    @Produces("application/json;charset=UTF-8")
    public ComplexIncidentModel getIncidentDetail(@PathParam("id") long id) {
        Incident incident = incidentService.findIncidentLazyInitialized(id);
        if(incident == null) {
            throw new RestNotFoundException();
        }

        ComplexIncidentModel model = new ComplexIncidentModel();
        fillIncidentModel(incident, model);
        model.setDescription(incident.getDescription());
        model.setAddress(incident.getAddress());
        model.setLat(incident.getLatitude());
        model.setLon(incident.getLongitude());
        model.setMessages(new ArrayList<>());

        for(Message message : incident.getMessages()) {
            MessageModel messageModel = new MessageModel();
            messageModel.setText(message.getText());
            messageModel.setTimeOfCreation(message.getInsertedTime().toString(JEEDateTimeUtils.DATE_TIME_PATTERN));
            if(message.getAuthor() != null) {
                messageModel.setAuthor(message.getAuthor().getWholeName());
            }

            model.getMessages().add(messageModel);
        }

        return model;
    }

    /**
     * /incidents post endpoint
     * @param model incident to create
     * @return id of newly created incident
     */
    @POST
    @Consumes("application/json;charset=UTF-8")
    @Produces("application/json;charset=UTF-8")
    public Response saveIncident(NewIncidentModel model) {
        Incident incident = new Incident();
        incident.setTitle(model.getTitle());
        incident.setDescription(model.getDescription());

        try {
            incidentAddressInitializer.initializeIncidentWithAddressAndRegion(incident, model.getLat(), model.getLon());
        } catch (BadGPSException e) {
            throw new RestBadRequestException();
        }

        incidentService.createIncident(incident);
        return Response.created(URI.create("incidents/" + incident.getId())).entity(new IdResponse(incident.getId())).build();
    }

    /**
     * /incidents/list endpoint
     * @return incident list
     */
    @GET
    @Path("/list")
    @Produces("application/json;charset=UTF-8")
    @RestSecured
    public Response getIncidentList() {
        List<ListIncident> modelList = new ArrayList<>();

        for(Incident incident : incidentService.findAllForCurrentUser()) {
            ListIncident model = new ListIncident();
            fillIncidentModel(incident, model);
            model.setAddress(incident.getAddress());

            modelList.add(model);
        }

        return Response.ok(new DataTableResource<>(modelList)).build();
    }

    @GET
    @Path("/currentUserMap")
    @Produces("application/json;charset=UTF-8")
    @RestSecured
    public List<SimpleIncidentModel> getIncidentListForCurrentUser() {
        List<SimpleIncidentModel> modelList = new ArrayList<>();

        for(Incident incident : incidentService.findAllForCurrentUser(IncidentState.NEW, IncidentState.IN_PROGRESS)) {
            SimpleIncidentModel model = new SimpleIncidentModel();
            fillIncidentModel(incident, model);
            model.setLat(incident.getLatitude());
            model.setLon(incident.getLongitude());

            modelList.add(model);
        }

        return modelList;
    }

    /**
     * will copy properties from incident to incident model
     * @param incident incident
     * @param incidentModel incident model
     */
    private void fillIncidentModel(Incident incident, IncidentModel incidentModel) {
        incidentModel.setId(incident.getId());
        incidentModel.setTitle(incident.getTitle());
        incidentModel.setTimeOfCreation(incident.getInsertedTime().toString(JEEDateTimeUtils.DATE_TIME_PATTERN));
        incidentModel.setState(incident.getState().name());
    }

}
