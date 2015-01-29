package cz.cvut.jee.service;

import cz.cvut.jee.entity.Comment;
import cz.cvut.jee.entity.Incident;
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
public class CommentServiceTest extends AbstractArquillianTest {

    @Inject
    private IncidentService incidentService;

    @Inject
    private CommentService commentService;
    
    @Inject
    private SecurityUtil securityUtil;

    private Comment prepareComment() {
        Comment comment = new Comment();
        
        Incident incident = new Incident();
        incident.setTitle("testIncident");
        incident.setLatitude(10);
        incident.setLongitude(20);
        incident.setState(IncidentState.NEW);
        incident.setAddress("testAddress");
        incident.setDescription("testDescription");

        comment.setIncident(incident);
        comment.setAuthor(securityUtil.getCurrentUser());
        comment.setInsertedTime(new LocalDateTime());
        comment.setText("foo");
        
        return comment;
    }

    @Test(groups = "OFFICER")
    public void testCreateFind() {
        Comment comment = prepareComment();
        incidentService.createIncident(comment.getIncident());
        commentService.createComment(comment);
        
        em.clear();

        Comment retrieved = commentService.findComment(comment.getId());
        
        assertNotNull(retrieved);
        assertEquals(comment, retrieved);
        assertEquals(comment.getInsertedTime(), retrieved.getInsertedTime());
        assertEquals(comment.getText(), retrieved.getText());
        assertNotNull(retrieved.getAuthor());
        assertNotNull(retrieved.getIncident());
    }

}
