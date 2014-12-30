package cz.cvut.jee.testik.students;

import javax.ejb.Stateless;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Loads a list of {@link Student}s and makes it available under the "allStudentsSortedByName" EL name.
 *
 * @author Jozef Hartinger
 */
@Stateless
public class StudentList {

    @PersistenceContext
    private EntityManager manager;

    @Produces
    @Model
    public List<Student> getAllStudentsSortedByName() {
        return manager
                .createQuery("select student from Student student order by student.surname", Student.class)
                .getResultList();
    }
}
