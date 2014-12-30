package cz.cvut.jee.service;

import cz.cvut.jee.entity.Incident;

import javax.ejb.Local;
import java.util.List;

/**
 * Service for incident.
 *
 * @author jakubchalupa
 * @since 28.12.14
 */
@Local
public interface IncidentService {

    /**
     * @param id incident id
     * @return incident by id
     */
    public Incident findIncident(long id);

    /**
     * @param incident incident to update
     * @return updated incident
     */
    public Incident updateIncident(Incident incident);

    /**
     * @param incident incident to persist
     */
    public void createIncident(Incident incident);

    /**
     * @param id id of incident to delete
     */
    public void deleteIncident(long id);

    /**
     * @return all incidents
     */
    public List<Incident> findAll();
}
