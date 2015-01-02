package cz.cvut.jee.service;

import cz.cvut.jee.dao.CommentDao;
import cz.cvut.jee.entity.Comment;
import cz.cvut.jee.utils.security.SecurityUtil;
import org.joda.time.LocalDateTime;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;

/**
 * Comment service implementation.
 *
 * @author jakubchalupa
 * @since 28.12.14
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class CommentServiceImpl implements CommentService {

    @Inject
    protected CommentDao commentDao;

    @Inject
    protected SecurityUtil securityUtil;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed({"SUPER_ADMIN", "REGION_ADMIN", "OFFICER"})
    public Comment findComment(long id) {
        return commentDao.find(id);
    }

    @Override
    @DenyAll
    public Comment updateComment(Comment comment) {
        return commentDao.update(comment);
    }

    @Override
    @RolesAllowed({"SUPER_ADMIN", "REGION_ADMIN", "OFFICER"})
    public void createComment(Comment comment) {
        comment.setInsertedTime(new LocalDateTime());
        comment.setAuthor(securityUtil.getCurrentUser());

        commentDao.create(comment);
    }

    @Override
    @DenyAll
    public void deleteComment(long id) {
        commentDao.delete(id);
    }
}
