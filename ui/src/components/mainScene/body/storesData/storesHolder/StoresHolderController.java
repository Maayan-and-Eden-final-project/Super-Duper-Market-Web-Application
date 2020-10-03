package components.mainScene.body.storesData.storesHolder;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class StoresHolderController {
    @FXML
    private ScrollPane storesHolderPane;

    @FXML
    private VBox storesHolderVbox;

    public VBox getStoresHolderVbox() {
        return storesHolderVbox;
    }
}
