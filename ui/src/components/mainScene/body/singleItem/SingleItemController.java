package components.mainScene.body.singleItem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SingleItemController {

    @FXML
    private Label itemIdLabel;

    @FXML
    private Label itemIdValue;

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label itemNameValue;

    @FXML
    private Label purchaseCategoryLabel;

    @FXML
    private Label purchaseCategoryValue;

    @FXML
    private Label sellingStoresLabel;

    @FXML
    private Label sellingStoresValue;

    @FXML
    private Label averagePriceLabel;

    @FXML
    private Label averagePriceValue;

    @FXML
    private Label amountSoldLabel;

    @FXML
    private Label amountSoldValue;

    public Label getItemIdValue() {
        return itemIdValue;
    }

    public Label getItemNameValue() {
        return itemNameValue;
    }

    public Label getPurchaseCategoryValue() {
        return purchaseCategoryValue;
    }

    public Label getSellingStoresValue() {
        return sellingStoresValue;
    }

    public Label getAveragePriceValue() {
        return averagePriceValue;
    }

    public Label getAmountSoldValue() {
        return amountSoldValue;
    }
}
