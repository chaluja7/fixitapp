package cz.cvut.jee.dao;

import cz.cvut.jee.entity.Person;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;

/**
 * Person DAO.
 *
 * @author jakubchalupa
 * @since 28.12.14
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PersonDao extends AbstractGenericDao<Person> {

    protected PersonDao() {
        super(Person.class);
    }
}
