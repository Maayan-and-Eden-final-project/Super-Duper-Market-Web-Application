package systemInfoContainers.webContainers;

public class StoreIdAndNameContainer {
    private Integer storeId;
    private String storeName;

    public StoreIdAndNameContainer(Integer storeId, String storeName) {
        this.storeId = storeId;
        this.storeName = storeName;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

}
