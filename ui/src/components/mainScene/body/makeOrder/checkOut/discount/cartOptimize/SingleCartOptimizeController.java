package components.mainScene.body.makeOrder.checkOut.discount.cartOptimize;

import components.mainScene.body.makeOrder.checkOut.discount.DiscountsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import systemInfoContainers.ProgressOrderItem;
import systemInfoContainers.StoreItemInformation;

public class SingleCartOptimizeController {
    @FXML
    private Label youPurchasedLabel;

    @FXML
    private Label youPurchasedValue;

    @FXML
    private Label atAPriceOfLabel;

    @FXML
    private Label atAPriceOfValue;

    @FXML
    private Label youCanUseLabel;

    @FXML
    private Label andGetLabel;

    @FXML
    private Label andGetValue;

    @FXML
    private Label forValue;

    @FXML
    private Button replaceButton;

    private DiscountsController discountsController;
    private Integer discountedItemId;
    private Double discountedAmount;
    private Float discountedTotalPrice;

    public void setDiscountedTotalPrice(Float discountedTotalPrice) {
        this.discountedTotalPrice = discountedTotalPrice;
    }

    public void setDiscountedAmount(Double discountedAmount) {
        this.discountedAmount = discountedAmount;
    }

    public void setDiscountedItemId(Integer discountedItemId) {
        this.discountedItemId = discountedItemId;
    }

    public void setDiscountsController(DiscountsController discountsController) {
        this.discountsController = discountsController;
    }

    public Label getYouPurchasedValue() {
        return youPurchasedValue;
    }

    public Label getAtAPriceOfValue() {
        return atAPriceOfValue;
    }

    public Label getYouCanUseLabel() {
        return youCanUseLabel;
    }

    public Label getAndGetValue() {
        return andGetValue;
    }

    public Label getForValue() {
        return forValue;
    }

    @FXML
    void replaceAction(ActionEvent event) {
        if(this.discountsController.getProgressDynamicOrderInfo() != null)
        {
            ProgressOrderItem orderedItem = this.discountsController.getProgressDynamicOrderInfo().getItemIdToOrderItem().get(discountedItemId);
            if(orderedItem.getAmount() != null)
            {
                orderedItem.setAmount((float)(orderedItem.getAmount() - discountedAmount));
            }

            if(orderedItem.getDiscountAmount() != null)
            {
                orderedItem.setDiscountAmount((float)(orderedItem.getDiscountAmount() + discountedAmount));
            }
            else{
                orderedItem.setDiscountAmount(discountedAmount.floatValue());
            }

            if(orderedItem.getOptimisedItemPrice() != null)
            {
                orderedItem.setOptimisedItemPrice(orderedItem.getOptimisedItemPrice() + (int)(discountedTotalPrice/discountedAmount));
            }
            else{
                orderedItem.setOptimisedItemPrice((int)(discountedTotalPrice/discountedAmount));
            }
            this.replaceButton.setDisable(true);
        }
        else if(this.discountsController.getProgressStaticOrderContainer() != null) {
            StoreItemInformation orderedItem = this.discountsController.getProgressStaticOrderContainer().getItemIdToOrderInfo().get(discountedItemId);


            if(orderedItem.getAmount() != null)
            {
                orderedItem.setOnlyAmount((float) (orderedItem.getAmount() - discountedAmount));
            }
            orderedItem.setTotalItemPrice(-orderedItem.getTotalItemPrice() + orderedItem.getAmount() * orderedItem.getItemPrice() + discountedTotalPrice);
            orderedItem.setDiscountAmount(discountedAmount.floatValue());
            orderedItem.setItemPrice((int)(discountedTotalPrice/discountedAmount));
            this.replaceButton.setDisable(true);
        }
    }

}
