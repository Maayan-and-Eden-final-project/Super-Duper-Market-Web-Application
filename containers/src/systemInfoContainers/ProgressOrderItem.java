package systemInfoContainers;

import sdm.enums.PurchaseCategory;

public class ProgressOrderItem {
    private Integer itemId;
    private String itemName;
    private PurchaseCategory purchaseCategory;
    private Float amount;
    private Float discountAmount = null;
    private Integer optimisedItemPrice = null;

    public Integer getOptimisedItemPrice() {
        return optimisedItemPrice;
    }

    public void setOptimisedItemPrice(Integer optimisedItemPrice) {
        this.optimisedItemPrice = optimisedItemPrice;
    }

    public Float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Float discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public PurchaseCategory getPurchaseCategory() {
        return purchaseCategory;
    }

    public void setPurchaseCategory(PurchaseCategory purchaseCategory) {
        this.purchaseCategory = purchaseCategory;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
}
