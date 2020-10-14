package systemInfoContainers.webContainers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleAreaOptionContainer {
    private String areaName;
    private List<SingleOrderStoreContainer> stores;

    public SingleAreaOptionContainer() {
        this.stores = new ArrayList<>();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<SingleOrderStoreContainer> getStores() {
        return stores;
    }

    public void setStores(List<SingleOrderStoreContainer> stores) {
        this.stores = stores;
    }
}
