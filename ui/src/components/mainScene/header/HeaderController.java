package components.mainScene.header;

import components.mainScene.app.AppController;
import components.mainScene.body.singleItem.SingleItemController;
import components.mainScene.body.updateStoreItem.UpdateStoreItemController;
import components.welcomeScene.WelcomeController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import systemInfoContainers.CustomersContainer;
import systemInfoContainers.ItemsContainer;
import systemInfoContainers.OrderSummeryContainer;
import systemInfoContainers.StoresContainer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeaderController {

    @FXML
    private Button loadXmlButton;

    @FXML
    private Button UpdateItemButton;

    @FXML
    private Button storesButton;

    @FXML
    private Button itemsButton;

    @FXML
    private Button customersButton;

    @FXML
    private Button ordersButton;

    @FXML
    private Button makeOrderButton;

    @FXML
    private Button mapButton;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label progressLabel;

    @FXML
    private Button purpleSkin;

    @FXML
    private Button blueSkin;

    @FXML
    private Button yellowSkin;

    @FXML
    private Button addDiscountButton;

    @FXML
    private Button addStoreButton;

    @FXML
    private Button addItemButton;

    @FXML
    private CheckBox animationsCheckBox;

    private WelcomeController welcomeController;
    private AppController mainController;

    public void setWelcomeController(WelcomeController welcomeController) {
        this.welcomeController = welcomeController;
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public CheckBox getAnimationsCheckBox() {
        return animationsCheckBox;
    }

    @FXML
    private void initialize() {

    }

    @FXML
    void addStoreAction(ActionEvent event) {
        mainController.cleanPreviousOrder();
        mainController.cleanBody();
        mainController.loadAddNewStoreMenu();
    }

    @FXML
    void blueSkinAction(ActionEvent event) {
        mainController.getStage().getScene().getStylesheets().clear();
        mainController.getStage().getScene().getStylesheets().add(AppController.class.getResource("/components/resources/blueSkin/welcome.css").toExternalForm());
        mainController.getStage().getScene().getStylesheets().add(AppController.class.getResource("/components/resources/blueSkin/header.css").toExternalForm());
        mainController.getStage().getScene().getStylesheets().add(AppController.class.getResource("/components/resources/blueSkin/singleInformationTile.css").toExternalForm());
    }

    @FXML
    void purpleSkinAction(ActionEvent event) {
        mainController.getStage().getScene().getStylesheets().clear();
        mainController.getStage().getScene().getStylesheets().add(AppController.class.getResource("/components/resources/purpleSkin/welcomePurple.css").toExternalForm());
        mainController.getStage().getScene().getStylesheets().add(AppController.class.getResource("/components/resources/purpleSkin/headerPurple.css").toExternalForm());
        mainController.getStage().getScene().getStylesheets().add(AppController.class.getResource("/components/resources/purpleSkin/singleInformationTilePurple.css").toExternalForm());
    }

    @FXML
    void yellowSkinAction(ActionEvent event) {
        mainController.getStage().getScene().getStylesheets().clear();
        mainController.getStage().getScene().getStylesheets().add(AppController.class.getResource("/components/resources/yellowSkin/welcomeYellow.css").toExternalForm());
        mainController.getStage().getScene().getStylesheets().add(AppController.class.getResource("/components/resources/yellowSkin/headerYellow.css").toExternalForm());
        mainController.getStage().getScene().getStylesheets().add(AppController.class.getResource("/components/resources/yellowSkin/singleInformationTileYellow.css").toExternalForm());
    }

    @FXML
    void addDiscountAction(ActionEvent event) {
        mainController.cleanPreviousOrder();
        mainController.cleanBody();
        mainController.loadAddDiscountMenu();
    }

    @FXML
    void addItemAction(ActionEvent event) {
        mainController.cleanPreviousOrder();
        mainController.cleanBody();
        mainController.loadAddItemMenu();
    }

    @FXML
    void makeOrderAction(ActionEvent event) {
        mainController.cleanPreviousOrder();
        mainController.cleanBody();
        try {
            mainController.makeOrderHeader();
        } catch (IOException e) {
            //TODO: error info
        }
    }


    @FXML
    void showCustomersActionTest(ActionEvent event) {
        mainController.cleanPreviousOrder();
        CustomersContainer customersContainer;
        try {
            mainController.cleanBody();
            customersContainer = mainController.getEngine().getCustomersInformation();
            mainController.createCustomersData(customersContainer);
        } catch (Exception e) {
            //TODO: error info
        }
    }

    @FXML
    void showItemsAction(ActionEvent event) {
        mainController.cleanPreviousOrder();
        ItemsContainer itemsContainer;
        try {
            mainController.cleanBody();
            itemsContainer = mainController.getEngine().getItemsInformation();
            mainController.createItemsData(itemsContainer);
        } catch (Exception e) {
            //TODO: error info
        }
    }

    @FXML
    void showMapAction(ActionEvent event) {
        mainController.cleanPreviousOrder();
        mainController.cleanBody();
        mainController.createMap(welcomeController.getPrimaryStage());
    }

    @FXML
    void showOrdersButton(ActionEvent event) {
        mainController.cleanPreviousOrder();
        mainController.cleanBody();
        List<OrderSummeryContainer> ordersHistory = mainController.getEngine().getOrdersHistory();
        mainController.displayOrdersHistory(ordersHistory);
    }

    @FXML
    void showStoresAction(ActionEvent event) {
        mainController.cleanPreviousOrder();
        StoresContainer storesContainer;
        try {
            mainController.cleanBody();
            storesContainer = mainController.getEngine().getStoresInformation();
            mainController.createStoresData(storesContainer);
        } catch (Exception e) {
            //TODO: error info
        }
    }

    @FXML
    void updateItemAction(ActionEvent event) throws IOException {
        mainController.cleanPreviousOrder();
        mainController.cleanBody();
        mainController.makeUpdateHeader();
        mainController.displayUpdateMenu();
    }

    @FXML
    void openFileButtonAction(ActionEvent event) {
        mainController.cleanPreviousOrder();
        welcomeController.setProgressBar(progressBar);
        welcomeController.setProgressLabel(progressLabel);
        welcomeController.setLoadXmlButton(loadXmlButton);
        welcomeController.openFileButtonAction(event);
    }
}
