package components.mainScene.body.addStore;

import components.mainScene.app.AppController;
import components.mainScene.body.addStore.addItemPrice.SelectItemPriceController;
import exceptions.XmlSimilarStoresIdException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import systemInfoContainers.AddNewStoreContainer;
import systemInfoContainers.AddNewStoreItemContainer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AddStoreController {

    @FXML
    private HBox storeIdHbox;

    @FXML
    private HBox storeNameHbox;

    @FXML
    private HBox storeLocationHbox;

    @FXML
    private HBox storePpkHbox;

    @FXML
    private Label storeIdLabel;

    @FXML
    private TextField storeIdTextField;

    @FXML
    private Label storeIdMessage;

    @FXML
    private Label storeNameLabel;

    @FXML
    private TextField storeNameTextField;

    @FXML
    private Label storeNameMessge;

    @FXML
    private Label storeLocationLabel;

    @FXML
    private TextField storeLocationTextField;

    @FXML
    private Label storeLocationMessage;

    @FXML
    private Label storePpkLabel;

    @FXML
    private TextField storePpkTextField;

    @FXML
    private Label storePpkMessage;

    @FXML
    private Button itemsPriceButton;

    @FXML
    private FlowPane itemsFlowPane;

    @FXML
    private HBox createStoreHbox;

    @FXML
    private Button createStoreButton;

    @FXML
    private Label createStoreButtonMessage;

    public FlowPane getItemsFlowPane() {
        return itemsFlowPane;
    }

    private AppController appController;
    private Integer storeId;
    private String storeName;
    private Point storeLocation;
    private Integer ppk;
    private List<AddNewStoreItemContainer> newStoreItemContainerList;

    public List<AddNewStoreItemContainer> getNewStoreItemContainerList() {
        return newStoreItemContainerList;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    void initialize() {
        newStoreItemContainerList = new ArrayList<>();
    }

    @FXML
    void createStoreButtonAction(ActionEvent event) {
        if(newStoreItemContainerList.size() == 0 ) {
            createStoreButtonMessage.setTextFill(Color.RED);
            createStoreButtonMessage.setText("Please add at least one item");
        } else {
            createStoreButtonMessage.setTextFill(Color.GREEN);
            createStoreButtonMessage.setText(String.format("Store Added Successfully, go to 'Show Stores' %nand see it%n"));
            itemsFlowPane.setVisible(false);

            AddNewStoreContainer newStore = new AddNewStoreContainer(storeId,storeName,storeLocation,ppk,newStoreItemContainerList);
            try {
                appController.getEngine().addNewStore(newStore);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void itemsPriceAction(ActionEvent event) {
        validateStoreId();
        validateStoreName();
        validateStoreLocation();
        validatePpk();

        if(storeId != null && storeName != null && storeLocation != null && ppk != null) {
            itemsPriceButton.setDisable(true);
            createStoreButton.setDisable(false);
            storeIdHbox.setDisable(true);
            storeNameHbox.setDisable(true);
            storeLocationHbox.setDisable(true);
            storePpkHbox.setDisable(true);
            itemsFlowPane.setVisible(true);
            appController.createItemsForNewStore();
        }
    }

    private void validatePpk() {
        if (!this.storePpkTextField.getText().isEmpty()
                && this.storePpkTextField.getText().matches("[0-9]+")) {
            ppk = Integer.parseInt(this.storePpkTextField.getText());
            storePpkMessage.setVisible(false);
        } else {
            storePpkMessage.setVisible(true);
            storePpkMessage.setText("please enter store ppk (a whole number)");
            storePpkMessage.setTextFill(Color.RED);
        }
    }

    private void validateStoreLocation() {
        String regexBetween1to50 = "^(50|[1-4]?[0-9])$";
        String[] storeLocationCoordinates;

        if (!this.storeLocationTextField.getText().isEmpty()) {
            storeLocationCoordinates = this.storeLocationTextField.getText().split(",");
            if (storeLocationCoordinates.length == 2) {
                if (storeLocationCoordinates[0].matches(regexBetween1to50) && storeLocationCoordinates[1].matches(regexBetween1to50)) {
                    this.storeLocation = new Point(Integer.parseInt(storeLocationCoordinates[0]), Integer.parseInt(storeLocationCoordinates[1]));
                    this.storeLocationMessage.setVisible(false);
                    if (!appController.getEngine().isValidLocation(this.storeLocation)) {
                        storeLocationMessage.setVisible(true);
                        storeLocationMessage.setTextFill(Color.RED);
                        storeLocationMessage.setText("A store already exist in the given location");
                    }
                } else {
                    storeLocationMessage.setVisible(true);
                    storeLocationMessage.setTextFill(Color.RED);
                    storeLocationMessage.setText("Please enter a location in x,y format (x,y between [1,50])");
                }
            } else {
                storeLocationMessage.setVisible(true);
                storeLocationMessage.setTextFill(Color.RED);
                storeLocationMessage.setText("Please enter a location in x,y format (x,y between [1,50])");
            }
        } else {
            storeLocationMessage.setVisible(true);
            storeLocationMessage.setTextFill(Color.RED);
            storeLocationMessage.setText("Please enter a location in x,y format (x,y between [1,50])");
        }
    }

    private void validateStoreName() {
        if (!this.storeNameTextField.getText().isEmpty()) {
            storeName = this.storeNameTextField.getText();
            storeNameMessge.setVisible(false);
        } else {
            storeNameMessge.setVisible(true);
            storeNameMessge.setText("please enter store name");
            storeNameMessge.setTextFill(Color.RED);
        }
    }

    private void validateStoreId() {
        if (!this.storeIdTextField.getText().isEmpty()
                && this.storeIdTextField.getText().matches("[0-9]+")) {
            Integer tempStoreId = Integer.parseInt(storeIdTextField.getText());
            try {
                appController.getEngine().rejectIfStoreIdIsAlreadyExist(tempStoreId);
                storeId = tempStoreId;
                storeIdMessage.setVisible(false);
            } catch (XmlSimilarStoresIdException e) {
                storeIdMessage.setVisible(true);
                storeIdMessage.setText(e.getMessage());
                storeIdMessage.setTextFill(Color.RED);
            }
        } else {
            storeIdMessage.setVisible(true);
            storeIdMessage.setText("please enter a whole number");
            storeIdMessage.setTextFill(Color.RED);
        }
    }
}
