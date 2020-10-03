package components.mainScene.body.makeOrder.checkOut.discount.orderSummery.summeryHolder.summeryOrderCost;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SummeryOrderCostController {

    @FXML
    private Label totalItemsCostLabel;

    @FXML
    private Label totalItemsCostValue;

    @FXML
    private Label totalShippingCostLabel;

    @FXML
    private Label totalShippingCostValue;

    @FXML
    private Label totalOrderCostLabel;

    @FXML
    private Label totalOrderCostValue;

    public Label getTotalItemsCostValue() {
        return totalItemsCostValue;
    }

    public Label getTotalShippingCostValue() {
        return totalShippingCostValue;
    }

    public Label getTotalOrderCostValue() {
        return totalOrderCostValue;
    }
}
