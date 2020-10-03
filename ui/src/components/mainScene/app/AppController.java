package components.mainScene.app;

import components.mainScene.body.BodyController;
import components.mainScene.body.addDiscount.AddDiscountController;
import components.mainScene.body.addDiscount.addOffer.AddOfferController;
import components.mainScene.body.addItem.AddItemController;
import components.mainScene.body.addItem.storeOption.StoreOptionController;
import components.mainScene.body.addStore.AddStoreController;
import components.mainScene.body.addStore.addItemPrice.SelectItemPriceController;
import components.mainScene.body.customersHolder.CustomersHolderController;
import components.mainScene.body.itemsHolder.ItemsHolderController;
import components.mainScene.body.makeOrder.checkOut.CheckOutController;
import components.mainScene.body.makeOrder.checkOut.discount.DiscountsController;
import components.mainScene.body.makeOrder.checkOut.discount.orderSummery.summeryHolder.SummeryHolderController;
import components.mainScene.body.makeOrder.checkOut.discount.orderSummery.summeryHolder.orderConfirm.OrderConfirmController;
import components.mainScene.body.makeOrder.checkOut.discount.orderSummery.summeryHolder.summeryOrderCost.SummeryOrderCostController;
import components.mainScene.body.makeOrder.checkOut.discount.orderSummery.summeryItem.SummeryItemController;
import components.mainScene.body.makeOrder.checkOut.discount.orderSummery.summeryStore.SummeryStoreController;
import components.mainScene.body.makeOrder.orderBody.itemsHolder.OrderItemsHolderController;
import components.mainScene.body.makeOrder.orderBody.singleItem.OrderSingleItemController;
import components.mainScene.body.makeOrder.orderHeader.OrderHeaderController;
import components.mainScene.body.map.MapCustomerController;
import components.mainScene.body.map.MapStoreController;
import components.mainScene.body.singleCustomer.SingleCustomerController;
import components.mainScene.body.storesData.singleDiscount.SingleDiscountController;
import components.mainScene.body.singleItem.SingleItemController;
import components.mainScene.body.storesData.singleOffer.SingleOfferController;
import components.mainScene.body.storesData.singleOrder.SingleOrderController;
import components.mainScene.body.storesData.singleStore.SingleStoreController;
import components.mainScene.body.storesData.singleStoreItem.SingleStoreItemController;
import components.mainScene.body.storesData.storesHolder.StoresHolderController;
import components.mainScene.body.updateStoreItem.UpdateStoreItemController;
import components.mainScene.header.HeaderController;
import components.welcomeScene.UiAdapter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import sdm.enums.PurchaseCategory;
import sdm.sdmElements.*;
import systemEngine.DesktopEngine;
import systemInfoContainers.*;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppController {
    @FXML
    private HeaderController headerComponentController;

    @FXML
    private GridPane headerComponent;

    @FXML private VBox bodyComponent;

    @FXML private BodyController bodyComponentController;

    private DesktopEngine engine;
    private ItemsHolderController itemsHolderController;
    private CustomersHolderController customersHolderController;
    private StoresHolderController storesHolderController;
    private OrderHeaderController orderHeaderController;
    private Map<Integer,StoreItemInformation> currentStaticOrderItems;
    private Map<Integer,Float> currentDynamicOrderItems;
    private SimpleStringProperty currentOrderMethod;
    private CheckOutController checkOutController;
    private DiscountsController discountsController;
    private Node dynamicSummery;
    private SummeryHolderController dynamicSummeryController;
    private UpdateStoreItemController updateStoreItemController;
    private Node makeUpdateHolder;
    private Stage stage;
    private AddDiscountController addDiscountController;
    private AddStoreController addStoreController;
    private AddItemController addItemController;
    private BooleanProperty isAnimation;

    @FXML
    public void initialize() {
        if (headerComponentController != null && bodyComponentController != null ) {
            headerComponentController.setMainController(this);
            bodyComponentController.setMainController(this);
            isAnimation = new SimpleBooleanProperty();
            isAnimation.bind(headerComponentController.getAnimationsCheckBox().selectedProperty());
        }
        currentOrderMethod = new SimpleStringProperty();
        this.currentDynamicOrderItems = new HashMap<>();
        this.currentStaticOrderItems = new HashMap<>();
    }

    public boolean isIsAnimation() {
        return isAnimation.get();
    }

    public BooleanProperty isAnimationProperty() {
        return isAnimation;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void cleanPreviousOrder() {
        this.currentDynamicOrderItems.clear();
        this.currentStaticOrderItems.clear();
    }

    public void addNewDiscount(AddDiscountContainer newDiscount) {
        try {
            engine.addNewDiscountToStore(newDiscount);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public void loadAddItemMenu() {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL addItemUrl = getClass().getResource("/components/mainScene/body/addItem/addItem.fxml");
            loader.setLocation(addItemUrl);
            Node addItem = loader.load();
            addItemController = loader.getController();
            addItemController.setAppController(this);
            initAddItemMenu();

            bodyComponentController.getBodyVbox().getChildren().add(addItem);
        } catch (Exception e) {

        }

    }

    private void initAddItemMenu() {
        List<String> purchaseCategoryList = new ArrayList<String>();
        ObservableList<String> purchaseCategoryObservableList = FXCollections.observableList(purchaseCategoryList);

        purchaseCategoryObservableList.addAll("QUANTITY","WEIGHT");
        addItemController.getPurchaseCategoryComboBox().setItems(purchaseCategoryObservableList);

        engine.getStores().values().forEach(store -> {
            FXMLLoader loader = new FXMLLoader();
            URL addItemStoreUrl = getClass().getResource("/components/mainScene/body/addItem/storeOption/storeOption.fxml");
            loader.setLocation(addItemStoreUrl);
            Node storeNode = null;
            try {
                storeNode = loader.load();
            } catch (IOException e) {

            }
            StoreOptionController storeOptionController = loader.getController();
            storeOptionController.getStoreCheckBox().setText("Store Id: " + store.getId() + ", Store Name: " + store.getName());
            addItemController.getCheckedStores().add(storeOptionController);
            addItemController.getCheckStoresVbox().getChildren().add(storeNode);
        });
    }

    public void addNewItemToSystem(Integer itemIdSelection, String itemNameSelection, PurchaseCategory purchaseCategorySelection, Map<Integer, Integer> sellingStoresIdToPrice){
        engine.addNewItemToSystem(itemIdSelection,itemNameSelection,purchaseCategorySelection,sellingStoresIdToPrice);
    }

    public void displayUpdateMenu() {
       bodyComponentController.getBodyVbox().getChildren().add(makeUpdateHolder);
    }

    public void loadAddNewStoreMenu() {
        FXMLLoader loader = new FXMLLoader();
        URL addNewStoreUrl = getClass().getResource("/components/mainScene/body/addStore/addStore.fxml");
        loader.setLocation(addNewStoreUrl);
        try {
            Node addNewStore = loader.load();
            this.addStoreController = loader.getController();
            addStoreController.setAppController(this);

            bodyComponentController.getBodyVbox().getChildren().add(addNewStore);
        } catch (Exception e) {

        }
    }

    public void loadAddDiscountMenu() {
        FXMLLoader loader = new FXMLLoader();
        URL addDiscountUrl = getClass().getResource("/components/mainScene/body/addDiscount/addDiscount.fxml");
        loader.setLocation(addDiscountUrl);
        try {
            Node addDiscount = loader.load();
            this.addDiscountController = loader.getController();
            addDiscountController.setAppController(this);

            List<String> storesList = new ArrayList<String>();
            ObservableList<String> observableList = FXCollections.observableList(storesList);

            engine.getStores().values().forEach(store -> {
                observableList.add("Store Id: " + store.getId() + ", Store Name: " + store.getName());
            });

            addDiscountController.getChooseStoreComboBox().setItems(observableList);
            bodyComponentController.getBodyVbox().getChildren().add(addDiscount);

        } catch (Exception e) {

        }
    }

    public void loadStoreItemsForDiscount(Integer storeId, ComboBox ifYouBuyItemsComboBox,ComboBox offerItemsComboBox, ComboBox operator) {
        List<String> itemsList = new ArrayList<String>();
        ObservableList<String> itemsObservableList = FXCollections.observableList(itemsList);


        engine.getStores().get(storeId).getItemsAndPrices().keySet().forEach(item -> {
            itemsObservableList.add("Item Id: " + item.getId() + ", Item Name: " + item.getName());
        });
        ifYouBuyItemsComboBox.setItems(itemsObservableList);
        offerItemsComboBox.setItems(itemsObservableList);

        List<String> operatorsList = new ArrayList<String>();
        ObservableList<String> operatorsObservableList = FXCollections.observableList(operatorsList);

        operatorsObservableList.addAll("ONE-OF","ALL-OR-NOTHING","IRRELEVANT");
        operator.setItems(operatorsObservableList);

    }

    public void makeUpdateHeader() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL makeUpdateHolderUrl = getClass().getResource("/components/mainScene/body/updateStoreItem/updateStoreItem.fxml");
        loader.setLocation(makeUpdateHolderUrl);
        makeUpdateHolder = loader.load();
        updateStoreItemController = loader.getController();
        List<String> storesList = new ArrayList<String>();
        ObservableList<String> observableList = FXCollections.observableList(storesList);
        updateStoreItemController.setAppController(this);

        engine.getStores().values().forEach(store -> {
            observableList.add("Store Id: " + store.getId() + ", Store Name: " + store.getName());
        });
        updateStoreItemController.getChooseStoreComboBox().setItems(observableList);

        List<String> itemsList = new ArrayList<String>();
        ObservableList<String> itemsObservableList = FXCollections.observableList(itemsList);

        engine.getItems().values().forEach(item -> {
            itemsObservableList.add("Item Id: " + item.getId() + ", Item Name: " + item.getName());
        });

        updateStoreItemController.getChooseItemComboBox().setItems(itemsObservableList);

        List<String> updateActionList = new ArrayList<>();
        ObservableList<String> observableActionList = FXCollections.observableList(updateActionList);
        observableActionList.addAll("Update Item Price", "Delete Item From Store", "Add New Item To Store");
        updateStoreItemController.getChooseUpdateComboBox().setItems(observableActionList);

    }

    public void createMap(Stage primaryStage) {
        Point maxCoordinates = engine.getMaxCoordinates();
        List<Mappable> elements = engine.getMapElements();
        ScrollPane mapScroll = new ScrollPane();
        GridPane mapGrid = new GridPane();
        mapGrid.getStylesheets().add(AppController.class.getResource("/components/mainScene/body/map/map.css").toExternalForm());

        mapGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
        mapGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
        mapGrid.setPadding(new Insets(5,5,15,5));
        mapScroll.setFitToHeight(true);
        mapScroll.setFitToWidth(true);
        for (int i = 0; i <= maxCoordinates.y; i++) // rows
        {
            for (int j = 0; j <= maxCoordinates.x; j++) { // cols
                if (i == 0) {
                    Label colIndex = new Label(String.valueOf(j));
                    colIndex.getStyleClass().add("map-index");
                    mapGrid.addColumn(j, colIndex);
                    ColumnConstraints col = new ColumnConstraints();
                    col.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    col.setMinWidth(25);
                    col.setHgrow(Priority.ALWAYS);
                    mapGrid.getColumnConstraints().add(col);

                } else if (j == 0) {
                    Label rowIndex = new Label(String.valueOf(i));
                    rowIndex.getStyleClass().add("map-index");
                    mapGrid.addRow(i, rowIndex);
                    RowConstraints row = new RowConstraints();
                    row.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    row.setMinHeight(25);
                    row.setVgrow(Priority.ALWAYS);
                    mapGrid.getRowConstraints().add(row);
                }
            }
        }

        for (Mappable mapElem : elements) {
            if (mapElem instanceof StoreMapContainer) {
                loadStoreElement((StoreMapContainer) mapElem, mapGrid, primaryStage);
            } else if (mapElem instanceof CustomerMapContainer) {
                loadCustomerElement((CustomerMapContainer) mapElem, mapGrid, primaryStage);
            }
        }

        mapScroll.setContent(mapGrid);
        bodyComponentController.getBodyVbox().getChildren().add(mapScroll);
    }

    private void loadCustomerElement(CustomerMapContainer customerMapContainer, GridPane mapGrid, Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        URL customerMapElementUrl = getClass().getResource("/components/mainScene/body/map/mapCustomer.fxml");
        loader.setLocation(customerMapElementUrl);
        try {
            Node customerMapElement = loader.load();
            MapCustomerController customerController = loader.getController();
            customerController.setCustomerId(customerMapContainer.getCustomerId());
            customerController.setCustomerName(customerMapContainer.getCustomerName());
            customerController.setNumOfOrders(customerMapContainer.getNumOfOrders());
            customerController.setStage(primaryStage);
            customerController.setAppController(this);

            String customerInfo = "Customer Id: " + customerMapContainer.getCustomerId() + "%n Customer Name: " + customerMapContainer.getCustomerName() + "%n Number Of Orders: " + customerMapContainer.getNumOfOrders();

            Tooltip mapToolTip = new Tooltip();
            mapToolTip.setText(String.format(customerInfo));
            customerController.getCustomerButton().setTooltip(mapToolTip);
            mapGrid.add(customerMapElement, customerMapContainer.getLocation().x, customerMapContainer.getLocation().y);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStoreElement(StoreMapContainer storeMapContainer, GridPane mapGrid, Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        URL storeMapElementUrl = getClass().getResource("/components/mainScene/body/map/mapStore.fxml");
        loader.setLocation(storeMapElementUrl);
        try {
            Node storeMapElement = loader.load();
            MapStoreController storeController = loader.getController();
            storeController.setStoreId(storeMapContainer.getStoreId());
            storeController.setStoreName(storeMapContainer.getStoreName());
            storeController.setPpk(storeMapContainer.getPpk());
            storeController.setNumOfOrders(storeMapContainer.getNumOfOrders());
            storeController.setStage(primaryStage);
            storeController.setAppController(this);

            String storeInfo = "Store Id: " + storeMapContainer.getStoreId() + "%n Store Name: " + storeMapContainer.getStoreName() + "%n PPK: " + storeMapContainer.getPpk() + "%n Number Of Orders: " + storeMapContainer.getNumOfOrders();

            Tooltip mapToolTip = new Tooltip();
            mapToolTip.setText(String.format(storeInfo));
            storeController.getShopButton().setTooltip(mapToolTip);
            mapGrid.add(storeMapElement, storeMapContainer.getLocation().x, storeMapContainer.getLocation().y);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public VBox getBodyComponent() {
        return bodyComponent;
    }

    public String getCurrentOrderMethod() {
        return currentOrderMethod.get();
    }

    public SimpleStringProperty currentOrderMethodProperty() {
        return currentOrderMethod;
    }

    public Map<Integer, Float> getCurrentDynamicOrderItems() {
        return currentDynamicOrderItems;
    }

    public void setCurrentDynamicOrderItems(Map<Integer, Float> currentDynamicOrderItems) {
        this.currentDynamicOrderItems = currentDynamicOrderItems;
    }

    public Map<Integer, StoreItemInformation> getCurrentStaticOrderItems() {
        return currentStaticOrderItems;
    }

    public void setCurrentStaticOrderItems(Map<Integer, StoreItemInformation> currentStaticOrderItems) {
        this.currentStaticOrderItems = currentStaticOrderItems;
    }

    public DesktopEngine getEngine() {
        return engine;
    }

    public void setEngine(DesktopEngine engine) {
        this.engine = engine;
    }

    public void setHeaderComponentController(HeaderController headerComponentController) {
        this.headerComponentController = headerComponentController;
        headerComponentController.setMainController(this);
    }

    public void loadOrdersHistoryHolder() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/components/mainScene/body/makeOrder/checkOut/discount/orderSummery/summeryHolder/summeryHolder.fxml"));

        try {
           this.dynamicSummery = loader.load();
           this.dynamicSummeryController  = loader.getController();
           this.dynamicSummeryController.getOrderSummeryHeaderLabel().setText("Orders History");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayOrderSummery(OrderSummeryContainer orderSummery) {

        try {
            for (SingleOrderStoreInfo storeInfo : orderSummery.getStoreIdToStoreInfo().values()) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/components/mainScene/body/makeOrder/checkOut/discount/orderSummery/summeryStore/summeryStore.fxml"));
                Node dynamicSummeryStore = loader.load();

                SummeryStoreController storeController = loader.getController();
                storeController.getStoreIdValue().setText(storeInfo.getStoreId().toString());
                storeController.getStoreNameValue().setText(storeInfo.getStoreName());
                storeController.getPpkValue().setText(storeInfo.getPpk().toString());
                storeController.getDistanceFromUserValue().setText(String.format("%.2f", storeInfo.getDistanceFromCustomer()));
                storeController.getTotalShippingCostValue().setText(String.format("%.2f", storeInfo.getCustomerShippingCost()));

                for (OrderStoreItemInfo item : storeInfo.getItemIdMapToProgressItem().values()) {
                    loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/components/mainScene/body/makeOrder/checkOut/discount/orderSummery/summeryItem/summeryItem.fxml"));
                    Node dynamicSummeryItem = loader.load();
                    SummeryItemController itemController = loader.getController();
                    itemController.getItemIdValue().setText(item.getItemId().toString());
                    itemController.getItemNameValue().setText(item.getItemName());
                    itemController.getAmountValue().setText(item.getAmount().toString());
                    itemController.getPricePerPieceValue().setText(item.getPricePerPiece().toString());
                    itemController.getPurchaseCategoryValue().setText(item.getPurchaseCategory().toString());
                    itemController.getTotalItemPriceValue().setText(item.getTotalPrice().toString());
                    itemController.getIsFromDiscountValue().setText(String.valueOf(item.isFromDiscount()));
                    storeController.getStoreItemFlowPane().getChildren().add(dynamicSummeryItem);
                }

                loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/components/mainScene/body/makeOrder/checkOut/discount/orderSummery/summeryHolder/summeryOrderCost/summeryOrderCost.fxml"));
                Node summeryOrderCost = loader.load();
                SummeryOrderCostController summeryOrderCostController = loader.getController();

                summeryOrderCostController.getTotalItemsCostValue().setText(String.format("%.2f", orderSummery.getTotalOrderCostWithoutShipping()));
                summeryOrderCostController.getTotalShippingCostValue().setText(String.format("%.2f", orderSummery.getTotalShippingCost()));
                summeryOrderCostController.getTotalOrderCostValue().setText(String.format("%.2f", orderSummery.getTotalOrderCost()));
                storeController.getSummeryStoreVbox().getChildren().add(summeryOrderCost);
                dynamicSummeryController.getDynamicSummeryVbox().getChildren().add(dynamicSummeryStore);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayOrdersHistory(List<OrderSummeryContainer> ordersHistory) {
        loadOrdersHistoryHolder();
        ordersHistory.forEach(order -> displayOrderSummery(order));
        bodyComponentController.getBodyVbox().getChildren().add(dynamicSummery);
    }

    public void createStoreItemsForOrder() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL storeItemHolderUrl = getClass().getResource("/components/mainScene/body/makeOrder/orderBody/itemsHolder/itemsHolder.fxml");
        loader.setLocation(storeItemHolderUrl);
        Node storeItemsHolder = loader.load();
        OrderItemsHolderController orderItemsHolderController = loader.getController();

        for(StoreItemInformation storeItemInformation : this.currentStaticOrderItems.values()){
            loader = new FXMLLoader();
            URL singleStoreItemUrl = getClass().getResource("/components/mainScene/body/makeOrder/orderBody/singleItem/singleItem.fxml");
            loader.setLocation(singleStoreItemUrl);
            Node singleStoreItem = loader.load();
            OrderSingleItemController orderSingleItemController = loader.getController();
            orderSingleItemController.setAppController(this);
            orderSingleItemController.getItemIdValue().setText(storeItemInformation.getItem().getId().toString());
            orderSingleItemController.getItemNameValue().setText(storeItemInformation.getItem().getName());
            orderSingleItemController.getPurchaseCategoryValue().setText(storeItemInformation.getItem().getPurchaseCategory().toString());
            orderSingleItemController.setStage(stage);
            orderSingleItemController.setImageView(orderItemsHolderController.getCartImage());
            if(storeItemInformation.getItemPrice() != -1) {
                orderSingleItemController.getItemPriceValue().setText(storeItemInformation.getItemPrice().toString());
            } else {
                orderSingleItemController.getItemAmountTextField().setDisable(true);
                orderSingleItemController.getItemPriceValue().setText("Item Not Sold");
                orderSingleItemController.getAddItemButton().setDisable(true);
            }
            orderItemsHolderController.getItemsHolderFlowPane().getChildren().add(singleStoreItem);
        }
        bodyComponentController.getBodyVbox().getChildren().add(storeItemsHolder);
        loadCheckOutComponent();
        checkOutController.setStoreItemInfo(currentStaticOrderItems);

    }

    public void createItemsForOrder(ItemsContainer itemsContainer) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL itemHolderUrl = getClass().getResource("/components/mainScene/body/makeOrder/orderBody/itemsHolder/itemsHolder.fxml");
        loader.setLocation(itemHolderUrl);
        Node itemsHolder = loader.load();
        OrderItemsHolderController orderItemsHolderController = loader.getController();

        for (ItemInfo item : itemsContainer.getItemIdToItemInfo().values()) {
            loader = new FXMLLoader();
            URL singleItemUrl = getClass().getResource("/components/mainScene/body/makeOrder/orderBody/singleItem/singleItem.fxml");
            loader.setLocation(singleItemUrl);
            Node singleItem = loader.load();
            OrderSingleItemController orderSingleItemController = loader.getController();
            orderSingleItemController.setAppController(this);
            orderSingleItemController.getItemIdValue().setText(item.getItemId().toString());
            orderSingleItemController.getItemNameValue().setText(item.getItemName());
            orderSingleItemController.getPurchaseCategoryValue().setText(item.getPurchaseCategory().toString());
            orderSingleItemController.getItemPriceLabel().setVisible(false);
            orderSingleItemController.getItemPriceValue().setVisible(false);
            orderSingleItemController.setStage(stage);
            orderSingleItemController.setImageView(orderItemsHolderController.getCartImage());
            orderItemsHolderController.getItemsHolderFlowPane().getChildren().add(singleItem);
        }
        bodyComponentController.getBodyVbox().getChildren().add(itemsHolder);
        loadCheckOutComponent();

    }

    private void loadCheckOutComponent() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL checkOutUrl = getClass().getResource("/components/mainScene/body/makeOrder/checkOut/checkOut.fxml");
        loader.setLocation(checkOutUrl);
        Node checkOut = loader.load();
        this.checkOutController = loader.getController();
        checkOutController.setAppController(this);
        checkOutController.setStoreId(orderHeaderController.getStoreIdSelection());
        checkOutController.setUserLocation(engine.getIdToCustomer().get(orderHeaderController.getCustomerIdSelection()).getLocation());
        checkOutController.setUserId(orderHeaderController.getCustomerIdSelection());
        checkOutController.setOrderDate(orderHeaderController.getOrderDateSelection());
        bodyComponentController.getBodyVbox().getChildren().add(checkOut);
    }

    public HeaderController getHeaderComponentController() {
        return headerComponentController;
    }

    public void makeOrderHeader() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL makeOrderHolderUrl = getClass().getResource("/components/mainScene/body/makeOrder/orderHeader/orderHeader.fxml");
        loader.setLocation(makeOrderHolderUrl);
        Node makeOrderHolder = loader.load();
        orderHeaderController = loader.getController();
        orderHeaderController.setMainController(this);
        this.currentOrderMethodProperty().bind(orderHeaderController.orderMethodSelectionProperty());
        List<String> storesList = new ArrayList<String>();
        ObservableList<String> observableList = FXCollections.observableList(storesList);

        engine.getStores().values().forEach(store -> {
         observableList.add("Store Id: " + store.getId() + ", Store Name: " + store.getName() +
                    ", Store Location: [" + store.getLocation().x + "," + store.getLocation().y + "]");
        });
        orderHeaderController.getStoreComboBox().setItems(observableList);

        List<String> customersList = new ArrayList<String>();
        ObservableList<String> customersObservableList = FXCollections.observableList(customersList);

        engine.getIdToCustomer().values().forEach(customer -> {
            customersObservableList.add("Customer Id: " + customer.getId() + ", Customer Name: " + customer.getName());
        });
        orderHeaderController.getCustomerComboBox().setItems(customersObservableList);

        bodyComponentController.getBodyVbox().getChildren().add(makeOrderHolder);

    }


    public void createStoresData(StoresContainer storesContainer) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL storesHolderUrl = getClass().getResource("/components/mainScene/body/storesData/storesHolder/storesHolder.fxml");
        loader.setLocation(storesHolderUrl);
        Node storesHolder = loader.load();
        storesHolderController = loader.getController();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM-hh:mm");

        for (Store store : storesContainer.getStores().values()) {

            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/components/mainScene/body/storesData/singleStore/singleStore.fxml"));
            Node singleStoreTile = loader.load();
            SingleStoreController singleStoreController = loader.getController();

            singleStoreController.getIdValue().setText(String.valueOf(store.getId()));
            singleStoreController.getNameValue().setText(store.getName());
            singleStoreController.getPpkValue().setText(String.valueOf(store.getDeliveryPPK()));
            singleStoreController.getTotalDeliveryPaymentsValue().setText(String.format("%.2f", store.getTotalDeliveryPayment()));

            for (Item item : store.getItemsAndPrices().keySet()) {

                loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/components/mainScene/body/storesData/singleStoreItem/singleStoreItem.fxml"));
                Node singleStoreItemTile = loader.load();
                SingleStoreItemController singleStoreItemController = loader.getController();

                singleStoreItemController.getIdValue().setText(item.getId().toString());
                singleStoreItemController.getNameValue().setText(item.getName());
                singleStoreItemController.getPurchaseCategoryValue().setText(item.getPurchaseCategory().toString());
                singleStoreItemController.getPriceValue().setText(store.getItemsAndPrices().get(item).toString());
                singleStoreItemController.getAmountPurchasedValue().setText(store.getPurchasedItems().containsKey(item.getId())
                        ? store.getPurchasedItems().get(item.getId()).toString() : "0");
                singleStoreController.getStoreItemsComponentController().getStoresItemPane().getChildren().add(singleStoreItemTile);
            }

            if (store.getOrders().size() > 0) {
                for (Order order : store.getOrders().values()) {
                    loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/components/mainScene/body/storesData/singleOrder/singleOrder.fxml"));
                    Node singleOrderTile = loader.load();
                    SingleOrderController singleOrderController = loader.getController();

                    singleOrderController.getDateValue().setText(dateFormat.format(order.getOrderDate()));
                    singleOrderController.getTotalItemsValue().setText(String.valueOf(order.getTotalNumberOfItems()));
                    singleOrderController.getTotalItemsCostValue().setText(String.format("%.2f", order.getTotalItemsPrice()));
                    singleOrderController.getTotalDeliveryCostValue().setText(String.format("%.2f", order.getDeliveryCost()));
                    singleOrderController.getTotalCostValue().setText(String.format("%.2f", order.getTotalOrderPrice()));
                    singleOrderController.getDynamicOrderIdValue().setText(order.getDynamicOrderId() != -1 ? String.valueOf(order.getOrderId()) : "Not Dynamic Order");
                    singleStoreController.getStoreOrdersComponentController().getStoresOrderPane().getChildren().add(singleOrderTile);
                }
            } else {
                singleStoreController.getStoreOrdersComponentController().getStoresOrderPane().getChildren().add(new Label("No Orders"));
            }

            if (store.getDiscountList().size() > 0) {
                for (Discount discount : store.getDiscountList()) {
                    loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/components/mainScene/body/storesData/singleDiscount/singleDiscount.fxml"));
                    Node singleDiscountTile = loader.load();
                    SingleDiscountController singleDiscountController = loader.getController();

                    singleDiscountController.getDiscountNameValue().setText(discount.getName());
                    singleDiscountController.getItemIdValue().setText(String.valueOf(discount.getIfYouBuy().getItemId()));
                    singleDiscountController.getAmountValue().setText(String.valueOf(discount.getIfYouBuy().getQuantity()));
                    singleDiscountController.getOperatorLabel().setText(discount.getThenYouGet().getOperator().toString());

                    for (Offer offer : discount.getThenYouGet().getOffers()) {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/components/mainScene/body/storesData/singleOffer/singleOffer.fxml"));
                        Node singleOfferTile = loader.load();
                        SingleOfferController singleOfferController = loader.getController();

                        singleOfferController.getItemIdValue().setText(String.valueOf(offer.getItemId()));
                        singleOfferController.getAmountValue().setText(String.valueOf(offer.getQuantity()));
                        singleOfferController.getForAdditionalValue().setText(String.valueOf(offer.getForAdditional()));
                        singleDiscountController.getDiscountVbox().getChildren().add(singleOfferTile);
                    }
                    singleStoreController.getStoreDiscountsComponentController().getDiscountsHolderVbox().getChildren().add(singleDiscountTile);
                }
            } else {
                singleStoreController.getStoreDiscountsComponentController().getDiscountsHolderVbox().getChildren().add(new Label("No Discounts"));
            }


            storesHolderController.getStoresHolderVbox().getChildren().add(singleStoreTile);
        }
        bodyComponentController.getBodyVbox().getChildren().add(storesHolder);
    }

    public void createItemsForNewStore() {

        for(Item item : engine.getItems().values()) {
        FXMLLoader loader = new FXMLLoader();
        URL itemsPriceUrl = getClass().getResource("/components/mainScene/body/addStore/addItemPrice/selectItemPrice.fxml");
        loader.setLocation(itemsPriceUrl);
            Node itemsPrice = null;
            try {
                itemsPrice = loader.load();
                SelectItemPriceController selectItemPriceController = loader.getController();
                selectItemPriceController.getItemIdValue().setText(item.getId().toString());
                selectItemPriceController.getItemNameValue().setText(item.getName());
                selectItemPriceController.setAddStoreController(addStoreController);
                addStoreController.getItemsFlowPane().getChildren().add(itemsPrice);
            } catch (IOException e) {
            }

        }

    }

    public void createItemsData(ItemsContainer itemsContainer) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL itemsHolderUrl = getClass().getResource("/components/mainScene/body/itemsHolder/itemsHolder.fxml");
        loader.setLocation(itemsHolderUrl);
        Node itemsHolder = loader.load();
        itemsHolderController = loader.getController();

        for(ItemInfo item : itemsContainer.getItemIdToItemInfo().values()) {

            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/components/mainScene/body/singleItem/singleItem.fxml"));
            Node singleItemTile = loader.load();

            SingleItemController singleItemController = loader.getController();
            singleItemController.getItemIdValue().setText(item.getItemId().toString());
            singleItemController.getItemNameValue().setText(item.getItemName());
            singleItemController.getPurchaseCategoryValue().setText(item.getPurchaseCategory().toString());
            singleItemController.getAmountSoldValue().setText(item.getTotalPurchases().toString());
            singleItemController.getAveragePriceValue().setText(item.getAvgPrice().toString());
            singleItemController.getSellingStoresValue().setText(item.getNumOfSellingStores().toString());
            itemsHolderController.getItemsHolderVbox().getChildren().add(singleItemTile);
        }
        bodyComponentController.getBodyVbox().getChildren().add(itemsHolder);

    }

    public void createCustomersData(CustomersContainer customersContainer) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL customersHolderUrl = getClass().getResource("/components/mainScene/body/customersHolder/customersHolder.fxml");
        loader.setLocation(customersHolderUrl);
        Node customersHolder = loader.load();
        customersHolderController = loader.getController();

        for(CustomerInformation customer : customersContainer.getCustomerIdToCustomer().values()) {
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/components/mainScene/body/singleCustomer/singleCustomer.fxml"));
            Node singleCustomerTile = loader.load();


            SingleCustomerController singleCustomerController = loader.getController();
            singleCustomerController.getIdValue().setText(customer.getId().toString());
            singleCustomerController.getNameValue().setText(customer.getName());
            singleCustomerController.getLocationValue().setText("[" + customer.getLocation().x + "," +  customer.getLocation().y + "]");
            singleCustomerController.getAverageOrdersCostValue().setText(String.format("%.2f",customer.getAverageOrdersCost()));
            singleCustomerController.getAverageShipmentCostValue().setText(String.format("%.2f",customer.getAverageShipping()));
            singleCustomerController.getNumberOfOrdersValue().setText(customer.getNumberOfOrders().toString());

            customersHolderController.getCustomersHolderVbox().getChildren().add(singleCustomerTile);
        }
        bodyComponentController.getBodyVbox().getChildren().add(customersHolder);
    }

    public void cleanBody() {
        this.bodyComponentController.getBodyVbox().getChildren().clear();
    }
}
