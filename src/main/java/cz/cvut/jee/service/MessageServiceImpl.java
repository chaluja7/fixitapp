package cz.cvut.jee.service;

import cz.cvut.jee.dao.MessageDao;
import cz.cvut.jee.entity.Message;
import cz.cvut.jee.utils.security.SecurityUtil;
import org.joda.time.LocalDateTime;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 * Message service implementation.
 *
 * @author jakubchalupa
 * @since 28.12.14
 */
@Singleton
public class MessageServiceImpl implements MessageService {

    @Inject
    protected MessageDao messageDao;

    @Inject
    protected SecurityUtil securityUtil;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Message findMessage(long id) {
        return messageDao.find(id);
    }

    @Override
    public Message updateMessage(Message message) {
        return messageDao.update(message);
    }

    @Override
    public void createMessage(Message message) {
        message.setInsertedTime(new LocalDateTime());
        message.setAuthor(securityUtil.getCurrentUser());

        messageDao.create(message);
    }

    @Override
    public void deleteMessage(long id) {
        messageDao.delete(id);
    }
}
