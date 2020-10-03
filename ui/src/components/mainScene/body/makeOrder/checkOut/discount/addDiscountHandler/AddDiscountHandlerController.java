package components.mainScene.body.makeOrder.checkOut.discount.addDiscountHandler;

import components.mainScene.body.makeOrder.checkOut.discount.DiscountsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import sdm.enums.Operator;
import sdm.sdmElements.Item;
import sdm.sdmElements.Offer;
import systemInfoContainers.ProgressOrderItem;

import java.util.List;
import java.util.Map;

public class AddDiscountHandlerController {
    @FXML
    private Button addDiscountButton;

    private Operator operator;
    private DiscountsController discountsController;
    private List<Offer> offerList;
    private Integer purchasedItemId;
    private ToggleGroup toggleGroup;
    private Map<Integer, Item> items;

    public void setItems(Map<Integer, Item> items) {
        this.items = items;
    }

    public void setToggleGroup(ToggleGroup toggleGroup) {
        this.toggleGroup = toggleGroup;
    }

    public void setDiscountsController(DiscountsController discountsController) {
        this.discountsController = discountsController;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public void setOfferList(List<Offer> offerList) {
        this.offerList = offerList;
    }

    public void setPurchasedItemId(Integer purchasedItemId) {
        this.purchasedItemId = purchasedItemId;
    }

    private void updateDiscountSelectionAmount(Offer offer) {

        if(discountsController.getProgressDynamicOrderInfo() != null) {
            if (discountsController.getProgressDynamicOrderInfo().getItemIdToOrderItem().containsKey(offer.getItemId())) {
                Float discountAmount = discountsController.getProgressDynamicOrderInfo().getItemIdToOrderItem()
                        .get(offer.getItemId()).getDiscountAmount();

                if (discountAmount != null) {
                    discountsController.getProgressDynamicOrderInfo().getItemIdToOrderItem()
                            .get(offer.getItemId()).setDiscountAmount(discountAmount + (float) offer.getQuantity());
                } else {
                    discountsController.getProgressDynamicOrderInfo().getItemIdToOrderItem()
                            .get(offer.getItemId()).setDiscountAmount((float) offer.getQuantity());
                }
            } else {
                ProgressOrderItem progressDynamicItem = new ProgressOrderItem();
                progressDynamicItem.setItemId(offer.getItemId());
                progressDynamicItem.setPurchaseCategory(items.get(offer.getItemId()).getPurchaseCategory());
                progressDynamicItem.setItemName(items.get(offer.getItemId()).getName());
                progressDynamicItem.setDiscountAmount((float) offer.getQuantity());
                discountsController.getProgressDynamicOrderInfo().getItemIdToOrderItem().put(offer.getItemId(), progressDynamicItem);
            }
        } else {
            discountsController.getProgressStaticOrderContainer().getItemIdToOrderInfo()
                    .get(offer.getItemId()).setDiscountAmount((float)offer.getQuantity());
            discountsController.getProgressStaticOrderContainer().getItemIdToOrderInfo().get(offer.getItemId())
                    .setTotalItemPrice((float)offer.getQuantity() * offer.getForAdditional());
        }
    }

    @FXML
    void addDiscountAction(ActionEvent event) {

        if (this.operator.equals(Operator.ONE_OF)) {
            RadioButton chosenOffer = (RadioButton) toggleGroup.getSelectedToggle();
            int offerItemId = Integer.parseInt(chosenOffer.getId());

            for (Offer offer : offerList) {
                if (offer.getItemId() == offerItemId) {
                    updateDiscountSelectionAmount(offer);
                }
            }
        } else {
            for (Offer offer : offerList) {
                updateDiscountSelectionAmount(offer);
            }
        }
        this.addDiscountButton.setDisable(true);
    }
}
