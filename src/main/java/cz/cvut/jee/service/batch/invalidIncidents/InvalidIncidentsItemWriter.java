package cz.cvut.jee.service.batch.invalidIncidents;

import cz.cvut.jee.entity.InvalidIncidentReference;
import cz.cvut.jee.service.InvalidIncidentReferenceService;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Batch - invalid incidents - writer.
 *
 * @author jakubchalupa
 * @since 29.01.15
 */
@Named
public class InvalidIncidentsItemWriter extends AbstractItemWriter {

    @Inject
    private InvalidIncidentReferenceService invalidIncidentReferenceService;

    @Override
    public void writeItems(List<Object> items) throws Exception {
        for(Object item : items) {
            invalidIncidentReferenceService.createInvalidIncidentReference((InvalidIncidentReference) item);
        }
    }
}
