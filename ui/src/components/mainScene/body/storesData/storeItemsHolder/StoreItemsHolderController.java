package components.mainScene.body.storesData.storeItemsHolder;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class StoreItemsHolderController {

    @FXML
    private VBox storeItemsHolderVbox;

    @FXML
    private Label itemsInStoreLabel;

    @FXML
    private FlowPane storesItemPane;


    public FlowPane getStoresItemPane() {
        return storesItemPane;
    }
}
