package cz.cvut.jee.dao;

import cz.cvut.jee.entity.Comment;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;

/**
 * Comment DAO.
 *
 * @author jakubchalupa
 * @since 28.12.14
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class CommentDao extends AbstractGenericDao<Comment> {

    protected CommentDao() {
        super(Comment.class);
    }
}
