package cz.cvut.jee.service;

import cz.cvut.jee.entity.Incident;
import cz.cvut.jee.entity.Message;
import cz.cvut.jee.entity.enums.IncidentState;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Incident service test class.
 *
 * @author jakubchalupa
 * @since 28.01.15
 */
public class IncidentServiceTest extends AbstractArquillianTest {

    @Inject
    private IncidentService incidentService;

    @Inject
    private RegionService regionService;

    private Incident prepareIncident(String text, int... region) {
        Incident incident = new Incident();
        
        Message message = new Message();
        message.setText(text);
        List<Message> messageList = new ArrayList<>();
        messageList.add(message);

        if(region.length == 0 || region.length > 1)
            incident.setRegion(regionService.findRegion(2));
        else
            incident.setRegion(regionService.findRegion(region[0]));
        
        incident.setTitle("testIncident");
        incident.setLatitude(10);
        incident.setLongitude(20);
        incident.setState(IncidentState.NEW);
        incident.setAddress("testAddress");
        incident.setDescription("testDescription");
        incident.setMessages(messageList);

        return incident;
    }

    @Test
    public void testCreateFind() {
        Incident incident = prepareIncident("foo");
        incidentService.createIncident(incident);
        em.clear();

        Incident retrieved = incidentService.findIncident(incident.getId());
        assertNotNull(retrieved);
        assertEquals(incident, retrieved);
        assertEquals(incident.getTitle(), retrieved.getTitle());
        assertEquals(incident.getLatitude(), retrieved.getLatitude());
        assertEquals(incident.getLongitude(), retrieved.getLongitude());
        assertEquals(incident.getState(), retrieved.getState());
        assertEquals(incident.getAddress(), retrieved.getAddress());
        assertEquals(incident.getDescription(), retrieved.getDescription());
    }

    @Test(groups = "SUPER_ADMIN")
    public void testUpdateState() {
        Incident incident = prepareIncident("foo");
        incidentService.createIncident(incident);

        incidentService.updateState(incident.getId(), IncidentState.IN_PROGRESS);
        em.clear();
        Incident updated = incidentService.findIncident(incident.getId());
        assertNotNull(updated);
        assertNotNull(updated.getState());
        assertEquals(updated.getState(), IncidentState.IN_PROGRESS);
    }
    
    @Test
    public void findAll(){
        Incident incident1 = prepareIncident("foo1");
        Incident incident2 = prepareIncident("foo2");
        Incident incident3 = prepareIncident("foo3");
        List<Incident> initialList = new ArrayList<>();
        initialList.add(incident1);
        initialList.add(incident2);

        incidentService.createIncident(incident1);
        incidentService.createIncident(incident2);
        incidentService.createIncident(incident3);

        List<Incident> retrievedList = incidentService.findAll(incident2.getState());
        assertTrue(retrievedList.containsAll(initialList));
        
        initialList.add(incident3);
        assertFalse(retrievedList.contains(initialList));
    }

    @Test(groups = "SUPER_ADMIN")
    public void testFindIncidentLazyInitializedWithAccessControlSuperAdmin() {
        Incident incident1 = prepareIncident("foo", 1);
        Incident incident2 = prepareIncident("foo", 2);
        incidentService.createIncident(incident1);
        incidentService.createIncident(incident2);
        em.clear();

        Incident retrieved1 = incidentService.findIncidentLazyInitializedWithAccessControl(incident1.getId());
        assertNotNull(retrieved1);
        assertEquals(retrieved1.getId(), incident1.getId());
        
        Incident retrieved2 = incidentService.findIncidentLazyInitializedWithAccessControl(incident2.getId());
        assertNotNull(retrieved2);
        assertEquals(retrieved2.getId(), incident2.getId());
    }

    @Test(groups = "SUPER_ADMIN")
    public void testFindIncidentLazyInitialized(){
        Incident incident = prepareIncident("foo");
        incidentService.createIncident(incident);

        Incident retrieved = incidentService.findIncident(incident.getId());
        assertEquals(retrieved.getComments().size(), 0);
        
        em.clear();

        retrieved = incidentService.findIncidentLazyInitialized(incident.getId());
        assertEquals(retrieved.getComments().size(), incident.getComments().size());
    }


    @Test(groups = "REGION_ADMIN")
    public void testFindIncidentLazyInitializedWithAccessControlRegionAdmin() {
        Incident incident1 = prepareIncident("foo", 1);
        Incident incident2 = prepareIncident("foo", 2);
        incidentService.createIncident(incident1);
        incidentService.createIncident(incident2);
        em.clear();

        Incident retrieved1 = incidentService.findIncidentLazyInitializedWithAccessControl(incident1.getId());
        assertNotNull(retrieved1);
        assertEquals(retrieved1.getId(), incident1.getId());

        Incident retrieved2 = incidentService.findIncidentLazyInitializedWithAccessControl(incident2.getId());
        assertNull(retrieved2);
    }
    @Test(groups = "OFFICER")
    public void testFindIncidentLazyInitializedWithAccessControlOfficer() {

        Incident incident1 = prepareIncident("foo", 1);
        Incident incident2 = prepareIncident("foo", 2);
        incidentService.createIncident(incident1);
        incidentService.createIncident(incident2);
        em.clear();

        Incident retrieved1 = incidentService.findIncidentLazyInitializedWithAccessControl(incident1.getId());
        assertNotNull(retrieved1);
        assertEquals(retrieved1.getId(), incident1.getId());

        Incident retrieved2 = incidentService.findIncidentLazyInitializedWithAccessControl(incident2.getId());
        assertNull(retrieved2);
    }

}
