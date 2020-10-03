package components.mainScene.body.customersHolder;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class CustomersHolderController {
    @FXML
    private VBox customersHolderVbox;

    public VBox getCustomersHolderVbox() {
        return customersHolderVbox;
    }

    public void setCustomersHolderVbox(VBox customersHolderVbox) {
        this.customersHolderVbox = customersHolderVbox;
    }
}
