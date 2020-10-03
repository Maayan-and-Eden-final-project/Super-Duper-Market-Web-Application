package task.calcMinimalCart;

import components.welcomeScene.UiAdapter;
import javafx.application.Platform;
import javafx.concurrent.Task;
import sdm.sdmElements.Store;
import systemEngine.Connector;
import systemInfoContainers.ProgressOrderItem;
import systemInfoContainers.ProgressDynamicOrderContainer;

import java.util.*;
import java.util.stream.Collectors;

public class CalcMinimalCartTask extends Task<Boolean> {

    private Connector engine;
    private ProgressDynamicOrderContainer progressDynamicOrderInfo;
    private UiAdapter uiAdapter;

    public CalcMinimalCartTask(Connector engine, ProgressDynamicOrderContainer progressDynamicOrderInfo, UiAdapter uiAdapter) {
        this.engine = engine;
        this.progressDynamicOrderInfo = progressDynamicOrderInfo;
        this.uiAdapter = uiAdapter;
    }

    @Override
    protected Boolean call() throws Exception {
        Store orderStore;
        int progressCounter = 0;
        Map<Integer, List<Integer>> storeIdToItemIDList = new HashMap<>();
        updateProgress(progressCounter, progressDynamicOrderInfo.getItemIdToOrderItem().size());
        Thread.sleep(200);
        for (ProgressOrderItem item : progressDynamicOrderInfo.getItemIdToOrderItem().values()) {

            Map<Integer, Integer> itemPriceToStoreId = engine.getStores().entrySet().stream().filter(store -> store.getValue().getItemsIdAndPrices().containsKey(item.getItemId()) == true)
                    .collect(Collectors.toMap(e -> e.getValue().getItemsIdAndPrices().get(item.getItemId()), e -> e.getKey()));
            Integer minItemPrice = Collections.min(itemPriceToStoreId.keySet());

            orderStore = engine.getStores().get(itemPriceToStoreId.get(minItemPrice));

            if (!storeIdToItemIDList.containsKey(orderStore.getId())) {
                List<Integer> itemsList = new ArrayList<>();
                itemsList.add(item.getItemId());
                storeIdToItemIDList.put(orderStore.getId(), itemsList);
            } else {
                storeIdToItemIDList.get(orderStore.getId()).add(item.getItemId());
                storeIdToItemIDList.put(orderStore.getId(), storeIdToItemIDList.get(orderStore.getId()));
            }
            updateProgress(++progressCounter, progressDynamicOrderInfo.getItemIdToOrderItem().size());
            Thread.sleep(300);
        }

        Platform.runLater(() -> {
            uiAdapter.clearBody();
        });

        uiAdapter.loadDynamicStoresHolder(engine, storeIdToItemIDList, progressDynamicOrderInfo);
        for (Integer storeId : storeIdToItemIDList.keySet()) {
            double distance = engine.getStores().get(storeId).getLocation().distance(progressDynamicOrderInfo.getUserLocation().x, progressDynamicOrderInfo.getUserLocation().y);
            float totalItemCost = 0;
            for (Integer itemId : storeIdToItemIDList.get(storeId)) {
                totalItemCost += progressDynamicOrderInfo.getItemIdToOrderItem().get(itemId).getAmount() *
                        engine.getStores().get(storeId).getItemsIdAndPrices().get(itemId);
            }

            uiAdapter.showDynamicOrderMinCart(storeId, engine.getStores().get(storeId).getName(),
                    engine.getStores().get(storeId).getLocation(), distance,
                    engine.getStores().get(storeId).getDeliveryPPK(), engine.getStores().get(storeId).getDeliveryPPK() * distance,
                    storeIdToItemIDList.get(storeId).size(), totalItemCost);
        }
        uiAdapter.addDynamicStoresHolder();
        this.cancel();
        return false;
    }

    public void setEngine(Connector engine) {
        this.engine = engine;
    }
}

