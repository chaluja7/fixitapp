package cz.cvut.jee.rest.googleMaps.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Single address result from google maps api.
 *
 * @author jakubchalupa
 * @since 03.01.15
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class GoogleResult {

    private String formatted_address;

    private List<AddressComponentsResult> address_components;

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public List<AddressComponentsResult> getAddress_components() {
        return address_components;
    }

    public void setAddress_components(List<AddressComponentsResult> address_components) {
        this.address_components = address_components;
    }

}