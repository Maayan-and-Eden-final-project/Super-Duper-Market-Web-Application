package components.mainScene.body.map;

import components.mainScene.app.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

public class MapCustomerController {

    @FXML
    private Button customerButton;

    private Integer customerId;
    private String customerName;
    private Integer numOfOrders;
    private AppController appController;
    private Stage stage;

    public Button getCustomerButton() {
        return customerButton;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setNumOfOrders(Integer numOfOrders) {
        this.numOfOrders = numOfOrders;
    }

}
