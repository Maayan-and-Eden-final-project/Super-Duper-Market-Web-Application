package components.mainScene.body.makeOrder.orderBody.itemsHolder;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class OrderItemsHolderController {

    @FXML
    private ScrollPane itemsHolderScroll;

    @FXML
    private ImageView cartImage;


    public ImageView getCartImage() {
        return cartImage;
    }

    @FXML
    private FlowPane itemsHolderFlowPane;

    public FlowPane getItemsHolderFlowPane() {
        return itemsHolderFlowPane;
    }
}
