package components.mainScene.body.makeOrder.checkOut.discount;

import components.welcomeScene.UiAdapter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import sdm.sdmElements.Item;
import sdm.sdmElements.Offer;
import systemEngine.DesktopEngine;
import systemInfoContainers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DiscountsController {
    @FXML
    private Button nextButton;

    @FXML
    private VBox discountsVbox;

    private DesktopEngine engine;
    private Map<Integer, List<Integer>> storeIdToItemIDList;
    private ProgressDynamicOrderContainer progressDynamicOrderInfo;
    private ProgressStaticOrderContainer progressStaticOrderContainer;
    private UiAdapter uiAdapter;
    private Map<Integer, Item> items;

    @FXML
    void nextAction(ActionEvent event) {
        showDiscounts();
    }

    public VBox getDiscountsVbox() {
        return discountsVbox;
    }

    public ProgressStaticOrderContainer getProgressStaticOrderContainer() {
        return progressStaticOrderContainer;
    }

    public void setProgressStaticOrderContainer(ProgressStaticOrderContainer progressStaticOrderContainer) {
        this.progressStaticOrderContainer = progressStaticOrderContainer;
    }

    public Map<Integer, List<Integer>> getStoreIdToItemIDList() {
        return storeIdToItemIDList;
    }

    public Map<Integer, Item> getItems() {
        return engine.getItems();
    }

    public ProgressDynamicOrderContainer getProgressDynamicOrderInfo() {
        return progressDynamicOrderInfo;
    }

    private Map<Integer, String> getItemIdAndNames() {
        return engine.getItems().values().stream()
                .collect(Collectors.toMap(item -> item.getId(), item -> item.getName()));

    }

    public void setUiAdapter(UiAdapter uiAdapter) {
        this.uiAdapter = uiAdapter;
    }

    public void setStoreIdToItemIDList(Map<Integer, List<Integer>> storeIdToItemIDList) {
        this.storeIdToItemIDList = storeIdToItemIDList;
    }

    public void setProgressDynamicOrderInfo(ProgressDynamicOrderContainer progressDynamicOrderInfo) {
        this.progressDynamicOrderInfo = progressDynamicOrderInfo;
    }

    public void setEngine(DesktopEngine engine) {
        this.engine = engine;
    }

    public Button getNextButton() {
        return nextButton;
    }

    public void showDiscounts() {
        try {
            List<SingleDiscountContainer> discountContainerList;
            List<OrderDiscountOptimizeContainer> optimizeContainerList;
            if (this.progressDynamicOrderInfo != null) {
                discountContainerList = engine.findRelevantDiscounts(storeIdToItemIDList, progressDynamicOrderInfo);
                optimizeContainerList = lookForBetterPrices(discountContainerList, progressDynamicOrderInfo);
            } else {
                discountContainerList = engine.findRelevantDiscounts(storeIdToItemIDList, progressStaticOrderContainer);
                optimizeContainerList = lookForBetterPrices(discountContainerList, progressStaticOrderContainer);
            }

            uiAdapter.setDiscountsController(this);
            uiAdapter.displayOrderDiscounts(discountContainerList, getItemIdAndNames());
            uiAdapter.displayOptimizedItems(optimizeContainerList, this.engine);
        } catch (Exception e) {
            //TODO
        }
    }

    private List<OrderDiscountOptimizeContainer> lookForBetterPrices(List<SingleDiscountContainer> discountContainerList, Containable orderInfo) {
        List<OrderDiscountOptimizeContainer> optimizeContainerList = new ArrayList<>();

        for (SingleDiscountContainer discount : discountContainerList) {
            for (Offer offer : discount.getThenYouGet().getOffers()) {
                if(orderInfo instanceof ProgressDynamicOrderContainer)
                {
                    for(ProgressOrderItem item : ((ProgressDynamicOrderContainer) orderInfo).getItemIdToOrderItem().values())
                    {
                        if(item.getItemId().equals(offer.getItemId()) && item.getAmount() != null && item.getAmount() >= offer.getQuantity())
                        {
                            if(optimizeContainerList.stream().filter(optimizedItem -> optimizedItem.getDiscountedItemId().equals(offer.getItemId())).count() > 0) //item optimize exist already
                            {
                                List<OrderDiscountOptimizeContainer> optimizeTempList = optimizeContainerList.stream().filter(optimizedItem -> optimizedItem.getDiscountedItemId().equals(offer.getItemId())).collect(Collectors.toList());
                                OrderDiscountOptimizeContainer existingOptimizedItem = optimizeContainerList.get(optimizeTempList.indexOf(optimizeTempList.get(0))); //get the existing optimized item

                                if(item.getAmount() >= existingOptimizedItem.getDiscountedAmount() + offer.getQuantity())
                                {
                                    existingOptimizedItem.setDiscountedAmount(existingOptimizedItem.getDiscountedAmount() + offer.getQuantity());
                                    existingOptimizedItem.setOldTotalItemPrice(existingOptimizedItem.getOldTotalItemPrice() + (float)offer.getQuantity() * engine.getPriceForItem(getSelectedStoreForItem(item.getItemId()), item.getItemId()));
                                    existingOptimizedItem.setNewTotalItemPrice(existingOptimizedItem.getNewTotalItemPrice() + (float)offer.getQuantity() * offer.getForAdditional());
                                }
                            }
                            else{
                                OrderDiscountOptimizeContainer optimizedItem = new OrderDiscountOptimizeContainer();
                                optimizedItem.setPurchasedItemId(offer.getItemId());
                                optimizedItem.setDiscountedItemId(offer.getItemId());
                                optimizedItem.setDiscountedAmount(offer.getQuantity());
                                optimizedItem.setPurchasedItemName(engine.getNameFromItemId(offer.getItemId()));
                                optimizedItem.setDiscountedItemName(engine.getNameFromItemId(offer.getItemId()));
                                optimizedItem.setOldTotalItemPrice((float)offer.getQuantity() * engine.getPriceForItem(getSelectedStoreForItem(item.getItemId()), item.getItemId()));
                                optimizedItem.setDiscountName(discount.getName());
                                optimizedItem.setNewTotalItemPrice((float)offer.getQuantity() * offer.getForAdditional());
                                optimizeContainerList.add(optimizedItem);
                            }
                        }
                    }
                }
                else if(orderInfo instanceof ProgressStaticOrderContainer) {
                    for (StoreItemInformation item : ((ProgressStaticOrderContainer) orderInfo).getItemIdToOrderInfo().values()) {
                        if (item.getItem().getId().equals(offer.getItemId()) && item.getAmount() != null && item.getAmount() >= offer.getQuantity()) {
                            if (optimizeContainerList.stream().filter(optimizedItem -> optimizedItem.getDiscountedItemId().equals(offer.getItemId())).count() > 0) //item optimize exist already
                            {
                                List<OrderDiscountOptimizeContainer> optimizeTempList = optimizeContainerList.stream().filter(optimizedItem -> optimizedItem.getDiscountedItemId().equals(offer.getItemId())).collect(Collectors.toList());
                                OrderDiscountOptimizeContainer existingOptimizedItem = optimizeContainerList.get(optimizeTempList.indexOf(optimizeTempList.get(0))); //get the existing optimized item

                                if (item.getAmount() >= existingOptimizedItem.getDiscountedAmount() + offer.getQuantity()) {
                                    existingOptimizedItem.setDiscountedAmount(existingOptimizedItem.getDiscountedAmount() + offer.getQuantity());
                                    existingOptimizedItem.setOldTotalItemPrice(existingOptimizedItem.getOldTotalItemPrice() + (float) offer.getQuantity() * item.getItemPrice());
                                    existingOptimizedItem.setNewTotalItemPrice(existingOptimizedItem.getNewTotalItemPrice() + (float) offer.getQuantity() * offer.getForAdditional());
                                }
                            } else {
                                OrderDiscountOptimizeContainer optimizedItem = new OrderDiscountOptimizeContainer();
                                optimizedItem.setPurchasedItemId(offer.getItemId());
                                optimizedItem.setDiscountedItemId(offer.getItemId());
                                optimizedItem.setDiscountedAmount(offer.getQuantity());
                                optimizedItem.setPurchasedItemName(engine.getNameFromItemId(offer.getItemId()));
                                optimizedItem.setDiscountedItemName(engine.getNameFromItemId(offer.getItemId()));
                                optimizedItem.setOldTotalItemPrice((float) offer.getQuantity() * item.getItemPrice());
                                optimizedItem.setDiscountName(discount.getName());
                                optimizedItem.setNewTotalItemPrice((float) offer.getQuantity() * offer.getForAdditional());
                                optimizeContainerList.add(optimizedItem);
                            }
                        }
                    }
                }
            }
        }
        return optimizeContainerList;
    }

    private Integer getSelectedStoreForItem(Integer itemId)
    {
        for(Integer storeId : storeIdToItemIDList.keySet())
        {
            if(storeIdToItemIDList.get(storeId).contains(itemId))
            {
                return storeId;
            }
        }
        return -1;
    }
}
