package cz.cvut.jee.dao;

import cz.cvut.jee.entity.Incident;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;

/**
 * Incident DAO.
 *
 * @author jakubchalupa
 * @since 28.12.14
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class IncidentDao extends AbstractGenericDao<Incident> {

    protected IncidentDao() {
        super(Incident.class);
    }
}
