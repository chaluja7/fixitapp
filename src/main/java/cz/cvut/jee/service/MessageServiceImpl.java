package cz.cvut.jee.service;

import cz.cvut.jee.dao.MessageDao;
import cz.cvut.jee.entity.Message;
import cz.cvut.jee.utils.security.SecurityUtil;
import org.joda.time.LocalDateTime;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;

/**
 * Message service implementation.
 *
 * @author jakubchalupa
 * @since 28.12.14
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class MessageServiceImpl implements MessageService {

    @Inject
    protected MessageDao messageDao;

    @Inject
    protected SecurityUtil securityUtil;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @PermitAll
    public Message findMessage(long id) {
        return messageDao.find(id);
    }

    @Override
    @DenyAll
    public Message updateMessage(Message message) {
        return messageDao.update(message);
    }

    @Override
    @RolesAllowed({"SUPER_ADMIN", "REGION_ADMIN", "OFFICER"})
    public void createMessage(Message message) {
        message.setInsertedTime(new LocalDateTime());
        message.setAuthor(securityUtil.getCurrentUser());

        messageDao.create(message);
    }

    @Override
    @DenyAll
    public void deleteMessage(long id) {
        messageDao.delete(id);
    }
}
