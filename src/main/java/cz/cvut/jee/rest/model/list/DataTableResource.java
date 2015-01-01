package cz.cvut.jee.rest.model.list;

import java.util.List;

/**
 *  Generic resource for data table purpose. It just wraps data to aa container.
 *
 * @author jakubchalupa
 * @since 01.01.15.
 */
public class DataTableResource<T> {

    private List<T> aaData;

    public DataTableResource() {

    }

    public DataTableResource(List<T> data) {
        setAaData(data);
    }

    public List<T> getAaData() {
        return aaData;
    }

    public void setAaData(List<T> aaData) {
        this.aaData = aaData;
    }

}
