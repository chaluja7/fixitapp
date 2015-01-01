package cz.cvut.jee.dao;

import cz.cvut.jee.entity.Incident;
import cz.cvut.jee.entity.IncidentState;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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

    public void updateState(long id, IncidentState state) {
        Query query = em.createQuery("update Incident set state = :state where id = :id");
        query.setParameter("state", state);
        query.setParameter("id", id);

        query.executeUpdate();
    }

    public List<Incident> findAllFromRegion(long regionId) {
        TypedQuery<Incident> query = em.createQuery("from " + type.getName() + " where region_id = :regionId", Incident.class);
        query.setParameter("regionId", regionId);

        return query.getResultList();
    }
}
