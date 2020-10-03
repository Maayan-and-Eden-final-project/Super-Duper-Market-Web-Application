package components.mainScene.body.storesData.singleOffer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SingleOfferController {
    @FXML
    private Label itemIdLabel;

    @FXML
    private Label itemIdValue;

    @FXML
    private Label amountLabel;

    @FXML
    private Label amountValue;

    @FXML
    private Label forAdditionalLabel;

    @FXML
    private Label forAdditionalValue;

    public Label getItemIdValue() {
        return itemIdValue;
    }

    public Label getAmountValue() {
        return amountValue;
    }

    public Label getForAdditionalValue() {
        return forAdditionalValue;
    }
}
