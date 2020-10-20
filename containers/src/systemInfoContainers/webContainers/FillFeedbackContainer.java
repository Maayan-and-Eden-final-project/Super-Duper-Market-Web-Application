package systemInfoContainers.webContainers;

import java.util.ArrayList;
import java.util.List;

public class FillFeedbackContainer {
    private List<StoreIdAndNameContainer> orderStores;
    private String orderDate;

    public FillFeedbackContainer() {
        this.orderStores = new ArrayList<>();
    }

    public List<StoreIdAndNameContainer> getOrderStores() {
        return orderStores;
    }

    public void setOrderStores(List<StoreIdAndNameContainer> orderStores) {
        this.orderStores = orderStores;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
