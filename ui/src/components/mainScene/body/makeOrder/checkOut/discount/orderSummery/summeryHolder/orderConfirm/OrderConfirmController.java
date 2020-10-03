package components.mainScene.body.makeOrder.checkOut.discount.orderSummery.summeryHolder.orderConfirm;

import components.welcomeScene.UiAdapter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import systemEngine.DesktopEngine;
import systemInfoContainers.OrderSummeryContainer;

public class OrderConfirmController {
    @FXML
    private Button confirmOrderButton;
    private DesktopEngine engine;
    private OrderSummeryContainer orderSummery;
    private UiAdapter uiAdapter;

    public void setUiAdapter(UiAdapter uiAdapter) {
        this.uiAdapter = uiAdapter;
    }

    public void setEngine(DesktopEngine engine) {
        this.engine = engine;
    }

    public void setOrderSummery(OrderSummeryContainer orderSummery) {
        this.orderSummery = orderSummery;
    }

    @FXML
    void confirmOrderAction(ActionEvent event) {
        uiAdapter.clearBody();
        engine.updateNewOrder(this.orderSummery, uiAdapter.getStoreId(),uiAdapter.getUserId(),uiAdapter.getOrderDate());

    }
}
