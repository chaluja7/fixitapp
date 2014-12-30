package cz.cvut.jee.service;

import cz.cvut.jee.entity.Comment;

import javax.ejb.Local;

/**
 * Service for comment.
 *
 * @author jakubchalupa
 * @since 28.12.14
 */
@Local
public interface CommentService {

    /**
     * @param id comment id
     * @return comment by id
     */
    public Comment findComment(long id);

    /**
     * @param comment comment to update
     * @return updated comment
     */
    public Comment updateComment(Comment comment);

    /**
     * @param comment comment to persist
     */
    public void createComment(Comment comment);

    /**
     * @param id id of comment to delete
     */
    public void deleteComment(long id);

}
