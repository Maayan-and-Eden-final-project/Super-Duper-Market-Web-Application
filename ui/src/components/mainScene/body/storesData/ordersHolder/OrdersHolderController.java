package components.mainScene.body.storesData.ordersHolder;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class OrdersHolderController {

    @FXML
    private VBox storeOrdersHolderVbox;

    @FXML
    private Label ordersInStoreLabel;

    @FXML
    private FlowPane storesOrderPane;

    public FlowPane getStoresOrderPane() {
        return storesOrderPane;
    }
}
