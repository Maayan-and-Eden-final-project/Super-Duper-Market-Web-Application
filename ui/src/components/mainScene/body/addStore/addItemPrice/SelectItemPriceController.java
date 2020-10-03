package components.mainScene.body.addStore.addItemPrice;

import components.mainScene.body.addStore.AddStoreController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import systemInfoContainers.AddNewStoreItemContainer;

public class SelectItemPriceController {
    @FXML
    private VBox singleItemVbox;

    @FXML
    private Label itemIdLabel;

    @FXML
    private Label itemIdValue;

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label itemNameValue;

    @FXML
    private TextField itemPriceTextField;

    @FXML
    private Button itemPriceAddButton;

    @FXML
    private Label itemPriceMessage;


    private AddStoreController addStoreController;
    private Integer itemPrice;

    public void setAddStoreController(AddStoreController addStoreController) {
        this.addStoreController = addStoreController;
    }

    public Label getItemIdValue() {
        return itemIdValue;
    }

    public Label getItemNameValue() {
        return itemNameValue;
    }

    @FXML
    void itemPriceAddAction(ActionEvent event) {
        //validate
        validateItemPrice();
        if (itemPrice != null) {
            AddNewStoreItemContainer newItemPrice = new AddNewStoreItemContainer();
            newItemPrice.setItemId(Integer.parseInt(itemIdValue.getText()));
            newItemPrice.setItemName(itemNameValue.getText());
            newItemPrice.setItemPrice(itemPrice);
            addStoreController.getNewStoreItemContainerList().add(newItemPrice);
        }
    }

    private void validateItemPrice() {
        if (!itemPriceTextField.getText().isEmpty()
                && itemPriceTextField.getText().matches("[0-9]+")) {
            itemPriceMessage.setText("Item Added Successfully");
            itemPriceMessage.setTextFill(Color.GREEN);
            itemPrice = Integer.parseInt(itemPriceTextField.getText());
            singleItemVbox.setDisable(true);
        } else {
            itemPriceMessage.setText("Please write a number");
            itemPriceMessage.setTextFill(Color.RED);
        }
    }
}
