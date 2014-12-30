package cz.cvut.jee.testik.students;

import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * Holds the state of the student registration process.
 * @author Jozef Hartinger
 *
 */
@ConversationScoped
@Named
@Stateful
public class StudentRegistrationWizard implements Serializable {

    @Inject
    private Conversation conversation;

    @PersistenceContext
    private EntityManager manager;

    private final Student student = new Student();

    @Produces
    @Named
    public Student getStudent() {
        return student;
    }

    public void begin() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    public String cancel() {
        conversation.end();
        return "cancel";
    }

    public String register() {
        manager.persist(student);
        conversation.end();
        return "register";
    }
}
