package components.mainScene.body.makeOrder.checkOut.discount.orderSummery.summeryHolder;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SummeryHolderController {
    @FXML
    private VBox dynamicSummeryVbox;

    public VBox getDynamicSummeryVbox() {
        return dynamicSummeryVbox;
    }

    @FXML
    private Label orderSummeryHeaderLabel;

    public Label getOrderSummeryHeaderLabel() {
        return orderSummeryHeaderLabel;
    }
}
