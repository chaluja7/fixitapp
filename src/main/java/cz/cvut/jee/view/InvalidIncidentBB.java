package cz.cvut.jee.view;

import cz.cvut.jee.dao.wrappers.InvalidIncidentStatisticItem;
import cz.cvut.jee.service.InvalidIncidentReferenceService;
import cz.cvut.jee.service.batch.BatchService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Backing bean for invalidIncident statistics.
 *
 * @author jakubchalupa
 * @since 29.01.15
 */
@Named
@RequestScoped
public class InvalidIncidentBB {

    @Inject
    private InvalidIncidentReferenceService invalidIncidentReferenceService;

    @Inject
    private BatchService batchService;

    public String refreshTable() {
        batchService.processInvalidIncidentsJob();
        return "invalid-incident-list?faces-redirect=true";
    }

    public List<InvalidIncidentStatisticItem> getInvalidIncidentStatisticItems() {
        return invalidIncidentReferenceService.getStatistics();
    }

}
