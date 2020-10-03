package components.mainScene.body.makeOrder.checkOut.discount.orderSummery.summeryItem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SummeryItemController {
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
    private Label amountLabel;

    @FXML
    private Label amountValue;

    @FXML
    private Label pricePerPieceLabel;

    @FXML
    private Label pricePerPieceValue;

    @FXML
    private Label totalItemPriceLabel;

    @FXML
    private Label totalItemPriceValue;

    @FXML
    private Label isFromDiscountLabel;

    @FXML
    private Label isFromDiscountValue;

    public Label getItemIdValue() {
        return itemIdValue;
    }

    public Label getItemNameValue() {
        return itemNameValue;
    }

    public Label getPurchaseCategoryValue() {
        return purchaseCategoryValue;
    }

    public Label getAmountValue() {
        return amountValue;
    }

    public Label getPricePerPieceValue() {
        return pricePerPieceValue;
    }

    public Label getTotalItemPriceValue() {
        return totalItemPriceValue;
    }

    public Label getIsFromDiscountValue() {
        return isFromDiscountValue;
    }
}
