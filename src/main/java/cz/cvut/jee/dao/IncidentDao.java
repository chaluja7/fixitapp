package cz.cvut.jee.dao;

import cz.cvut.jee.entity.Incident;
import cz.cvut.jee.entity.enums.IncidentState;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
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
        if(incidentStates == null || incidentStates.length == 0) {
            return em.createNamedQuery("Incident.findAll", Incident.class).getResultList();
        }

        return em.createNamedQuery("Incident.findAllInStates", Incident.class).setParameter("states", Arrays.asList(incidentStates)).getResultList();
    }

    /**
     * will update state of incident
     * @param id incident id
     * @param state new state
     */
    public void updateState(long id, IncidentState state) {
        em.createNamedQuery("Incident.updateState").setParameter("state", state).setParameter("id", id).executeUpdate();
    }

    /**
     * @param regionId id of region
     * @param incidentStates possible states (if null then every state)
     * @return all incidents from given region and in given states
     */
    public List<Incident> findAllFromRegion(long regionId, IncidentState... incidentStates) {
        if(incidentStates == null || incidentStates.length == 0) {
            return em.createNamedQuery("Incident.findAllFromRegion", Incident.class).setParameter("regionId", regionId).getResultList();
        }

        return em.createNamedQuery("Incident.findAllFromRegionInStates", Incident.class).setParameter("regionId", regionId)
                .setParameter("states", Arrays.asList(incidentStates)).getResultList();
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
