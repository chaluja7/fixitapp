package cz.cvut.jee.service;

import cz.cvut.jee.dao.InvalidIncidentReferenceDao;
import cz.cvut.jee.dao.wrappers.InvalidIncidentStatisticItem;
import cz.cvut.jee.entity.InvalidIncidentReference;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.List;

/**
 * Invalid incident reference service implementation.
 *
 * @author jakubchalupa
 * @since 29.01.15
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class InvalidIncidentReferenceServiceImpl implements InvalidIncidentReferenceService {

    @Inject
    private InvalidIncidentReferenceDao invalidIncidentReferenceDao;

    @Override
    @RolesAllowed({"SUPER_ADMIN", "SYSTEM"})
    public void createInvalidIncidentReference(InvalidIncidentReference invalidIncidentReference) {
        invalidIncidentReferenceDao.create(invalidIncidentReference);
    }

    @Override
    @DenyAll
    public void deleteInvalidIncidentReference(long id) {
        invalidIncidentReferenceDao.delete(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed({"SUPER_ADMIN"})
    public List<InvalidIncidentReference> findAll() {
        return invalidIncidentReferenceDao.findAll();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed({"SUPER_ADMIN"})
    public InvalidIncidentReference findByIncidentId(long incidentId) {
        return invalidIncidentReferenceDao.findByIncidentId(incidentId);
    }

    @Override
    public List<InvalidIncidentStatisticItem> getStatistics() {
        return invalidIncidentReferenceDao.getStatistics();
    }
}
