package cz.cvut.jee.rest.googleMaps;

import cz.cvut.jee.rest.googleMaps.resource.GoogleAddressResource;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;

/**
 * Bean for handling address from google map api.
 *
 * @author jakubchalupa
 * @since 03.01.15
 */
@Named
@Dependent
public class AddressManager implements Serializable {

    private static final String googleApiUrl = "https://maps.googleapis.com";

    private static final String googleMapJsonUri = "maps/api/geocode/json";

    private static final String languageParam = "language";

    private static final String gpsParam = "latlng";

    /**
     * @param lat latitude
     * @param lon longitude
     * @return google address response on given latlon
     */
    public GoogleAddressResource getGoogleAddresses(double lat, double lon) {
        return getGoogleAddressResponse(lat, lon).readEntity(GoogleAddressResource.class);
    }

    /**
     * @param lat latitude
     * @param lon longitude
     * @return whole string of google address response on given latlon
     */
    public String getGoogleAddressesResponseString(double lat, double lon) {
        return getGoogleAddressResponse(lat, lon).readEntity(String.class);
    }

    /**
     * @param lat latitude
     * @param lon longitude
     * @return response from google api on given latlon
     */
    private Response getGoogleAddressResponse(double lat, double lon) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(googleApiUrl).path(googleMapJsonUri).
                queryParam(languageParam, "cs").
                queryParam(gpsParam, lat + "," + lon);

        return target.request(MediaType.APPLICATION_JSON_TYPE).get();
    }
}
