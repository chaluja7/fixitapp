package cz.cvut.jee.service;

import cz.cvut.jee.dao.IncidentDao;
import cz.cvut.jee.entity.Incident;
import cz.cvut.jee.entity.Message;
import cz.cvut.jee.entity.Person;
import cz.cvut.jee.entity.enums.IncidentState;
import cz.cvut.jee.entity.enums.PersonRole;
import cz.cvut.jee.utils.security.SecurityUtil;
import org.joda.time.LocalDateTime;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Incident service implementation.
 *
 * @author jakubchalupa
 * @since 28.12.14
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class IncidentServiceImpl implements IncidentService {

    @Inject
    protected IncidentDao incidentDao;

    @Inject
    protected SecurityUtil securityUtil;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @PermitAll
    public Incident findIncident(long id) {
        return incidentDao.find(id);
    }

    @Override
    @DenyAll
    public Incident updateIncident(Incident incident) {
        return incidentDao.update(incident);
    }

    @Override
    @PermitAll
    public void createIncident(Incident incident) {
        incident.setInsertedTime(new LocalDateTime());

        if(incident.getMessages().size() > 0) {
            for(Message message : incident.getMessages()) {
                message.setInsertedTime(new LocalDateTime());
            }
        }

        incidentDao.create(incident);
    }

    @Override
    @DenyAll
    public void deleteIncident(long id) {
        incidentDao.delete(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @PermitAll
    public List<Incident> findAll() {
        return incidentDao.findAll();
    }

    @Override
    @RolesAllowed({"SUPER_ADMIN", "REGION_ADMIN", "OFFICER"})
    public void updateState(long id, IncidentState state) {
        incidentDao.updateState(id, state);
    }

    @Override
    @PermitAll
    public Incident findIncidentLazyInitialized(long id) {
        Incident incident = incidentDao.find(id);
        if(incident != null) {
            incident.getMessages().size();
            incident.getComments().size();
        }

        return incident;
    }

    @Override
    @RolesAllowed({"SUPER_ADMIN", "REGION_ADMIN", "OFFICER"})
    public Incident findIncidentLazyInitializedWithAccessControl(long id) {
        Person currentUser = securityUtil.getCurrentUser();
        Incident incident = incidentDao.find(id);

        if(currentUser.getRole().equals(PersonRole.SUPER_ADMIN)) {
            incident.getMessages().size();
            incident.getComments().size();

            return incident;
        } else if(currentUser.getRole().equals(PersonRole.REGION_ADMIN) || currentUser.getRole().equals(PersonRole.OFFICER)) {
            if(incident.getRegion().equals(currentUser.getRegion())) {
                incident.getMessages().size();
                incident.getComments().size();

                return incident;
            }
        }

        return null;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed({"SUPER_ADMIN", "REGION_ADMIN", "OFFICER"})
    public List<Incident> findAllForCurrentUser() {
        Person currentUser = securityUtil.getCurrentUser();

        if(currentUser.getRole().equals(PersonRole.SUPER_ADMIN)) {
            return incidentDao.findAll();
        } else if(currentUser.getRole().equals(PersonRole.REGION_ADMIN) || currentUser.getRole().equals(PersonRole.OFFICER)) {
            return incidentDao.findAllFromRegion(currentUser.getRegion().getId());
        }

        return new ArrayList<>();
    }
}
