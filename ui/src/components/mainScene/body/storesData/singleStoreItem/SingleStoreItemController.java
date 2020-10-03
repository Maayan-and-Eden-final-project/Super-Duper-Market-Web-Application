package components.mainScene.body.storesData.singleStoreItem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class SingleStoreItemController {
    @FXML
    private ScrollPane singleStoreItemPane;

    @FXML
    private GridPane singleStoreItemGrid;

    @FXML
    private Label idLabel;

    @FXML
    private Label idValue;

    @FXML
    private Label nameLabel;

    @FXML
    private Label nameValue;

    @FXML
    private Label purchaseCategoryLabel;

    @FXML
    private Label purchaseCategoryValue;

    @FXML
    private Label priceLabel;

    @FXML
    private Label priceValue;

    @FXML
    private Label amountPurchasedLabel;

    @FXML
    private Label amountPurchasedValue;

    public Label getIdValue() {
        return idValue;
    }

    public Label getNameValue() {
        return nameValue;
    }

    public Label getPurchaseCategoryValue() {
        return purchaseCategoryValue;
    }

    public Label getPriceValue() {
        return priceValue;
    }

    public Label getAmountPurchasedValue() {
        return amountPurchasedValue;
    }
}
