package components.mainScene.body.makeOrder.checkOut.dynamicMinimalCart.singleDynamicStore;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SingleDynamicStoreController {
    @FXML
    private Label storeIdLabel;

    @FXML
    private Label storeIdValue;

    @FXML
    private Label storeNameLabel;

    @FXML
    private Label storeNameValue;

    @FXML
    private Label locationLabel;

    @FXML
    private Label locationValue;

    @FXML
    private Label distanceLabel;

    @FXML
    private Label distanceValue;

    @FXML
    private Label ppkLabel;

    @FXML
    private Label ppkValue;

    @FXML
    private Label shippingCostLabel;

    @FXML
    private Label shippingCostValue;

    @FXML
    private Label numberOfItemsLabel;

    @FXML
    private Label numberOfItemsValue;

    @FXML
    private Label totalItemsCostLabel;

    @FXML
    private Label totalItemsCostValue;

    public Label getStoreIdValue() {
        return storeIdValue;
    }

    public Label getStoreNameValue() {
        return storeNameValue;
    }

    public Label getLocationValue() {
        return locationValue;
    }

    public Label getDistanceValue() {
        return distanceValue;
    }

    public Label getPpkValue() {
        return ppkValue;
    }

    public Label getShippingCostValue() {
        return shippingCostValue;
    }

    public Label getNumberOfItemsValue() {
        return numberOfItemsValue;
    }

    public Label getTotalItemsCostValue() {
        return totalItemsCostValue;
    }
}
