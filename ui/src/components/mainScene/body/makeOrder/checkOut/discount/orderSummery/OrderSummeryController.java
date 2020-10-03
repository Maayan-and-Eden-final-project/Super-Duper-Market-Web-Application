package components.mainScene.body.makeOrder.checkOut.discount.orderSummery;

import components.welcomeScene.UiAdapter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import systemEngine.DesktopEngine;
import systemInfoContainers.OrderSummeryContainer;
import systemInfoContainers.ProgressDynamicOrderContainer;
import systemInfoContainers.ProgressStaticOrderContainer;

import java.util.List;
import java.util.Map;

public class OrderSummeryController {
    @FXML
    private Button nextButton;
    private UiAdapter uiAdapter;
    private ProgressDynamicOrderContainer progressDynamicOrderInfo;
    private ProgressStaticOrderContainer progressStaticOrderContainer;
    private Map<Integer, List<Integer>> storeIdToItemIDList;
    private DesktopEngine engine;

    public void setEngine(DesktopEngine engine) {
        this.engine = engine;
    }

    public void setStoreIdToItemIDList(Map<Integer, List<Integer>> storeIdToItemIDList) {
        this.storeIdToItemIDList = storeIdToItemIDList;
    }

    public void setProgressStaticOrderContainer(ProgressStaticOrderContainer progressStaticOrderContainer) {
        this.progressStaticOrderContainer = progressStaticOrderContainer;
    }

    public void setUiAdapter(UiAdapter uiAdapter) {
        this.uiAdapter = uiAdapter;
    }

    public void setProgressDynamicOrderInfo(ProgressDynamicOrderContainer progressDynamicOrderInfo) {
        this.progressDynamicOrderInfo = progressDynamicOrderInfo;
    }


    @FXML
    void orderSummeryAction(ActionEvent event) {
        uiAdapter.clearBody();
        OrderSummeryContainer orderSummery;
        if (this.progressDynamicOrderInfo != null) {
            orderSummery = engine.getOrderSummery(progressDynamicOrderInfo, storeIdToItemIDList);
        } else {
            orderSummery = engine.getOrderSummery(progressStaticOrderContainer, storeIdToItemIDList);
        }
        uiAdapter.displayOrderSummery(orderSummery, engine);
    }
}
