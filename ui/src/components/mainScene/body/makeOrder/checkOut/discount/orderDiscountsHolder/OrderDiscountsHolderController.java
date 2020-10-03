package components.mainScene.body.makeOrder.checkOut.discount.orderDiscountsHolder;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

public class OrderDiscountsHolderController {
    @FXML
    private ScrollPane discountsHolderScroll;

    @FXML
    private FlowPane discountsHolderFlowPane;

    public FlowPane getDiscountsHolderFlowPane() {
        return discountsHolderFlowPane;
    }
}
