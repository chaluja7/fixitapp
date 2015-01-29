package cz.cvut.jee.service;

import cz.cvut.jee.entity.Incident;
import cz.cvut.jee.entity.Region;
import cz.cvut.jee.entity.enums.IncidentState;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Incident service test class.
 *
 * @author jakubchalupa
 * @since 28.01.15
 */
public class RegionServiceTest extends AbstractArquillianTest {

    @Inject
    private IncidentService incidentService;

    @Inject
    private RegionService regionService;

    private Region prepareRegion() {
        Region region = new Region();

        List<Incident> incidents = new ArrayList<Incident>();
        Incident incident = prepareIncident();
        incident.setRegion(region);
        incidents.add(incident);
        
        region.setIncidents(incidents);
        region.setName("foo");
        
        return region;
    }

    private Incident prepareIncident() {
        Incident incident = new Incident();

        incident.setTitle("testIncident");
        incident.setLatitude(10);
        incident.setLongitude(20);
        incident.setState(IncidentState.NEW);
        incident.setAddress("testAddress");
        incident.setDescription("testDescription");

        return incident;
    }

    @Test(groups = "SUPER_ADMIN")
    public void testCreateFind() {
        Region region = prepareRegion();
        incidentService.createIncident(region.getIncidents().get(0));
        regionService.createRegion(region);

        em.clear();
        
        Region retrieved = regionService.findRegion(region.getId());
        assertNotNull(retrieved);
        assertEquals(region, retrieved);
        //assertEquals(region.getIncidents(), retrieved.getIncidents());
        assertEquals(region.getName(), retrieved.getName());
    }

    @Test(groups = "SUPER_ADMIN")
    public void testFindAllForCurrentUserSuperAdmin() {
        List<Region> regions = regionService.findAllForCurrentUser();
        
        assertEquals(regions.size(), 6);
    }

    @Test(groups = "REGION_ADMIN")
    public void testFindAllForCurrentUser() {
        List<Region> regions = regionService.findAllForCurrentUser();
        assertEquals(regions.size(), 1);
        assertEquals(regions.get(0).getName(), "Praha 1");
    }

}
