package cz.cvut.jee.entity;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalDateTime;

import javax.persistence.*;

/**
 * Entity representing message for logged users only.
 *
 * @author jakubchalupa
 * @since 28.12.14
 */
@Entity
@Table(name = "comment")
@SuppressWarnings("JpaDataSourceORMInspection")
public class Comment extends AbstractEntity {

    @Column(length = 2000)
    @NotBlank
    @Length(min = 2, max = 2000)
    private String text;

    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime insertedTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private Person author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "incident_id")
    private Incident incident;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getInsertedTime() {
        return insertedTime;
    }

    public void setInsertedTime(LocalDateTime insertedTime) {
        this.insertedTime = insertedTime;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public Incident getIncident() {
        return incident;
    }

    public void setIncident(Incident incident) {
        this.incident = incident;
    }
}
