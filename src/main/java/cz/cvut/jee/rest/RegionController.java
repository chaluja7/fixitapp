package cz.cvut.jee.rest;

import cz.cvut.jee.entity.Person;
import cz.cvut.jee.entity.Region;
import cz.cvut.jee.entity.enums.PersonRole;
import cz.cvut.jee.rest.model.list.DataTableResource;
import cz.cvut.jee.rest.model.list.ListRegion;
import cz.cvut.jee.service.RegionService;
import cz.cvut.jee.utils.security.RestSecured;

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
@Path("/regions")
@RestSecured(PersonRole.SUPER_ADMIN)
public class RegionController {

    @Inject
    protected RegionService regionService;

    /**
     * @return region list
     */
    @GET
    @Path("/list")
    @Produces("application/json;charset=UTF-8")
    public Response getRegionList() {
        List<ListRegion> modelList = new ArrayList<>();

        for(Region region : regionService.findAllForCurrentUser()) {
            ListRegion model = new ListRegion();
            model.setId(region.getId());
            model.setName(region.getName());

            Person person = regionService.findRegionAdmin(region.getId());
            if(person != null) {
                model.setAdmin(person.getWholeName());
            }

            modelList.add(model);
        }

        return Response.ok(new DataTableResource<>(modelList)).build();
    }

}
