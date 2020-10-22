package systemInfoContainers.webContainers;

import java.util.ArrayList;
import java.util.List;

public class SingleStoreOrdersContainer {
    private Integer storeId;
    private String storeName;
    private List<SingleShopOwnerOrderContainer> orders;

    public SingleStoreOrdersContainer() {
        this.orders = new ArrayList<>();
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

    public List<SingleShopOwnerOrderContainer> getOrders() {
        return orders;
    }

    public void setOrders(List<SingleShopOwnerOrderContainer> orders) {
        this.orders = orders;
    }
}
