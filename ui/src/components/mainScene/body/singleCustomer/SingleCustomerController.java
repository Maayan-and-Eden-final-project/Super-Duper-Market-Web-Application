package components.mainScene.body.singleCustomer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SingleCustomerController {
    @FXML
    private Label idLabel;

    @FXML
    private Label idValue;

    @FXML
    private Label nameLabel;

    @FXML
    private Label nameValue;

    @FXML
    private Label locationLabel;

    @FXML
    private Label locationValue;

    @FXML
    private Label numberOfOrdersLabel;

    @FXML
    private Label numberOfOrdersValue;

    @FXML
    private Label averageOrdersCostLabel;

    @FXML
    private Label averageOrdersCostValue;

    @FXML
    private Label averageShipmentCostLabel;

    @FXML
    private Label averageShipmentCostValue;

    public Label getIdValue() {
        return idValue;
    }

    public void setIdValue(Label idValue) {
        this.idValue = idValue;
    }

    public Label getNameValue() {
        return nameValue;
    }

    public void setNameValue(Label nameValue) {
        this.nameValue = nameValue;
    }

    public Label getLocationValue() {
        return locationValue;
    }

    public void setLocationValue(Label locationValue) {
        this.locationValue = locationValue;
    }

    public Label getNumberOfOrdersValue() {
        return numberOfOrdersValue;
    }

    public void setNumberOfOrdersValue(Label numberOfOrdersValue) {
        this.numberOfOrdersValue = numberOfOrdersValue;
    }

    public Label getAverageOrdersCostValue() {
        return averageOrdersCostValue;
    }

    public void setAverageOrdersCostValue(Label averageOrdersCostValue) {
        this.averageOrdersCostValue = averageOrdersCostValue;
    }

    public Label getAverageShipmentCostValue() {
        return averageShipmentCostValue;
    }

    public void setAverageShipmentCostValue(Label averageShipmentCostValue) {
        this.averageShipmentCostValue = averageShipmentCostValue;
    }
}
