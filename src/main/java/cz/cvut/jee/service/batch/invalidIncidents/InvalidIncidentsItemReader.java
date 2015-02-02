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

    /**
     * will collect data to read
     * @param checkpoint checkpoint
     */
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

    /**
     * @return next item from collection
     * @throws Exception
     */
    @Override
    public Incident readItem() throws Exception {
        if(incidentIterator.hasNext()) {
            recordNumber++;
            return incidentIterator.next();
        }

        return null;
    }

    /**
     * @return number of last processed item
     * @throws Exception
     */
    @Override
    public Serializable checkpointInfo() throws Exception {
        return recordNumber;
    }

}
