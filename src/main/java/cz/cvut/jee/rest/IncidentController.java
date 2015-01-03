package cz.cvut.jee.rest;


import cz.cvut.jee.entity.Incident;
import cz.cvut.jee.entity.IncidentState;
import cz.cvut.jee.entity.Message;
import cz.cvut.jee.entity.Region;
import cz.cvut.jee.rest.exceptions.RestNotFoundException;
import cz.cvut.jee.rest.googleMaps.AddressManager;
import cz.cvut.jee.rest.googleMaps.resource.GoogleAddressResource;
import cz.cvut.jee.rest.model.ComplexIncidentModel;
import cz.cvut.jee.rest.model.IncidentModel;
import cz.cvut.jee.rest.model.MessageModel;
import cz.cvut.jee.rest.model.SimpleIncidentModel;
import cz.cvut.jee.rest.model.consumed.NewIncidentModel;
import cz.cvut.jee.rest.model.list.DataTableResource;
import cz.cvut.jee.rest.model.list.ListIncident;
import cz.cvut.jee.rest.model.response.IdResponse;
import cz.cvut.jee.service.IncidentService;
import cz.cvut.jee.service.RegionService;
import cz.cvut.jee.utils.dateTime.JEEDateTimeUtils;
import cz.cvut.jee.utils.security.RestSecured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
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
    protected RegionService regionService;

    @Inject
    protected AddressManager addressManager;

    @GET
    @Produces("application/json;charset=UTF-8")
    public List<SimpleIncidentModel> getIncidents() {
        List<Incident> incidentList = incidentService.findAll();

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

    @POST
    @Consumes("application/json;charset=UTF-8")
    @Produces("application/json;charset=UTF-8")
    public IdResponse saveIncident(NewIncidentModel model) {
        Incident incident = new Incident();
        incident.setTitle(model.getTitle());
        incident.setDescription(model.getDescription());
        incident.setLatitude(model.getLat());
        incident.setLongitude(model.getLon());

        //get formatted address on given gps
        GoogleAddressResource googleAddressResource = addressManager.getGoogleAddresses(model.getLat(), model.getLon());
        if(googleAddressResource.getResults().size() > 0) {
            incident.setAddress(googleAddressResource.getResults().get(0).getFormatted_address());
        }

        //set region, if one exists on given gps
        String addressString = addressManager.getGoogleAddressesResponseString(model.getLat(), model.getLon());
        for(Region region : regionService.findAll()) {
            if(addressString.contains("\"" + region.getName() + "\"")) {
                incident.setRegion(region);
                break;
            }
        }

        //if region on given gps is in database, incident is valid.
        if(incident.getRegion() != null) {
            incident.setState(IncidentState.NEW);
        } else {
            incident.setState(IncidentState.INVALID);
        }

        incidentService.createIncident(incident);
        return new IdResponse(incident.getId());
    }

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
            messageModel.setTimeOfCreation(message.getInsertedTime().toString(JEEDateTimeUtils.dateTimePattern));
            if(message.getAuthor() != null) {
                messageModel.setAuthor(message.getAuthor().getWholeName());
            }

            model.getMessages().add(messageModel);
        }

        return model;
    }

    private void fillIncidentModel(Incident incident, IncidentModel incidentModel) {
        incidentModel.setId(incident.getId());
        incidentModel.setTitle(incident.getTitle());
        incidentModel.setTimeOfCreation(incident.getInsertedTime().toString(JEEDateTimeUtils.dateTimePattern));
        incidentModel.setState(incident.getState().name());
    }

}
