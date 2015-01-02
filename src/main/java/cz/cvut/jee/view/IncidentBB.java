package cz.cvut.jee.view;

import cz.cvut.jee.entity.Comment;
import cz.cvut.jee.entity.Incident;
import cz.cvut.jee.entity.IncidentState;
import cz.cvut.jee.entity.Message;
import cz.cvut.jee.service.CommentService;
import cz.cvut.jee.service.IncidentService;
import cz.cvut.jee.service.MessageService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Backing bean for incident.
 *
 * @author jakubchalupa
 * @since 29.12.14
 */
@Named
@ViewScoped
public class IncidentBB implements Serializable {

    @Inject
    protected IncidentService incidentService;

    @Inject
    protected MessageService messageService;

    @Inject
    protected CommentService commentService;

    private Long id;

    private Incident incident;

    private Message newMessage = new Message();

    private Comment newComment = new Comment();

    public void loadIncident() {
        if(id != null) {
            incident = incidentService.findIncidentLazyInitializedWithAccessControl(id);
            if(incident == null) {
                throw new RuntimeException("Invalid id");
            }
        }
    }

    public String changeState() {
        incidentService.updateState(incident.getId(), incident.getState());
        return "incident-list?dataSaved=true&faces-redirect=true";
    }

    public String addMessage() {
        newMessage.setIncident(incident);
        messageService.createMessage(newMessage);

        return "incident?id=" + id + "&dataSaved=true&faces-redirect=true";
    }

    public String addComment() {
        newComment.setIncident(incident);
        commentService.createComment(newComment);

        return "incident?id=" + id + "&dataSaved=true&faces-redirect=true";
    }

    public Map<String, Object> getAllIncidentStates() {
        Map<String, Object> map = new HashMap<>();
        for(IncidentState incidentState : IncidentState.values()) {
            map.put(incidentState.name(), incidentState);
        }

        return map;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Incident getIncident() {
        return incident;
    }

    public void setIncident(Incident incident) {
        this.incident = incident;
    }

    public Message getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(Message newMessage) {
        this.newMessage = newMessage;
    }

    public Comment getNewComment() {
        return newComment;
    }

    public void setNewComment(Comment newComment) {
        this.newComment = newComment;
    }
}
