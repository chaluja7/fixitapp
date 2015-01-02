package cz.cvut.jee.rest;

import cz.cvut.jee.entity.Person;
import cz.cvut.jee.rest.model.list.DataTableResource;
import cz.cvut.jee.rest.model.list.ListPerson;
import cz.cvut.jee.service.PersonService;
import cz.cvut.jee.utils.security.RestSecureLogged;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jakubchalupa
 * @since 02.01.15
 */
@Path("/users")
@RestSecureLogged
public class PersonController {

    @Inject
    protected PersonService personService;

    @GET
    @Path("/list")
    @Produces("application/json;charset=UTF-8")
    public Response getUserList() {
        List<ListPerson> modelList = new ArrayList<>();

        for(Person person : personService.findAllForCurrentUser()) {
            ListPerson model = new ListPerson();
            model.setId(person.getId());
            model.setName(person.getWholeName());
            model.setUsername(person.getUsername());

            if(person.getRegion() != null) {
                model.setRegion(person.getRegion().getName());
            }

            model.setRole(person.getRole().name());

            modelList.add(model);
        }

        return Response.ok(new DataTableResource<>(modelList)).build();
    }

}
