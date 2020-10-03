package components.mainScene.body.makeOrder.checkOut.discount.orderSummery.summeryStore;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class SummeryStoreController {

    @FXML
    private VBox summeryStoreVbox;

    @FXML
    private Label storeIdLabel;

    @FXML
    private Label storeIdValue;

    @FXML
    private Label storeName;

    @FXML
    private Label storeNameValue;

    @FXML
    private Label ppkLabel;

    @FXML
    private Label ppkValue;

    @FXML
    private Label distanceFromUserLabel;

    @FXML
    private Label distanceFromUserValue;

    @FXML
    private Label totalShippingCostLabel;

    @FXML
    private Label totalShippingCostValue;

    @FXML
    private FlowPane storeItemFlowPane;

    public VBox getSummeryStoreVbox() {
        return summeryStoreVbox;
    }

    public Label getStoreIdValue() {
        return storeIdValue;
    }

    public Label getStoreNameValue() {
        return storeNameValue;
    }

    public Label getPpkValue() {
        return ppkValue;
    }

    public Label getDistanceFromUserValue() {
        return distanceFromUserValue;
    }

    public Label getTotalShippingCostValue() {
        return totalShippingCostValue;
    }

    public FlowPane getStoreItemFlowPane() {
        return storeItemFlowPane;
    }
}
