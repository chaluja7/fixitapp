package cz.cvut.jee.service;

import cz.cvut.jee.dao.RegionDao;
import cz.cvut.jee.entity.Region;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;

/**
 * Region service implementation.
 *
 * @author jakubchalupa
 * @since 29.12.14
 */
@Singleton
public class RegionServiceImpl implements RegionService {

    @Inject
    protected RegionDao regionDao;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Region findRegion(long id) {
        return regionDao.find(id);
    }

    @Override
    public Region updateRegion(Region region) {
        return regionDao.update(region);
    }

    @Override
    public void createRegion(Region region) {
        regionDao.create(region);
    }

    @Override
    public void deleteRegion(long id) {
        regionDao.delete(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Region> findAll() {
        return regionDao.findAll();
    }
}
