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

    public List<Incident> findAll(IncidentState... incidentStates) {
        String selectQuery = "from " + type.getName();

        if(incidentStates == null || incidentStates.length == 0) {
            return em.createQuery(selectQuery, Incident.class).getResultList();
        }

        TypedQuery<Incident> query = em.createQuery(selectQuery + " where state in :states", Incident.class);
        query.setParameter("states", Arrays.asList(incidentStates));

        return query.getResultList();
    }

    public void updateState(long id, IncidentState state) {
        Query query = em.createQuery("update Incident set state = :state where id = :id");
        query.setParameter("state", state);
        query.setParameter("id", id);

        query.executeUpdate();
    }

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
}
