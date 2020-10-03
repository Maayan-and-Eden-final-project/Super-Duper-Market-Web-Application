package components.mainScene.body.addItem.storeOption;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class StoreOptionController {

    @FXML
    private HBox storeHbox;

    @FXML
    private CheckBox storeCheckBox;

    @FXML
    private TextField priceTextField;

    public CheckBox getStoreCheckBox() {
        return storeCheckBox;
    }

    public TextField getPriceTextField() {
        return priceTextField;
    }
}
