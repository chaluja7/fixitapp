package cz.cvut.jee.service;

import cz.cvut.jee.dao.RegionDao;
import cz.cvut.jee.entity.Person;
import cz.cvut.jee.entity.Region;
import cz.cvut.jee.entity.enums.PersonRole;
import cz.cvut.jee.utils.security.SecurityUtil;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Region service implementation.
 *
 * @author jakubchalupa
 * @since 29.12.14
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class RegionServiceImpl implements RegionService {

    @Inject
    protected RegionDao regionDao;

    @Inject
    protected SecurityUtil securityUtil;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @PermitAll
    public Region findRegion(long id) {
        return regionDao.find(id);
    }

    @Override
    @RolesAllowed("SUPER_ADMIN")
    public Region updateRegion(Region region) {
        return regionDao.update(region);
    }

    @Override
    @RolesAllowed("SUPER_ADMIN")
    public void createRegion(Region region) {
        regionDao.create(region);
    }

    @Override
    @RolesAllowed("SUPER_ADMIN")
    public void deleteRegion(long id) {
        regionDao.delete(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @PermitAll
    public List<Region> findAll() {
        return regionDao.findAll();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed({"SUPER_ADMIN", "REGION_ADMIN", "OFFICER"})
    public List<Region> findAllForCurrentUser() {
        Person currentUser = securityUtil.getCurrentUser();
        List<Region> regionList = new ArrayList<>();

        if(currentUser.getRole().equals(PersonRole.SUPER_ADMIN)) {
            return regionDao.findAll();
        } else if(currentUser.getRole().equals(PersonRole.OFFICER) || currentUser.getRole().equals(PersonRole.REGION_ADMIN)) {
            regionList.add(regionDao.find(currentUser.getRegion().getId()));
        }

        return regionList;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed("SUPER_ADMIN")
    public Person findRegionAdmin(long regionId) {
        return regionDao.findRegionAdmin(regionId);
    }

    @Override
    @RolesAllowed("SUPER_ADMIN")
    public void updateRegionAdmin(long regionId, long personId) {
        regionDao.updateRegionAdmin(regionId, personId);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed({"SUPER_ADMIN", "REGION_ADMIN", "OFFICER"})
    public Region findRegionWithAccessControl(long id) {
        Person currentUser = securityUtil.getCurrentUser();

        if(currentUser.getRole().equals(PersonRole.SUPER_ADMIN)) {
            return regionDao.find(id);
        }

        return null;
    }
}
