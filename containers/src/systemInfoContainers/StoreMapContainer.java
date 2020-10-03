package systemInfoContainers;

import java.awt.*;

public class StoreMapContainer implements Mappable {
    private Integer storeId;
    private String storeName;
    private Integer ppk;
    private Integer numOfOrders;
    private Point location;

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getPpk() {
        return ppk;
    }

    public void setPpk(Integer ppk) {
        this.ppk = ppk;
    }

    public Integer getNumOfOrders() {
        return numOfOrders;
    }

    public void setNumOfOrders(Integer numOfOrders) {
        this.numOfOrders = numOfOrders;
    }
}
