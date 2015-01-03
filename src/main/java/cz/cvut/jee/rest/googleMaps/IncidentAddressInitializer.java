package cz.cvut.jee.rest.googleMaps;

import cz.cvut.jee.entity.Incident;
import cz.cvut.jee.entity.Region;
import cz.cvut.jee.entity.enums.IncidentState;
import cz.cvut.jee.rest.exceptions.BadGPSException;
import cz.cvut.jee.rest.googleMaps.resource.GoogleAddressResource;
import cz.cvut.jee.service.RegionService;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Helper bean for incident initialization with address from google map api.
 *
 * @author jakubchalupa
 * @since 03.01.15
 */
@Named
@Dependent
public class IncidentAddressInitializer implements Serializable {

    @Inject
    protected AddressManager addressManager;

    @Inject
    protected RegionService regionService;

    public void initializeIncidentWithAddressAndRegion(Incident incident, double lat, double lon) throws BadGPSException {
        incident.setLatitude(lat);
        incident.setLongitude(lon);

        GoogleAddressResource googleAddresses = addressManager.getGoogleAddresses(lat, lon);
        if(googleAddresses.getResults().size() > 0) {
            incident.setAddress(googleAddresses.getResults().get(0).getFormatted_address());
        }

        if(incident.getAddress() == null) {
            throw new BadGPSException();
        }

        String addressString = addressManager.getGoogleAddressesResponseString(lat, lon);
        for(Region region : regionService.findAll()) {
            if(addressString.contains("\"" + region.getName() + "\"")) {
                incident.setRegion(region);
                break;
            }
        }

        if(incident.getRegion() != null) {
            incident.setState(IncidentState.NEW);
        } else {
            incident.setState(IncidentState.INVALID);
        }
    }

}
