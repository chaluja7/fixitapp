package cz.cvut.jee.dao;

import cz.cvut.jee.dao.wrappers.InvalidIncidentStatisticItem;
import cz.cvut.jee.entity.InvalidIncidentReference;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.persistence.Query;
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
        List<InvalidIncidentReference> resultList = em.createNamedQuery("InvalidIncidentReference.findByIncidentId", InvalidIncidentReference.class)
                .setParameter("incidentId", incidentId).getResultList();

        if(resultList.size() == 1) {
            return resultList.get(0);
        }

        return  null;
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
