package components.mainScene.body.storesData.singleOrder;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class SingleOrderController {
    @FXML
    private ScrollPane singleOrderPane;

    @FXML
    private GridPane singleOrderGrid;

    @FXML
    private Label dateLabel;

    @FXML
    private Label dateValue;

    @FXML
    private Label totalItemsLabel;

    @FXML
    private Label totalItemsValue;

    @FXML
    private Label totalItemsCostLabel;

    @FXML
    private Label totalItemsCostValue;

    @FXML
    private Label totalDeliveryCostLabel;

    @FXML
    private Label totalDeliveryCostValue;

    @FXML
    private Label totalCostLabel;

    @FXML
    private Label totalCostValue;

    @FXML
    private Label dynamicOrderIdLabel;

    @FXML
    private Label dynamicOrderIdValue;

    public Label getDateValue() {
        return dateValue;
    }

    public Label getTotalItemsValue() {
        return totalItemsValue;
    }

    public Label getTotalItemsCostValue() {
        return totalItemsCostValue;
    }

    public Label getTotalDeliveryCostValue() {
        return totalDeliveryCostValue;
    }

    public Label getTotalCostValue() {
        return totalCostValue;
    }

    public Label getDynamicOrderIdValue() {
        return dynamicOrderIdValue;
    }
}
