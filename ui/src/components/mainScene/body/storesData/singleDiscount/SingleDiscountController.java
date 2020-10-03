package components.mainScene.body.storesData.singleDiscount;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SingleDiscountController {

    @FXML
    private VBox discountVbox;

    @FXML
    private Label discountNameLabel;

    @FXML
    private Label discountNameValue;

    @FXML
    private Label ifYouBuyLabel;

    @FXML
    private Label itemId;

    @FXML
    private Label itemIdValue;

    @FXML
    private Label amountLabel;

    @FXML
    private Label amountValue;

    @FXML
    private HBox offersHbox;

    @FXML
    private Label thenYouGetLabel;

    @FXML
    private Label operatorLabel;

    public Label getOperatorLabel() {
        return operatorLabel;
    }

    public VBox getDiscountVbox() {
        return discountVbox;
    }

    public Label getDiscountNameValue() {
        return discountNameValue;
    }

    public Label getItemIdValue() {
        return itemIdValue;
    }

    public Label getAmountValue() {
        return amountValue;
    }
}
