package systemInfoContainers;

import java.awt.*;
import java.util.List;

public class AddNewStoreContainer {
    private Integer storeId;
    private String storeName;
    private Point storeLocation;
    private Integer ppk;
    private List<AddNewStoreItemContainer> itemsList;

    public AddNewStoreContainer(Integer storeId, String storeName, Point storeLocation, Integer ppk, List<AddNewStoreItemContainer> itemsList) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeLocation = storeLocation;
        this.ppk = ppk;
        this.itemsList = itemsList;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public Point getStoreLocation() {
        return storeLocation;
    }

    public Integer getPpk() {
        return ppk;
    }

    public List<AddNewStoreItemContainer> getItemsList() {
        return itemsList;
    }
}
