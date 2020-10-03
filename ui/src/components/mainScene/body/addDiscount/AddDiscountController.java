package components.mainScene.body.addDiscount;

import components.mainScene.app.AppController;
import components.mainScene.body.addDiscount.addOffer.AddOfferController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sdm.enums.Operator;
import sdm.enums.PurchaseCategory;
import sdm.sdmElements.Offer;
import sdm.sdmElements.ThenYouGet;
import systemInfoContainers.AddDiscountContainer;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddDiscountController {

    @FXML
    private ComboBox<String> chooseStoreComboBox;

    @FXML
    private TextField discountNameTextField;

    @FXML
    private HBox ifYouBuyHbox;

    @FXML
    private HBox thenYouGetHbox;

    @FXML
    private VBox  offerVbox;

    @FXML
    private HBox buttonsHbox;

    @FXML
    private Label ifYouBuyLable;

    @FXML
    private ComboBox<String> chooseItemComboBox;

    @FXML
    private TextField amountTextField;

    @FXML
    private Label thenYouGetLabel;

    @FXML
    private ComboBox<String> operatorComboBox;

    @FXML
    private Button plusOfferButton;

    @FXML
    private Button addDiscountButton;

    @FXML
    private HBox offerComponent;

    @FXML
    private Label offerNumberValue;

    @FXML
    private Label ifYouBuyMessage;

    @FXML
    private AddOfferController offerComponentController;

    private AppController appController;
    private List<AddOfferController> offersList;
    private AddDiscountContainer discountContainer;
    private boolean isIrrelevant = false;
    private Integer storeId;

    @FXML
    void initialize() {
        this.offersList = new ArrayList<>();
        this.discountContainer = new AddDiscountContainer();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public VBox getOfferVbox() {
        return offerVbox;
    }

    public ComboBox<String> getChooseStoreComboBox() {
        return chooseStoreComboBox;
    }

    @FXML
    void operatorAction(ActionEvent event) {
        if(this.operatorComboBox.getSelectionModel().getSelectedItem() != null) {
            buttonsHbox.setDisable(false);
            offerVbox.setDisable(false);
            operatorComboBox.setDisable(true);
            if(!this.operatorComboBox.getSelectionModel().getSelectedItem().equals("IRRELEVANT")) {
                if(this.operatorComboBox.getSelectionModel().getSelectedItem().equals("ONE-OF")) {
                    discountContainer.getThenYouGet().setOperator(Operator.ONE_OF);
                } else {
                    discountContainer.getThenYouGet().setOperator(Operator.ALL_OR_NOTHING);
                }
                plusOfferButton.setDisable(false);
            } else {
                discountContainer.getThenYouGet().setOperator(Operator.IRRELEVANT);
                plusOfferButton.setDisable(false);
                isIrrelevant = true;
            }
        }
    }

    private void validateDiscount() throws Exception {
        //validate
        thenYouGetHbox.setDisable(true);
        offerVbox.setDisable(true);
        plusOfferButton.setDisable(true);

        if (this.storeId != null) {
            discountContainer.setStoreId(storeId);
            this.chooseStoreComboBox.getStyleClass().remove("not-selected");
        } else {
            this.chooseStoreComboBox.getStyleClass().add("not-selected");
            throw new Exception("Not All Details Selected");
        }

        if (!this.discountNameTextField.getText().isEmpty()) {
            discountContainer.setDiscountName(this.discountNameTextField.getText());
            this.discountNameTextField.getStyleClass().remove("not-selected");
        } else {
            this.discountNameTextField.getStyleClass().add("not-selected");
            throw new Exception("Not All Details Selected");
        }

        if (this.chooseItemComboBox.getSelectionModel().getSelectedItem() != null) {
            String itemId = chooseItemComboBox.getSelectionModel().getSelectedItem().split(":")[1].split(",")[0];
            itemId = itemId.trim();
            discountContainer.getIfYouBuy().setItemId(Integer.parseInt(itemId));
            this.chooseItemComboBox.getStyleClass().remove("not-selected");

        } else {
            this.chooseItemComboBox.getStyleClass().add("not-selected");
            throw new Exception("Not All Details Selected");
        }

        if (!this.amountTextField.getText().isEmpty()
                && this.amountTextField.getText().matches("[0-9]+")) {
            discountContainer.getIfYouBuy().setQuantity(Double.parseDouble(this.amountTextField.getText()));
            amountTextField.getStyleClass().remove("not-selected");
            ifYouBuyMessage.setVisible(false);
        } else {
            amountTextField.getStyleClass().add("not-selected");
            ifYouBuyMessage.setText("Must be a whole number");
            ifYouBuyMessage.setTextFill(Color.RED);
            ifYouBuyMessage.setVisible(true);
            throw new Exception("Not All Details Selected");
        }
    }

    private void clearPreviousDiscountScreen() {
        offerComponentController.getForAdditionalMessage().setVisible(false);
        offerComponentController.getAmountMessage().setVisible(false);
        offerNumberValue.setVisible(false);
        chooseStoreComboBox.getSelectionModel().clearSelection();
        operatorComboBox.getSelectionModel().clearSelection();
        chooseItemComboBox.getSelectionModel().clearSelection();
        amountTextField.clear();
        discountNameTextField.clear();
        chooseStoreComboBox.setDisable(false);
        ifYouBuyHbox.setDisable(true);
        buttonsHbox.setDisable(true);
    }

    @FXML
    void addDiscountAction(ActionEvent event) {
        try {
            validateDiscount();
            clearPreviousDiscountScreen();
            appController.addNewDiscount(discountContainer);
            offersList.clear();
            discountContainer = new AddDiscountContainer();

        } catch (Exception exception) {
            // exception.printStackTrace();
        }
    }

    @FXML
    void chooseStoreAction(ActionEvent event) {
        if (this.chooseStoreComboBox.getSelectionModel().getSelectedItem() != null) {
            String storeId = chooseStoreComboBox.getSelectionModel().getSelectedItem().split(":")[1].split(",")[0];
            storeId = storeId.trim();
            this.storeId = (Integer.parseInt(storeId));

            appController.loadStoreItemsForDiscount(this.storeId, chooseItemComboBox, offerComponentController.getItemIdComboBox(), operatorComboBox);
            ifYouBuyHbox.setDisable(false);
            discountNameTextField.setDisable(false);
            thenYouGetHbox.setDisable(false);
            operatorComboBox.setDisable(false);
            //buttonsHbox.setDisable(false);
            //offerVbox.setDisable(false);
        }
    }

    @FXML
    void plusOfferAction(ActionEvent event) {
        if (offerComponentController.getItemIdComboBox().getSelectionModel().getSelectedItem() != null
                && offerComponentController.getOfferAmount() != null
                && offerComponentController.getForAdditionalAmount() != null) {

            Offer offer = new Offer();
            offer.setForAdditional(offerComponentController.getForAdditionalAmount());
            offer.setQuantity(offerComponentController.getOfferAmount());
            offer.setItemId(offerComponentController.getItemId());
            discountContainer.getThenYouGet().getOffers().add(offer); //maybe null exception
            offersList.add(offerComponentController);
            offerNumberValue.setText("Offer Number " + offersList.size() + " Successfully Added");

            //clear selections
            offerComponentController.getItemIdComboBox().getSelectionModel().clearSelection();
            offerComponentController.getForAdditional().clear();
            offerComponentController.getAmountTextField().clear();
            offerComponentController.setForAdditionalAmount(null);
            offerComponentController.setOfferAmount(null);
            addDiscountButton.setDisable(false);
            operatorComboBox.setDisable(true);

            if(isIrrelevant) {
                plusOfferButton.setDisable(true);
                offerVbox.setDisable(true);
                isIrrelevant = false;
            }

        } else {
            if (offerComponentController.getItemIdComboBox().getSelectionModel().getSelectedItem()  == null) {
                offerComponentController.getItemIdComboBox().getStyleClass().add("not-selected");
            }
            if (offerComponentController.getAmountTextField().getText().isEmpty()) {
                offerComponentController.getAmountTextField().getStyleClass().add("not-selected");
            }
            if (offerComponentController.getForAdditional().getText().isEmpty()) {
                offerComponentController.getForAdditional().getStyleClass().add("not-selected");
            }
        }

    }
}
