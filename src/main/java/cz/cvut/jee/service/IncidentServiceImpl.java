package cz.cvut.jee.service;

import cz.cvut.jee.dao.IncidentDao;
import cz.cvut.jee.entity.Incident;
import cz.cvut.jee.entity.Message;
import org.joda.time.LocalDateTime;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
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
}
