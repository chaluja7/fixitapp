package cz.cvut.jee.service;

import cz.cvut.jee.dao.CommentDao;
import cz.cvut.jee.entity.Comment;
import cz.cvut.jee.utils.security.SecurityUtil;
import org.joda.time.LocalDateTime;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 * Comment service implementation.
 *
 * @author jakubchalupa
 * @since 28.12.14
 */
@Singleton
public class CommentServiceImpl implements CommentService {

    @Inject
    protected CommentDao commentDao;

    @Inject
    protected SecurityUtil securityUtil;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Comment findComment(long id) {
        return commentDao.find(id);
    }

    @Override
    public Comment updateComment(Comment comment) {
        return commentDao.update(comment);
    }

    @Override
    public void createComment(Comment comment) {
        comment.setInsertedTime(new LocalDateTime());
        comment.setAuthor(securityUtil.getCurrentUser());

        commentDao.create(comment);
    }

    @Override
    public void deleteComment(long id) {
        commentDao.delete(id);
    }
}
