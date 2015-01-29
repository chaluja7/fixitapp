package cz.cvut.jee.dao;

import cz.cvut.jee.dao.wrappers.InvalidIncidentStatisticItem;
import cz.cvut.jee.entity.InvalidIncidentReference;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Invalid incident reference DAO.
 *
 * @author jakubchalupa
 * @since 29.01.14
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class InvalidIncidentReferenceDao extends AbstractGenericDao<InvalidIncidentReference> {

    public InvalidIncidentReferenceDao() {
        super(InvalidIncidentReference.class);
    }

    /**
     * @param incidentId id of incident
     * @return invalid incident reference for given incident id or null
     */
    @SuppressWarnings("JpaQlInspection")
    public InvalidIncidentReference findByIncidentId(long incidentId) {
        TypedQuery<InvalidIncidentReference> query = em.createQuery("select i from InvalidIncidentReference i where incident_id = :incidentId", InvalidIncidentReference.class);
        query.setParameter("incidentId", incidentId);

        if(query.getResultList().size() == 1) {
            return query.getResultList().get(0);
        }

        return null;
    }

    /**
     * @return list of statistics wrappers for stats
     */
    @SuppressWarnings("JpaQlInspection")
    public List<InvalidIncidentStatisticItem> getStatistics() {
        Query query = em.createQuery("select possibleRegionName as regionName, " +
                "count(incident_id) as numberOfItems from InvalidIncidentReference " +
                "group by (possibleRegionName) order by count(incident_id) desc");


        List<InvalidIncidentStatisticItem> invalidIncidentStatisticItemList = new ArrayList<>();
        List<Object[]> items = query.getResultList();
        for(Object[] item : items) {
            InvalidIncidentStatisticItem invalidIncidentStatisticItem = new InvalidIncidentStatisticItem();
            invalidIncidentStatisticItem.setRegionName((String) item[0]);
            invalidIncidentStatisticItem.setNumberOfItems((long) item[1]);

            invalidIncidentStatisticItemList.add(invalidIncidentStatisticItem);
        }

        return invalidIncidentStatisticItemList;
    }

}
