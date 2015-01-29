package cz.cvut.jee.service;

import cz.cvut.jee.dao.wrappers.InvalidIncidentStatisticItem;
import cz.cvut.jee.entity.InvalidIncidentReference;

import javax.ejb.Local;
import java.util.List;

/**
 * Service for Invalid incident reference.
 *
 * @author jakubchalupa
 * @since 29.01.14
 */
@Local
public interface InvalidIncidentReferenceService {

    /**
     * @param invalidIncidentReference invalidIncidentReference to persist
     */
    public void createInvalidIncidentReference(InvalidIncidentReference invalidIncidentReference);

    /**
     * @param id id of invalidIncidentReference to delete
     */
    public void deleteInvalidIncidentReference(long id);

    /**
     * @return all records
     */
    public List<InvalidIncidentReference> findAll();

    /**
     * @param incidentId id of incident
     * @return InvalidIncidentReference for given incidentId or null
     */
    public InvalidIncidentReference findByIncidentId(long incidentId);

    /**
     * @return statistics of invalid incidents
     */
    public List<InvalidIncidentStatisticItem> getStatistics();

}
