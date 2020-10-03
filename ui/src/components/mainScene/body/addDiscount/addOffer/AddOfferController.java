package components.mainScene.body.addDiscount.addOffer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class AddOfferController {

    @FXML
    private Label offerLabel;

    @FXML
    private ComboBox<String> itemIdComboBox;

    @FXML
    private TextField amountTextField;

    @FXML
    private TextField forAdditional;

    @FXML
    private Label forAdditionalMessage;

    @FXML
    private Label amountMessage;

    private Double offerAmount;
    private Integer forAdditionalAmount;
    private Integer itemId;


    public Double getOfferAmount() {
        return offerAmount;
    }

    public Integer getForAdditionalAmount() {
        return forAdditionalAmount;
    }

    public Label getForAdditionalMessage() {
        return forAdditionalMessage;
    }

    public void setForAdditionalMessage(Label forAdditionalMessage) {
        this.forAdditionalMessage = forAdditionalMessage;
    }

    public Label getAmountMessage() {
        return amountMessage;
    }

    public void setAmountMessage(Label amountMessage) {
        this.amountMessage = amountMessage;
    }

    public TextField getAmountTextField() {
        return amountTextField;
    }

    public TextField getForAdditional() {
        return forAdditional;
    }

    public ComboBox<String> getItemIdComboBox() {
        return itemIdComboBox;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setOfferAmount(Double offerAmount) {
        this.offerAmount = offerAmount;
    }

    public void setForAdditionalAmount(Integer forAdditionalAmount) {
        this.forAdditionalAmount = forAdditionalAmount;
    }

    @FXML
    void itemIdComboBoxAction(ActionEvent event) {
        if(itemIdComboBox.getSelectionModel().getSelectedItem() != null) {
            String itemId = itemIdComboBox.getSelectionModel().getSelectedItem().split(":")[1].split(",")[0];
            itemId = itemId.trim();
            this.itemId = Integer.parseInt(itemId);
            itemIdComboBox.getStyleClass().removeAll("not-selected");
        }
    }

    @FXML
    void forAdditionalAction(MouseEvent event) {
        if (!forAdditional.getText().isEmpty()
                && forAdditional.getText().matches("[0-9]+")) {
            forAdditionalMessage.setVisible(false);
            forAdditionalAmount = (Integer.parseInt(forAdditional.getText()));
            forAdditional.getStyleClass().remove("not-selected");
        } else {
            forAdditionalAmount = null;
            forAdditionalMessage.setText("Must be a whole number");
            forAdditionalMessage.setTextFill(Color.RED);
            forAdditionalMessage.setVisible(true);
        }
    }

    @FXML
    void offerAmountAction(MouseEvent event) {
        if (!amountTextField.getText().isEmpty()
                && amountTextField.getText().matches("[0-9]+")) {
            amountMessage.setVisible(false);
            offerAmount = (Double.parseDouble(amountTextField.getText()));
            amountTextField.getStyleClass().remove("not-selected");
        } else {
            offerAmount = null;
            amountMessage.setText("Must be a whole number");
            amountMessage.setTextFill(Color.RED);
            amountMessage.setVisible(true);
        }
    }
}
