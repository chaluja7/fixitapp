package cz.cvut.jee.dao.wrappers;

/**
 * Wrapper to wrap statistic result from invalidIncident table.
 *
 * @author jakubchalupa
 * @since 29.01.15
 */
public class InvalidIncidentStatisticItem {

    private String regionName;

    private long numberOfItems;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public long getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(long numberOfItems) {
        this.numberOfItems = numberOfItems;
    }
}
