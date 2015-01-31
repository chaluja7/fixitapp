package cz.cvut.jee.dao;

import cz.cvut.jee.entity.Person;
import cz.cvut.jee.entity.Region;
import cz.cvut.jee.entity.enums.PersonRole;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Region DAO.
 *
 * @author jakubchalupa
 * @since 28.12.14
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class RegionDao extends AbstractGenericDao<Region> {

    public RegionDao() {
        super(Region.class);
    }

    /**
     * @param regionId id of region
     * @return region admin from given region
     */
    @SuppressWarnings("JpaQlInspection")
    public Person findRegionAdmin(long regionId) {
        TypedQuery<Person> query = em.createQuery("select p from Person p where region_id = :regionId and role = :regionRole", Person.class);

        query.setParameter("regionId", regionId);
        query.setParameter("regionRole", PersonRole.REGION_ADMIN);

        if(query.getResultList().size() == 1) {
            return query.getResultList().get(0);
        }

        return null;
    }

    /**
     * will update region admin (can be only one in each region)
     * @param regionId region id
     * @param personId new region admin id
     */
    @SuppressWarnings("JpaQlInspection")
    public void updateRegionAdmin(long regionId, long personId) {
        Query query = em.createQuery("update Person set role = :role where region_id = :regionId");
        query.setParameter("role", PersonRole.OFFICER);
        query.setParameter("regionId", regionId);
        query.executeUpdate();

        Query query2 = em.createQuery("update Person set role = :role where id = :id");
        query2.setParameter("role", PersonRole.REGION_ADMIN);
        query2.setParameter("id", personId);
        query2.executeUpdate();
    }

    /**
     * @param name name of region
     * @return region with given name or null
     */
    @SuppressWarnings("JpaQlInspection")
    public Region findRegionByName(String name) {
        List<Region> regionList = em.createNamedQuery("Region.findByName", Region.class).setParameter("name", name).getResultList();

        if(regionList.size() == 1) {
            return regionList.get(0);
        }

        return null;
    }

}
