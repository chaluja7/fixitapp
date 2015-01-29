package cz.cvut.jee.service.batch.invalidIncidents;

import cz.cvut.jee.entity.Incident;
import cz.cvut.jee.service.IncidentService;

import javax.batch.api.chunk.AbstractItemReader;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Iterator;

/**
 * Batch - invalid incidents - reader.
 *
 * @author jakubchalupa
 * @since 29.01.15
 */
@Named
public class InvalidIncidentsItemReader extends AbstractItemReader {

    @Inject
    private IncidentService incidentService;

    private Iterator<Incident> incidentIterator;

    private Integer recordNumber = 0;

    @Override
    public void open(Serializable checkpoint) {
        incidentIterator = incidentService.findAllForInvalidIncidentsBatchJob().iterator();

        //pokud neni checkpoint null tak se batch spustil znovu a dle toho se zachovame
        if(checkpoint != null) {
            recordNumber = (Integer) checkpoint;
            for(int i = 0; i < recordNumber; i++) {
                incidentIterator.next();
            }
        }
    }

    @Override
    public Incident readItem() throws Exception {
        if(incidentIterator.hasNext()) {
            recordNumber++;
            return incidentIterator.next();
        }

        return null;
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return recordNumber;
    }

}
