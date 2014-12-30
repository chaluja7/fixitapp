package cz.cvut.jee.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing area unit.
 *
 * @author jakubchalupa
 * @since 28.12.14
 */
@Entity
@Table(name = "region")
@SuppressWarnings("JpaDataSourceORMInspection")
public class Region extends AbstractEntity {

    @Column(nullable = false, unique = true)
    @NotBlank
    @Length(min = 2, max = 20)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "region")
    private List<Incident> incidents;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Incident> getIncidents() {
        if(incidents == null) {
            incidents = new ArrayList<>();
        }
        return incidents;
    }

    public void setIncidents(List<Incident> incidents) {
        this.incidents = incidents;
    }

    public void addIncident(Incident incident) {
        if(!getIncidents().contains(incident)) {
            incidents.add(incident);
        }

        incident.setRegion(this);
    }

}
