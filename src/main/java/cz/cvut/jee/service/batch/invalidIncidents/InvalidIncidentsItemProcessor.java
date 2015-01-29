package cz.cvut.jee.service.batch.invalidIncidents;

import cz.cvut.jee.entity.Incident;
import cz.cvut.jee.entity.InvalidIncidentReference;
import cz.cvut.jee.rest.googleMaps.AddressManager;
import cz.cvut.jee.rest.googleMaps.resource.AddressComponentsResult;
import cz.cvut.jee.rest.googleMaps.resource.GoogleAddressResource;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Batch - invalid incidents - processor.
 *
 * @author jakubchalupa
 * @since 29.01.15
 */
@Named
public class InvalidIncidentsItemProcessor implements ItemProcessor {

    private static final String POSSIBLE_IDENTIFIER_1 = "postal_town";

    private static final String POSSIBLE_IDENTIFIER_2 = "sublocality_level_1";


    @Inject
    private AddressManager addressManager;

    @Override
    public InvalidIncidentReference processItem(Object item) throws Exception {
        Incident incident = (Incident) item;

        String possibleRegionName = null;
        //get addresses from google api
        GoogleAddressResource googleAddresses = addressManager.getGoogleAddresses(incident.getLatitude(), incident.getLongitude());
        if(googleAddresses.getResults().size() > 0) {
            //get only best match addresses (first)
            for(AddressComponentsResult result : googleAddresses.getResults().get(0).getAddress_components()) {
                //if types are present
                if(result.getTypes() != null && result.getTypes().length > 0) {
                    //look for postal_town - its value (short name) is possible region name
                    for(String type : result.getTypes()) {
                        if(type.equals(POSSIBLE_IDENTIFIER_1) || type.equals(POSSIBLE_IDENTIFIER_2)) {
                            possibleRegionName = result.getShort_name();
                            break;
                        }
                    }
                }

                if(possibleRegionName != null) {
                    break;
                }
            }
        }

        if(possibleRegionName != null) {
            InvalidIncidentReference invalidIncidentReference = new InvalidIncidentReference();
            invalidIncidentReference.setIncident(incident);
            invalidIncidentReference.setPossibleRegionName(possibleRegionName);

            return invalidIncidentReference;
        }

        return null;
    }
}
