package cz.cvut.jee.service;

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
}
