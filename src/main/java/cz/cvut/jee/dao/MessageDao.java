package cz.cvut.jee.dao;

import cz.cvut.jee.entity.Message;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;

/**
 * Message DAO.
 *
 * @author jakubchalupa
 * @since 28.12.14
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class MessageDao extends AbstractGenericDao<Message> {

    protected MessageDao() {
        super(Message.class);
    }
}
