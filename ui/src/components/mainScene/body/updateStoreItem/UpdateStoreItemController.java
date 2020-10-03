package components.mainScene.body.updateStoreItem;

import components.mainScene.app.AppController;
import exceptions.ItemIsNotSoldException;
import exceptions.SingleSellingStoreException;
import exceptions.XmlSimilarItemsIdException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UpdateStoreItemController {

    @FXML
    private ComboBox<String> chooseStoreComboBox;

    @FXML
    private ComboBox<String> chooseItemComboBox;

    @FXML
    private ComboBox<String> chooseUpdateComboBox;

    @FXML
    private Button updateButton;

    @FXML
    private TextField itemPriceTextField;

    @FXML
    private Label messageLabel;

    private Integer storeIdSelection;
    private Integer itemIdSelection;
    private AppController appController;


    @FXML
    void updateAction(ActionEvent event) {
        validate();
        messageLabel.setVisible(true);
    }

    public ComboBox<String> getChooseUpdateComboBox() {
        return chooseUpdateComboBox;
    }

    public TextField getItemPriceTextField() {
        return itemPriceTextField;
    }


    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public ComboBox<String> getChooseStoreComboBox() {
        return chooseStoreComboBox;
    }

    public ComboBox<String> getChooseItemComboBox() {
        return chooseItemComboBox;
    }


    private void validateByAction() throws Exception {
        switch (this.chooseUpdateComboBox.getSelectionModel().getSelectedItem()) {
            case "Update Item Price":
               if(this.itemPriceTextField.getText() != ""
                       && this.itemPriceTextField.getText().matches("[0-9]+")) {
                   appController.getEngine().rejectIfItemNotDefinedInStore(storeIdSelection, itemIdSelection);
                   appController.getEngine().updateItemPrice(storeIdSelection, itemIdSelection, Integer.parseInt(this.itemPriceTextField.getText()));
                    messageLabel.setText("Updated Successfully");
               } else {
                   throw new Exception("Not a whole number");
               }
                break;
            case "Delete Item From Store":
                appController.getEngine().rejectIfItemNotDefinedInStore(storeIdSelection, itemIdSelection);
                appController.getEngine().deleteStoreItem(storeIdSelection, itemIdSelection);
                messageLabel.setText("Updated Successfully");
                break;
            case "Add New Item To Store":
                appController.getEngine().rejectIfItemDefinedInStore(storeIdSelection, itemIdSelection);
                if(!this.itemPriceTextField.getText().isEmpty() && this.itemPriceTextField.getText().matches("[0-9]+")) {
                    appController.getEngine().addNewItemToStore(storeIdSelection, itemIdSelection, Integer.parseInt(this.itemPriceTextField.getText()));
                    messageLabel.setText("Updated Successfully");
                } else {
                    throw new Exception("Not a whole number");
                }
                break;
            default:
        }
    }

    private void validate() {
        try {
            if (this.chooseStoreComboBox.getSelectionModel().getSelectedItem() != null) {
                String storeId = chooseStoreComboBox.getSelectionModel().getSelectedItem().split(":")[1].split(",")[0];

                storeId = storeId.trim();
                storeIdSelection = Integer.parseInt(storeId);

            } else {
                this.chooseStoreComboBox.getStyleClass().add("not-selected");
            }

            if (this.chooseItemComboBox.getSelectionModel().getSelectedItem() != null) {
                String itemId = chooseItemComboBox.getSelectionModel().getSelectedItem().split(":")[1].split(",")[0];
                itemId = itemId.trim();
                itemIdSelection = Integer.parseInt(itemId);
            }
            validateByAction();

        } catch (Exception e) {
            messageLabel.setText(e.getMessage());
        }
    }

    @FXML
    void chooseUpdateAction(ActionEvent event) {
        if(this.chooseUpdateComboBox.getSelectionModel().getSelectedItem() != null) {
            if(this.chooseUpdateComboBox.getSelectionModel().getSelectedItem().equals("Update Item Price") ||
                    this.chooseUpdateComboBox.getSelectionModel().getSelectedItem().equals("Add New Item To Store") ) {
                itemPriceTextField.setDisable(false);
            } else {
                itemPriceTextField.setDisable(true);
            }
        }
    }
}




