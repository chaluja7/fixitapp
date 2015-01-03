package cz.cvut.jee.rest.googleMaps.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Single address result from google maps api.
 *
 * @author jakubchalupa
 * @since 03.01.15
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class GoogleResult {

    private String formatted_address;

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

}