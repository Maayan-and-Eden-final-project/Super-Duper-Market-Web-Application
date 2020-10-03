package components.mainScene.body.makeOrder.checkOut.dynamicMinimalCart.dynamicStoreHolder;

import components.mainScene.body.makeOrder.checkOut.discount.DiscountsController;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class DynamicStoreHolderController {

    @FXML
    private FlowPane dynamicStoresHolderFlow;

    @FXML
    private  VBox discountsComponent;

    @FXML
    private DiscountsController discountsComponentController;

    public DiscountsController getDiscountsComponentController() {
        return discountsComponentController;
    }

    public FlowPane getDynamicStoresHolderFlow() {
        return dynamicStoresHolderFlow;
    }
}
