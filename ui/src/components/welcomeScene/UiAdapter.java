package components.welcomeScene;

import components.mainScene.app.AppController;
import components.mainScene.body.makeOrder.checkOut.discount.DiscountsController;
import components.mainScene.body.makeOrder.checkOut.discount.addDiscountHandler.AddDiscountHandlerController;
import components.mainScene.body.makeOrder.checkOut.discount.cartOptimize.SingleCartOptimizeController;
import components.mainScene.body.makeOrder.checkOut.discount.cartOptimize.optimizeHolder.OptimizeHolderController;
import components.mainScene.body.makeOrder.checkOut.discount.orderDiscountsHolder.OrderDiscountsHolderController;
import components.mainScene.body.makeOrder.checkOut.discount.orderSummery.OrderSummeryController;
import components.mainScene.body.makeOrder.checkOut.discount.orderSummery.summeryHolder.SummeryHolderController;
import components.mainScene.body.makeOrder.checkOut.discount.orderSummery.summeryHolder.orderConfirm.OrderConfirmController;
import components.mainScene.body.makeOrder.checkOut.discount.orderSummery.summeryHolder.summeryOrderCost.SummeryOrderCostController;
import components.mainScene.body.makeOrder.checkOut.discount.orderSummery.summeryItem.SummeryItemController;
import components.mainScene.body.makeOrder.checkOut.discount.orderSummery.summeryStore.SummeryStoreController;
import components.mainScene.body.makeOrder.checkOut.discount.singleOrderDiscount.SingleOrderDiscountController;
import components.mainScene.body.makeOrder.checkOut.dynamicMinimalCart.dynamicStoreHolder.DynamicStoreHolderController;
import components.mainScene.body.makeOrder.checkOut.dynamicMinimalCart.singleDynamicStore.SingleDynamicStoreController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sdm.enums.Operator;
import sdm.sdmElements.Offer;
import systemEngine.Connector;
import systemEngine.DesktopEngine;
import systemInfoContainers.*;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class UiAdapter {

    private Stage primaryStage;
    private DynamicStoreHolderController dynamicStoreHolderController;
    private Node dynamicStoresHolder;
    private Node orderDiscountsHolder;
    private VBox mainAppBody;
    private OrderDiscountsHolderController orderDiscountsHolderController;
    private DiscountsController discountsController;
    private Integer storeId;
    private Integer userId;
    private Date orderDate;

    public UiAdapter(Integer storeId, Integer userId, Date orderDate) {
        this.storeId = storeId;
        this.userId = userId;
        this.orderDate = orderDate;
    }

    public UiAdapter(){}

    public UiAdapter(Stage primaryStage) {
    this.primaryStage = primaryStage;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void alertError(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    public void setDiscountsController(DiscountsController discountsController) {
        this.discountsController = discountsController;
    }

    public void setMainAppBody(VBox mainAppBody) {
        this.mainAppBody = mainAppBody;
    }


    public void addDynamicStoresHolder() {
        Platform.runLater(() -> {
            Label dynamicStoresHeader = new Label("Your Minimal Cart Orders");
            dynamicStoresHeader.getStyleClass().add("information-holder");
            mainAppBody.getChildren().add(dynamicStoresHeader);
            mainAppBody.getChildren().add(dynamicStoresHolder);

        });
    }

    public void loadDiscountController(DesktopEngine engine, Map<Integer,List<Integer>> storeIdToItemIdList, ProgressStaticOrderContainer staticOrderContainer) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/components/mainScene/body/makeOrder/checkOut/discount/discounts.fxml"));
        Node discountsHandler = loader.load();
        this.discountsController = loader.getController();
        this.discountsController.setProgressStaticOrderContainer(staticOrderContainer);
        this.discountsController.setUiAdapter(this);
        this.discountsController.setStoreIdToItemIDList(storeIdToItemIdList);
        this.discountsController.setEngine(engine);
        this.discountsController.showDiscounts();
    }

    public void loadOrderDiscountsHolder() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/components/mainScene/body/makeOrder/checkOut/discount/orderDiscountsHolder/orderDiscountsHolder.fxml"));

        try {
            this.orderDiscountsHolder = loader.load();
            this.orderDiscountsHolderController = loader.getController();

        } catch (IOException e) {
            //TODO
        }
    }

    private void makeAddDiscountButton(SingleOrderDiscountController singleOrderDiscountController, Operator operator, List<Offer> offers, Integer itemId, ToggleGroup toggleGroup) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/components/mainScene/body/makeOrder/checkOut/discount/addDiscountHandler/addDiscountHandler.fxml"));
        Node addDiscountHandler = loader.load();
        singleOrderDiscountController.getDiscountVbox().getChildren().add(addDiscountHandler);
        AddDiscountHandlerController addDiscountHandlerController = loader.getController();
        addDiscountHandlerController.setOperator(operator);
        addDiscountHandlerController.setDiscountsController(this.discountsController);
        addDiscountHandlerController.setOfferList(offers);
        addDiscountHandlerController.setPurchasedItemId(itemId);
        addDiscountHandlerController.setToggleGroup(toggleGroup);
        addDiscountHandlerController.setItems(discountsController.getItems());
    }

    private void makeOneOfDiscount(SingleOrderDiscountController singleOrderDiscountController, SingleDiscountContainer discount, Map<Integer,String> itemIdAndName) throws IOException {
        final ToggleGroup group = new ToggleGroup();
        String offerString;
        for(Offer offer : discount.getThenYouGet().getOffers()){

            offerString = offer.getQuantity() + " "
                    + itemIdAndName.get(offer.getItemId()) + " for " + offer.getForAdditional()
                    + " a piece (total: " + offer.getForAdditional() * offer.getQuantity() + ")";
            RadioButton offerRadioButton = new RadioButton(offerString);
            offerRadioButton.setId(String.valueOf(offer.getItemId()));
            offerRadioButton.setToggleGroup(group);
            singleOrderDiscountController.getDiscountVbox().getChildren().add(offerRadioButton);
        }

        makeAddDiscountButton(singleOrderDiscountController, discount.getThenYouGet().getOperator(), discount.getThenYouGet().getOffers(), discount.getIfYouBuy().getItemId(), group);
    }

    private void makeDefaultDiscount(SingleOrderDiscountController singleOrderDiscountController, SingleDiscountContainer discount, Map<Integer,String> itemIdAndName) throws IOException {
        for (Offer offer : discount.getThenYouGet().getOffers()) {
            String offerString = offer.getQuantity() + " "
                    + itemIdAndName.get(offer.getItemId()) + " for " + offer.getForAdditional()
                    + " a piece (total: " + offer.getForAdditional() * offer.getQuantity() + ")";

            Label offerLabel = new Label();
            offerLabel.setText(offerString);
            offerLabel.getStyleClass().add("information-value");
            singleOrderDiscountController.getDiscountVbox().getChildren().add(offerLabel);
        }
        makeAddDiscountButton(singleOrderDiscountController, discount.getThenYouGet().getOperator(), discount.getThenYouGet().getOffers(), discount.getIfYouBuy().getItemId(), null);
    }

    public void displayOptimizedItems(List<OrderDiscountOptimizeContainer> optimizeContainerList, DesktopEngine engine) {
        try {
            if (optimizeContainerList.size() > 0) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/components/mainScene/body/makeOrder/checkOut/discount/cartOptimize/optimizeHolder/optimizeHolder.fxml"));
                Node optimizedHolder = loader.load();
                OptimizeHolderController optimizeHolderController = loader.getController();

                for (OrderDiscountOptimizeContainer optimizeContainer : optimizeContainerList) {
                    loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/components/mainScene/body/makeOrder/checkOut/discount/cartOptimize/singleCartOptimize.fxml"));
                    Node optimizedItem = loader.load();
                    SingleCartOptimizeController optimizedItemController = loader.getController();
                    optimizedItemController.getYouPurchasedValue().setText(optimizeContainer.getDiscountedAmount() + " " + optimizeContainer.getPurchasedItemName());
                    optimizedItemController.getAtAPriceOfValue().setText(optimizeContainer.getOldTotalItemPrice().toString());
                    optimizedItemController.getAndGetValue().setText(optimizeContainer.getDiscountedAmount() + " " + optimizeContainer.getDiscountedItemName());
                    optimizedItemController.getForValue().setText(optimizeContainer.getNewTotalItemPrice().toString());
                    optimizedItemController.getYouCanUseLabel().setText("You can use the \"" + optimizeContainer.getDiscountName() + "\" discount ");
                    optimizedItemController.setDiscountsController(this.discountsController);
                    optimizedItemController.setDiscountedItemId(optimizeContainer.getPurchasedItemId());
                    optimizedItemController.setDiscountedAmount(optimizeContainer.getDiscountedAmount());
                    optimizedItemController.setDiscountedTotalPrice(optimizeContainer.getNewTotalItemPrice());
                    optimizeHolderController.getDiscountsHolderFlowPane().getChildren().add(optimizedItem);

                }
                mainAppBody.getChildren().add(optimizedHolder);
            }
            loadOrderSummeryButton(engine);

        } catch (Exception e) {

        }
    }

    public void displayOrderDiscounts(List<SingleDiscountContainer> discountContainerList, Map<Integer,String> itemIdAndNames) throws IOException {
        clearBody();
        if (discountContainerList.size() > 0) {
            loadOrderDiscountsHolder();
            for (SingleDiscountContainer discount : discountContainerList) {
                loadOrderDiscounts(discount, itemIdAndNames);
            }
            mainAppBody.getChildren().add(this.orderDiscountsHolder);
        } else {
            Label noDiscountsLabel = new Label("No Discounts, Please Press Next");
            noDiscountsLabel.getStyleClass().add("discount-header");
            mainAppBody.getChildren().add(noDiscountsLabel);
        }

    }

    public void clearBody() {
        mainAppBody.getChildren().clear();
    }

    public void displayOrderSummery(OrderSummeryContainer orderSummery, DesktopEngine engine) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/components/mainScene/body/makeOrder/checkOut/discount/orderSummery/summeryHolder/summeryHolder.fxml"));

        try {
            Node dynamicSummery = loader.load();
            SummeryHolderController dynamicSummeryController  = loader.getController();

            for(SingleOrderStoreInfo storeInfo : orderSummery.getStoreIdToStoreInfo().values()) {
                loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/components/mainScene/body/makeOrder/checkOut/discount/orderSummery/summeryStore/summeryStore.fxml"));
                Node dynamicSummeryStore = loader.load();
                SummeryStoreController storeController = loader.getController();
                storeController.getStoreIdValue().setText(storeInfo.getStoreId().toString());
                storeController.getStoreNameValue().setText(storeInfo.getStoreName());
                storeController.getPpkValue().setText(storeInfo.getPpk().toString());
                storeController.getDistanceFromUserValue().setText(String.format("%.2f",storeInfo.getDistanceFromCustomer()));
                storeController.getTotalShippingCostValue().setText(String.format("%.2f",storeInfo.getCustomerShippingCost()));

                for(OrderStoreItemInfo item :  storeInfo.getItemIdMapToProgressItem().values()) {
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
                dynamicSummeryController.getDynamicSummeryVbox().getChildren().add(dynamicSummeryStore);
            }
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/components/mainScene/body/makeOrder/checkOut/discount/orderSummery/summeryHolder/summeryOrderCost/summeryOrderCost.fxml"));
            Node summeryOrderCost = loader.load();
            SummeryOrderCostController summeryOrderCostController = loader.getController();

            summeryOrderCostController.getTotalItemsCostValue().setText(String.format("%.2f",orderSummery.getTotalOrderCostWithoutShipping()));
            summeryOrderCostController.getTotalShippingCostValue().setText(String.format("%.2f",orderSummery.getTotalShippingCost()));
            summeryOrderCostController.getTotalOrderCostValue().setText(String.format("%.2f",orderSummery.getTotalOrderCost()));
            mainAppBody.getChildren().add(dynamicSummery);
            mainAppBody.getChildren().add(summeryOrderCost);

            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/components/mainScene/body/makeOrder/checkOut/discount/orderSummery/summeryHolder/orderConfirm/orderConfirm.fxml"));
            Node orderConfirm = loader.load();
            OrderConfirmController orderConfirmController = loader.getController();
            orderConfirmController.setEngine(engine);
            orderConfirmController.setOrderSummery(orderSummery);
            orderConfirmController.setUiAdapter(this);
            mainAppBody.getChildren().add(orderConfirm);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadOrderSummeryButton(DesktopEngine engine) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/components/mainScene/body/makeOrder/checkOut/discount/orderSummery/orderSummery.fxml"));
        Node orderSummery = loader.load();
        OrderSummeryController orderSummeryController = loader.getController();
        orderSummeryController.setProgressDynamicOrderInfo(discountsController.getProgressDynamicOrderInfo());
        orderSummeryController.setProgressStaticOrderContainer(discountsController.getProgressStaticOrderContainer());
        orderSummeryController.setUiAdapter(this);
        orderSummeryController.setStoreIdToItemIDList(discountsController.getStoreIdToItemIDList());
        orderSummeryController.setEngine(engine);
        mainAppBody.getChildren().add(orderSummery);
    }

    private void loadOrderDiscounts(SingleDiscountContainer discount, Map<Integer,String> itemIdAndName) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/components/mainScene/body/makeOrder/checkOut/discount/singleOrderDiscount/singleOrderDiscount.fxml"));
        try {
            Node singleDiscountTile = loader.load();

            SingleOrderDiscountController singleOrderDiscountController = loader.getController();
            singleOrderDiscountController.getDiscountNameValue().setText(discount.getName());
            singleOrderDiscountController.getYouSelectedValue().setText(discount.getIfYouBuy().getQuantity() + " " + itemIdAndName.get(discount.getIfYouBuy().getItemId()));
            singleOrderDiscountController.getYouCanGetValue().setText(discount.getThenYouGet().getOperator().toString());

           if (discount.getThenYouGet().getOperator().equals(Operator.ONE_OF)) {
               makeOneOfDiscount(singleOrderDiscountController, discount, itemIdAndName);
            } else {
               makeDefaultDiscount(singleOrderDiscountController, discount, itemIdAndName);
           }

           this.orderDiscountsHolderController.getDiscountsHolderFlowPane().getChildren().add(singleDiscountTile);

        } catch (IOException e) {
            //TODO
        }
    }

    public void loadDynamicStoresHolder(Connector engine, Map<Integer, List<Integer>> storeIdToItemIDList, ProgressDynamicOrderContainer progressDynamicOrderInfo) {
        FXMLLoader loader = new FXMLLoader();
        URL dynamicStoresHolderUrl = getClass().getResource("/components/mainScene/body/makeOrder/checkOut/dynamicMinimalCart/dynamicStoreHolder/dynamicStoreHolder.fxml");
        loader.setLocation(dynamicStoresHolderUrl);
        try {
             dynamicStoresHolder = loader.load();
        } catch (IOException e) {

        }
        this.dynamicStoreHolderController = loader.getController();
        this.dynamicStoreHolderController.getDiscountsComponentController().setEngine((DesktopEngine)engine);
        this.dynamicStoreHolderController.getDiscountsComponentController().setUiAdapter(this);
        this.dynamicStoreHolderController.getDiscountsComponentController().setProgressDynamicOrderInfo(progressDynamicOrderInfo);
        this.dynamicStoreHolderController.getDiscountsComponentController().setStoreIdToItemIDList(storeIdToItemIDList);
    }

    public void showDynamicOrderMinCart(Integer storeId, String storeName, Point storeLocation, Double distance, Integer PPK, Double totalShippingCost, Integer numOfItems, float totalItemsCost) {
        FXMLLoader loader = new FXMLLoader();
        URL singleDynamicStoreUrl = getClass().getResource("/components/mainScene/body/makeOrder/checkOut/dynamicMinimalCart/singleDynamicStore/singleDynamicStore.fxml");
        loader.setLocation(singleDynamicStoreUrl);
        Node singleDynamicStore = null;
        try {
             singleDynamicStore = loader.load();
        } catch (IOException e) {

        }

        SingleDynamicStoreController singleDynamicStoreController = loader.getController();
        singleDynamicStoreController.getStoreIdValue().setText(storeId.toString());
        singleDynamicStoreController.getStoreNameValue().setText(storeName);
        singleDynamicStoreController.getLocationValue().setText("[" + storeLocation.x + "," + storeLocation.y + "]");
        singleDynamicStoreController.getDistanceValue().setText(String.format("%.2f",distance));
        singleDynamicStoreController.getPpkValue().setText(PPK.toString());
        singleDynamicStoreController.getShippingCostValue().setText(String.format("%.2f",totalShippingCost));
        singleDynamicStoreController.getNumberOfItemsValue().setText(numOfItems.toString());
        singleDynamicStoreController.getTotalItemsCostValue().setText(String.valueOf(totalItemsCost));

        dynamicStoreHolderController.getDynamicStoresHolderFlow().getChildren().add(singleDynamicStore);

    }

    public void loadMainScene(WelcomeController welcomeController) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/components/mainScene/app/app.fxml");
        fxmlLoader.setLocation(url);
        Parent root = null;
        try {
            root = fxmlLoader.load();
            AppController appController = fxmlLoader.getController();
            appController.getHeaderComponentController().setWelcomeController(welcomeController);
            appController.setEngine(welcomeController.getEngine());
            appController.setStage(primaryStage);
        } catch (IOException e) {
            alertError("Could not load main fxml file");
        }

        Scene scene = new Scene(root);
        scene.getStylesheets().add(AppController.class.getResource("/components/resources/blueSkin/welcome.css").toExternalForm());
        scene.getStylesheets().add(AppController.class.getResource("/components/resources/blueSkin/header.css").toExternalForm());
        scene.getStylesheets().add(AppController.class.getResource("/components/resources/blueSkin/singleInformationTile.css").toExternalForm());

        Platform.runLater(() -> {
            primaryStage.setScene(scene);
        });
    }

    public void enableProgressBar(ProgressBar progressBar, Label progressLabel) {
        Platform.runLater(() -> {
           progressBar.setVisible(true);
            progressLabel.setVisible(true);
        });
    }

    public void disableProgressBar(ProgressBar progressBar, Label progressLabel, Button loadButton) {
        Platform.runLater(() -> {
            progressLabel.setVisible(false);
            progressBar.setVisible(false);
            loadButton.setDisable(false);
        });
    }

}
