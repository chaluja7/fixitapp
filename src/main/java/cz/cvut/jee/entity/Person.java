package cz.cvut.jee.entity;

import cz.cvut.jee.entity.enums.PersonRole;
import cz.cvut.jee.entity.validators.CheckUsername;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing system user.
 *
 * @author jakubchalupa
 * @since 27.12.14
 */
@Entity
@Table(name = "person")
@SuppressWarnings("JpaDataSourceORMInspection")
public class Person extends AbstractEntity {

    @Column(unique = true)
    @CheckUsername
    private String username;

    @Column(length = 255)
    @NotBlank
    private String password;

    @Column
    @NotBlank
    @Length(min = 2, max = 20)
    private String name;

    @Column
    @NotBlank
    @Length(min = 2, max = 20)
    private String surname;

    @Column
    @Enumerated(EnumType.STRING)
    private PersonRole role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private List<Message> messages;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private List<Comment> comments;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public PersonRole getRole() {
        return role;
    }

    public void setRole(PersonRole role) {
        this.role = role;
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

    public List<Comment> getComments() {
        if(comments == null) {
            comments = new ArrayList<>();
        }

        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getWholeName() {
        return name + " " + surname;
    }
}
