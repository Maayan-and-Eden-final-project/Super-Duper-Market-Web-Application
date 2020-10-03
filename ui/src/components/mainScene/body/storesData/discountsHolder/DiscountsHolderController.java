package components.mainScene.body.storesData.discountsHolder;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DiscountsHolderController {

    @FXML
    private VBox discountsHolderVbox;

    @FXML
    private Label discountsInStoreLabel;

    public VBox getDiscountsHolderVbox() {
        return discountsHolderVbox;
    }
}
