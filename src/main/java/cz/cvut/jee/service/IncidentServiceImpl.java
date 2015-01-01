package cz.cvut.jee.service;

import cz.cvut.jee.dao.IncidentDao;
import cz.cvut.jee.entity.*;
import cz.cvut.jee.utils.security.SecurityUtil;
import org.joda.time.LocalDateTime;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
public class IncidentServiceImpl implements IncidentService {

    @Inject
    protected IncidentDao incidentDao;

    @Inject
    protected SecurityUtil securityUtil;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Incident findIncident(long id) {
        return incidentDao.find(id);
    }

    @Override
    public Incident updateIncident(Incident incident) {
        return incidentDao.update(incident);
    }

    @Override
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
    public void deleteIncident(long id) {
        incidentDao.delete(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Incident> findAll() {
        return incidentDao.findAll();
    }

    @Override
    public void updateState(long id, IncidentState state) {
        incidentDao.updateState(id, state);
    }

    @Override
    public Incident findIncidentLazyInitialized(long id) {
        Incident incident = incidentDao.find(id);
        incident.getMessages().size();
        incident.getComments().size();

        return incident;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
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
