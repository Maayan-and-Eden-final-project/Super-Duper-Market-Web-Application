package components.mainScene.body.makeOrder.orderHeader;

import components.mainScene.app.AppController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import systemInfoContainers.CustomersContainer;
import systemInfoContainers.ItemsContainer;
import systemInfoContainers.StoreItemInformation;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderHeaderController {

    @FXML
    private VBox storeVbox;

    @FXML
    private HBox orderHeaderHbox;

    @FXML
    private ComboBox<String> customerComboBox;

    @FXML
    private DatePicker orderDate;

    @FXML
    private ComboBox<String> orderMethodComboBox;

    @FXML
    private ComboBox<String> storeComboBox;

    @FXML
    private Button displayItemsButton;

    private int storeIdSelection = 0;
    private int customerIdSelection = 0;
    private Date orderDateSelection;
    private SimpleStringProperty orderMethodSelection;
    private AppController mainController;


    public int getStoreIdSelection() {
        return storeIdSelection;
    }

    public int getCustomerIdSelection() {
        return customerIdSelection;
    }

    public Date getOrderDateSelection() {
        return orderDateSelection;
    }

    @FXML
    private void initialize() {
        List<String> orderMethod = new ArrayList<String>();
        ObservableList<String> observableList = FXCollections.observableList(orderMethod);
        observableList.addAll("Static","Dynamic");
        orderMethodComboBox.setItems(observableList);
        orderMethodSelection = new SimpleStringProperty();
    }

    public String getOrderMethodSelection() {
        return orderMethodSelection.get();
    }

    public SimpleStringProperty orderMethodSelectionProperty() {
        return orderMethodSelection;
    }

    public void setOrderMethodSelection(String orderMethodSelection) {
        this.orderMethodSelection.set(orderMethodSelection);
    }

    public ComboBox<String> getCustomerComboBox() {
        return customerComboBox;
    }

    public ComboBox<String> getStoreComboBox() {
        return storeComboBox;
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }


    @FXML
    void selectingCustomerAction(ActionEvent event) {
        this.customerComboBox.getStyleClass().remove("not-selected");
    }

    @FXML
    void selectingDateAction(ActionEvent event) {
        this.orderDate.getStyleClass().remove("not-selected");
    }

    @FXML
    void selectingStoreAction(ActionEvent event) {
        this.storeComboBox.getStyleClass().remove("not-selected");
        Label label = new Label();
        String storeId = storeComboBox.getSelectionModel().getSelectedItem().split(":")[1].split(",")[0];
        storeId = storeId.trim();
        storeIdSelection = Integer.parseInt(storeId);

        String customerId = customerComboBox.getSelectionModel().getSelectedItem().split(":")[1].split(",")[0];
        customerId = customerId.trim();
        customerIdSelection = Integer.parseInt(customerId);

        double shipmentCost = mainController.getEngine().calcShipmentToCustomer(customerIdSelection, storeIdSelection);
        label.setText(String.format("Total Shipping Cost: %.2f", shipmentCost));
        this.storeVbox.getChildren().add(label);
    }

    @FXML
    void displayItemsAction(ActionEvent event) {
        validate();

        if ((customerIdSelection != 0 && orderDateSelection != null
                && (orderMethodSelection.getValue().equals("Dynamic") ||
                (orderMethodSelection.getValue().equals("Static") && storeIdSelection != 0)))) {

            try {
                mainController.cleanBody();
                if(orderMethodSelection.getValue().equals("Static")) {
                  Map<Integer, StoreItemInformation>  storeItemInformationMap = mainController.getEngine().getStoreItemsInformation(storeIdSelection);
                  mainController.setCurrentStaticOrderItems(storeItemInformationMap);
                  mainController.createStoreItemsForOrder();
                } else if(orderMethodSelection.getValue().equals("Dynamic")) {
                    ItemsContainer itemsContainer = mainController.getEngine().getItemsInformation();
                    mainController.createItemsForOrder(itemsContainer);
                }

            } catch (Exception e) {
                //TODO: error info
            }
        }
    }

    @FXML
    void orderMethodAction(ActionEvent event) {
        this.orderMethodComboBox.getStyleClass().remove("not-selected");

        if(this.orderMethodComboBox.getSelectionModel().getSelectedItem() != null) {
            if(this.orderMethodComboBox.getSelectionModel().getSelectedItem().equals("Static")) {
                storeComboBox.setDisable(false);
            } else {
                storeComboBox.setDisable(true);
            }
        }
    }

    private void validate() {

        if(this.customerComboBox.getSelectionModel().getSelectedItem() != null) {

            String customerId = customerComboBox.getSelectionModel().getSelectedItem().split(":")[1].split(",")[0];
            try {
               customerId = customerId.trim();
               customerIdSelection = Integer.parseInt(customerId);
            } catch (Exception e) {

            }
        } else {
                this.customerComboBox.getStyleClass().add("not-selected");
        }

        if(this.storeComboBox.getSelectionModel().getSelectedItem() != null) {
            String storeId = storeComboBox.getSelectionModel().getSelectedItem().split(":")[1].split(",")[0];
            try {
                storeId = storeId.trim();
               storeIdSelection = Integer.parseInt(storeId);
            } catch (Exception e) {

            }
        } else {
            if(!this.storeComboBox.isDisable()) {
                this.storeComboBox.getStyleClass().add("not-selected");
            }
        }

        if(this.orderDate.getValue() != null) {
            orderDateSelection = Date.from(Instant.from(this.orderDate.getValue().atStartOfDay(ZoneId.systemDefault())));
        } else {
            this.orderDate.getStyleClass().add("not-selected");
        }

        if(this.orderMethodComboBox.getSelectionModel().getSelectedItem() != null) {
           orderMethodSelection.set(this.orderMethodComboBox.getSelectionModel().getSelectedItem());
        }  else {
            this.orderMethodComboBox.getStyleClass().add("not-selected");
        }
    }


}
