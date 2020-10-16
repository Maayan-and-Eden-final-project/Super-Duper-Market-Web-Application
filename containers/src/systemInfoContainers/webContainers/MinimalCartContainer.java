package systemInfoContainers.webContainers;

import systemInfoContainers.ProgressOrderItem;

import java.util.List;
import java.util.Map;

public class MinimalCartContainer {
    List<SingleDynamicStoreContainer> dynamicStores;
    Map<Integer, List<ProgressOrderItem>> storeIdToItemsList;

    public MinimalCartContainer(List<SingleDynamicStoreContainer> dynamicStores, Map<Integer, List<ProgressOrderItem>> storeIdToItemsList) {
        this.dynamicStores = dynamicStores;
        this.storeIdToItemsList = storeIdToItemsList;
    }
}
