package components.mainScene.body.map;

import components.mainScene.app.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PopupControl;
import javafx.scene.control.Tooltip;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class MapStoreController {

    @FXML
    private Button shopButton;

    private Integer storeId;
    private String storeName;
    private Integer ppk;
    private Integer numOfOrders;
    private Stage stage;
    private AppController appController;

    public Button getShopButton() {
        return shopButton;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setPpk(Integer ppk) {
        this.ppk = ppk;
    }

    public void setNumOfOrders(Integer numOfOrders) {
        this.numOfOrders = numOfOrders;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
