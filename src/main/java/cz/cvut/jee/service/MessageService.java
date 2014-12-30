package cz.cvut.jee.service;

import cz.cvut.jee.entity.Message;

import javax.ejb.Local;

/**
 * Service for message.
 *
 * @author jakubchalupa
 * @since 28.12.14
 */
@Local
public interface MessageService {

    /**
     * @param id message id
     * @return message by id
     */
    public Message findMessage(long id);

    /**
     * @param message message to udpate
     * @return updated message
     */
    public Message updateMessage(Message message);

    /**
     * @param message message to persist
     */
    public void createMessage(Message message);

    /**
     * @param id id of message to delete
     */
    public void deleteMessage(long id);

}
