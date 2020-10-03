package components.mainScene.body.makeOrder.checkOut;

import components.mainScene.app.AppController;
import components.welcomeScene.UiAdapter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import systemInfoContainers.ProgressDynamicOrderContainer;
import systemInfoContainers.ProgressStaticOrderContainer;
import systemInfoContainers.StoreItemInformation;

import java.awt.*;
import java.util.Date;
import java.util.Map;

public class CheckOutController {
    @FXML
    private Button nextButton;


    private AppController appController;
    private Point userLocation;
    private Integer storeId;
    private Integer userId;
    private Date orderDate;
    private Map<Integer, StoreItemInformation> storeItemInfo;

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserLocation(Point userLocation) {
        this.userLocation = userLocation;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public void setStoreItemInfo(Map<Integer, StoreItemInformation> storeItemInfo) {
        this.storeItemInfo = storeItemInfo;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }


    @FXML
    void nextAction(ActionEvent event) {
        appController.cleanBody();

        if(appController.getCurrentOrderMethod().equals("Static")) {
            try {
                ProgressStaticOrderContainer orderSummery = appController.getEngine().getProgressOrderInformation(userLocation,storeId,storeItemInfo);
                appController.cleanPreviousOrder();
                UiAdapter uiAdapter = new UiAdapter(storeId,userId,orderDate);
                uiAdapter.setMainAppBody(appController.getBodyComponent());
                uiAdapter.loadDiscountController(appController.getEngine(),appController.getEngine().makeStoreIdToItemIdList(orderSummery,storeId), orderSummery);
            } catch (Exception e) {
                //TODO:
                int x = 5;
            }

        } else if(appController.getCurrentOrderMethod().equals("Dynamic")) {
            UiAdapter uiAdapter = new UiAdapter(storeId,userId,orderDate);
            uiAdapter.setMainAppBody(appController.getBodyComponent());
            ProgressDynamicOrderContainer progressDynamicOrderContainer = appController.getEngine().getProgressDynamicOrder(userLocation, appController.getCurrentDynamicOrderItems());
            appController.cleanPreviousOrder();
            appController.getEngine().calcMinimalCart(uiAdapter, progressDynamicOrderContainer, appController.getBodyComponent());
        }
    }
}
