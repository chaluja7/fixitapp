package cz.cvut.jee.service;

import cz.cvut.jee.entity.Person;
import cz.cvut.jee.entity.Region;

import javax.ejb.Local;
import java.util.List;

/**
 * Service for region.
 *
 * @author jakubchalupa
 * @since 28.12.14
 */
@Local
public interface RegionService {

    /**
     * @param id region id
     * @return region by id
     */
    public Region findRegion(long id);

    /**
     * @param region region to update
     * @return udpated region
     */
    public Region updateRegion(Region region);

    /**
     * @param region region to persist
     */
    public void createRegion(Region region);

    /**
     * @param id id of region to delete
     */
    public void deleteRegion(long id);

    /**
     * @return all regions
     */
    public List<Region> findAll();

    /**
     * @return all regions available to currently logged user
     */
    public List<Region> findAllForCurrentUser();

    /**
     * @param regionId id of region
     * @return admin of region with given id
     */
    public Person findRegionAdmin(long regionId);

    /**
     * @param regionId id of region to update
     * @param personId new region admin id
     */
    public void updateRegionAdmin(long regionId, long personId);

    /**
     * @param id region id
     * @return region by id with access control
     */
    public Region findRegionWithAccessControl(long id);
}
