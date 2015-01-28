package cz.cvut.jee.service;

import cz.cvut.jee.entity.Incident;
import cz.cvut.jee.entity.Message;
import cz.cvut.jee.entity.enums.IncidentState;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

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

    @PersistenceContext
    protected EntityManager em;

    @Test
    public void testCreateAndGet() {
        Incident incident = prepareIncident();
        incidentService.createIncident(incident);
        em.clear();

        Incident retrieved = incidentService.findIncident(incident.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(incident.getTitle(), retrieved.getTitle());
    }

    @Test(groups = "SUPER_ADMIN")
    public void testUpdateState() {
        Incident incident = prepareIncident();
        incidentService.createIncident(incident);

        Incident retrieved = incidentService.findIncident(incident.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertNotNull(retrieved.getState());
        Assert.assertEquals(retrieved.getState(), IncidentState.NEW);

        incidentService.updateState(retrieved.getId(), IncidentState.IN_PROGRESS);
        em.clear();
        Incident updated = incidentService.findIncident(retrieved.getId());
        Assert.assertNotNull(updated);
        Assert.assertNotNull(updated.getState());
        Assert.assertEquals(updated.getState(), IncidentState.IN_PROGRESS);
    }

    @Test(groups = "SUPER_ADMIN")
    public void testFindIncidentLazyInitializedWithAccessControlSuccess() {
        Incident incident = prepareIncident();
        incidentService.createIncident(incident);
        em.clear();

        Incident retrieved = incidentService.findIncidentLazyInitializedWithAccessControl(incident.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(retrieved.getId(), incident.getId());
    }

    @Test(groups = "OFFICER")
    public void testFindIncidentLazyInitializedWithAccessControlFailed() {
        Incident incident = prepareIncident();
        incidentService.createIncident(incident);
        em.clear();

        Incident retrieved = incidentService.findIncidentLazyInitializedWithAccessControl(incident.getId());
        Assert.assertNull(retrieved);
    }

    private Incident prepareIncident() {
        Message message = new Message();
        message.setText("testMessage");
        List<Message> messageList = new ArrayList<>();
        messageList.add(message);

        Incident incident = new Incident();
        incident.setTitle("testIncident");
        incident.setLatitude(10);
        incident.setLongitude(20);
        incident.setState(IncidentState.NEW);
        incident.setAddress("testAddress");
        incident.setDescription("testDescription");
        incident.setMessages(messageList);
        incident.setRegion(regionService.findRegion(2));

        return incident;
    }

}
