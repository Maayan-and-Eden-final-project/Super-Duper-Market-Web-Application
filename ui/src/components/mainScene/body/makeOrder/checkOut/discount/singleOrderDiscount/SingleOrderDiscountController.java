package components.mainScene.body.makeOrder.checkOut.discount.singleOrderDiscount;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SingleOrderDiscountController {

    @FXML
    private VBox discountVbox;

    @FXML
    private Label discountNameLabel;

    @FXML
    private Label discountNameValue;

    @FXML
    private Label youSelectedLabel;

    @FXML
    private Label youSelectedValue;

    @FXML
    private Label youCanGetLabel;

    @FXML
    private Label youCanGetValue;

    public VBox getDiscountVbox() {
        return discountVbox;
    }

    public Label getDiscountNameValue() {
        return discountNameValue;
    }

    public Label getYouSelectedValue() {
        return youSelectedValue;
    }

    public Label getYouCanGetValue() {
        return youCanGetValue;
    }
}
