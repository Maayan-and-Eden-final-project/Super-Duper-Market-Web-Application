package components.mainScene.body.storesData.singleStore;

import components.mainScene.body.storesData.discountsHolder.DiscountsHolderController;
import components.mainScene.body.storesData.ordersHolder.OrdersHolderController;
import components.mainScene.body.storesData.storeItemsHolder.StoreItemsHolderController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class SingleStoreController {
    @FXML
    private StoreItemsHolderController storeItemsComponentController;

    @FXML
    private VBox storeItemsComponent;

    @FXML
    private OrdersHolderController storeOrdersComponentController;

    @FXML
    private VBox storeOrdersComponent;

    @FXML
    private DiscountsHolderController storeDiscountsComponentController;

    @FXML
    private VBox storeDiscountsComponent;

    @FXML
    private ScrollPane storeScroll;

    @FXML
    private VBox storeDataVbox;

    @FXML
    private GridPane storeGrid;

    @FXML
    private Label idLabel;

    @FXML
    private Label idValue;

    @FXML
    private Label nameLabel;

    @FXML
    private Label nameValue;

    @FXML
    private Label ppkLabel;

    @FXML
    private Label ppkValue;

    @FXML
    private Label totalDeliveryPaymentsLabel;

    @FXML
    private Label totalDeliveryPaymentsValue;

    public StoreItemsHolderController getStoreItemsComponentController() {
        return storeItemsComponentController;
    }

    public OrdersHolderController getStoreOrdersComponentController() {
        return storeOrdersComponentController;
    }

    public DiscountsHolderController getStoreDiscountsComponentController() {
        return storeDiscountsComponentController;
    }

    public Label getIdValue() {
        return idValue;
    }

    public Label getNameValue() {
        return nameValue;
    }

    public Label getPpkValue() {
        return ppkValue;
    }

    public Label getTotalDeliveryPaymentsValue() {
        return totalDeliveryPaymentsValue;
    }
}
