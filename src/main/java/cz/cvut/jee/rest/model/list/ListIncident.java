package cz.cvut.jee.rest.model.list;

import cz.cvut.jee.rest.model.IncidentModel;

/**
 * List incident (for datatables)
 *
 * @author jakubchalupa
 * @since 01.01.15
 */
public class ListIncident extends IncidentModel {

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
