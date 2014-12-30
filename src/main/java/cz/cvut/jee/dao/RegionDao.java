package cz.cvut.jee.dao;

import cz.cvut.jee.entity.Region;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;

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
}
