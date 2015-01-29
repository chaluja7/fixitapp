package cz.cvut.jee.dao;

import cz.cvut.jee.entity.Incident;
import cz.cvut.jee.entity.enums.IncidentState;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;

/**
 * Incident DAO.
 *
 * @author jakubchalupa
 * @since 28.12.14
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class IncidentDao extends AbstractGenericDao<Incident> {

    protected IncidentDao() {
        super(Incident.class);
    }

    /**
     * @param incidentStates possible states of incidents (if null then every state)
     * @return all incidents with given states
     */
    public List<Incident> findAll(IncidentState... incidentStates) {
        String selectQuery = "from " + type.getName();

        if(incidentStates == null || incidentStates.length == 0) {
            return em.createQuery(selectQuery, Incident.class).getResultList();
        }

        TypedQuery<Incident> query = em.createQuery(selectQuery + " where state in :states", Incident.class);
        query.setParameter("states", Arrays.asList(incidentStates));

        return query.getResultList();
    }

    /**
     * will update state of incident
     * @param id incident id
     * @param state new state
     */
    public void updateState(long id, IncidentState state) {
        Query query = em.createQuery("update Incident set state = :state where id = :id");
        query.setParameter("state", state);
        query.setParameter("id", id);

        query.executeUpdate();
    }

    /**
     * @param regionId id of region
     * @param incidentStates possible states (if null then every state)
     * @return all incidents from given region and in given states
     */
    public List<Incident> findAllFromRegion(long regionId, IncidentState... incidentStates) {
        String selectQuery = "from " + type.getName() + " where region_id = :regionId";
        TypedQuery<Incident> query;

        if(incidentStates == null || incidentStates.length == 0) {
            query = em.createQuery(selectQuery, Incident.class);
        } else {
            query = em.createQuery(selectQuery + " and state in :states", Incident.class);
            query.setParameter("states", Arrays.asList(incidentStates));
        }

        query.setParameter("regionId", regionId);
        return query.getResultList();
    }

    /**
     * @return all incidents for invalid incidents batch job
     */
    public List<Incident> findAllForInvalidIncidentsBatchJob() {
        TypedQuery<Incident> query =
            em.createQuery("select i from Incident i where i.state = :state and not exists " +
                    "(select r.id from InvalidIncidentReference r where r.incident.id = i.id) order by i.id", Incident.class);
        query.setParameter("state", IncidentState.INVALID);

        return query.getResultList();
    }
}
