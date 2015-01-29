package cz.cvut.jee.entity;

import cz.cvut.jee.entity.enums.IncidentState;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing reported incident in some place.
 *
 * @author jakubchalupa
 * @since 28.12.14
 */
@Entity
@Table(name = "incident")
@SuppressWarnings("JpaDataSourceORMInspection")
public class Incident extends AbstractEntity {

    @Column(nullable = false)
    @NotBlank
    @Length(min = 3, max = 255)
    private String title;

    @Column
    private String address;

    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime insertedTime;

    @Column(length = 2000)
    @Length(max = 2000)
    private String description;

    @Column(nullable = false)
    @Min(-180)
    @Max(180)
    private double longitude;

    @Column(nullable = false)
    @Min(-90)
    @Max(90)
    private double latitude;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "incident")
    @OrderBy("insertedtime DESC")
    private List<Message> messages;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "incident")
    @OrderBy("insertedtime DESC")
    private List<Comment> comments;

    @Column
    @Enumerated(EnumType.STRING)
    private IncidentState state;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "incident")
    private List<InvalidIncidentReference> invalidIncidentReferenceList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getInsertedTime() {
        return insertedTime;
    }

    public void setInsertedTime(LocalDateTime insertedTime) {
        this.insertedTime = insertedTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<Message> getMessages() {
        if(messages == null) {
            messages = new ArrayList<>();
        }

        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message message) {
        if(!getMessages().contains(message)) {
            messages.add(message);
        }

        message.setIncident(this);
    }

    public List<Comment> getComments() {
        if(comments == null) {
            comments = new ArrayList<>();
        }

        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        if(!getComments().contains(comment)) {
            comments.add(comment);
        }

        comment.setIncident(this);
    }

    public IncidentState getState() {
        return state;
    }

    public void setState(IncidentState state) {
        this.state = state;
    }
}
