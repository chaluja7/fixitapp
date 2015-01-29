package cz.cvut.jee.service;

import cz.cvut.jee.entity.Incident;
import cz.cvut.jee.entity.Message;
import cz.cvut.jee.entity.enums.IncidentState;
import cz.cvut.jee.utils.security.SecurityUtil;
import org.joda.time.LocalDateTime;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Incident service test class.
 *
 * @author jakubchalupa
 * @since 28.01.15
 */
public class MessageServiceTest extends AbstractArquillianTest {

    @Inject
    private IncidentService incidentService;

    @Inject
    private MessageService messageService;
    
    @Inject
    private SecurityUtil securityUtil;

    private Message prepareMessage() {
        Message message = new Message();

        Incident incident = new Incident();
        incident.setTitle("testIncident");
        incident.setLatitude(10);
        incident.setLongitude(20);
        incident.setState(IncidentState.NEW);
        incident.setAddress("testAddress");
        incident.setDescription("testDescription");

        message.setIncident(incident);
        message.setAuthor(securityUtil.getCurrentUser());
        message.setInsertedTime(new LocalDateTime());
        message.setText("foo");
        
        return message;
    }

    @Test(groups = "OFFICER")
    public void testCreateFind() {
        Message message = prepareMessage();
        incidentService.createIncident(message.getIncident());
        messageService.createMessage(message);

        em.clear();

        Message retrieved = messageService.findMessage(message.getId());
        
        assertNotNull(retrieved);
        assertEquals(message, retrieved);
        assertEquals(message.getInsertedTime(), retrieved.getInsertedTime());
        assertEquals(message.getText(), retrieved.getText());
        assertNotNull(retrieved.getAuthor());
        assertNotNull(retrieved.getIncident());
    }

}
