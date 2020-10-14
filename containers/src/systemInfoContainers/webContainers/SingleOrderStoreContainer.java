package systemInfoContainers.webContainers;

public class SingleOrderStoreContainer {
    private Integer storeId;
    private String storeName;

    public SingleOrderStoreContainer(Integer storeId, String storeName) {
        this.storeId = storeId;
        this.storeName = storeName;
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
}
