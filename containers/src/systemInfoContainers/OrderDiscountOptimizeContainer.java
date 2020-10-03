package systemInfoContainers;

public class OrderDiscountOptimizeContainer {

    Integer purchasedItemId;
    String purchasedItemName;
    Integer discountedItemId;
    String discountedItemName;
    Float oldTotalItemPrice;
    Float newTotalItemPrice;
    Double discountedAmount;
    String discountName;

    public Integer getDiscountedItemId() {
        return discountedItemId;
    }

    public void setDiscountedItemId(Integer discountedItemId) {
        this.discountedItemId = discountedItemId;
    }

    public String getDiscountedItemName() {
        return discountedItemName;
    }

    public void setDiscountedItemName(String discountedItemName) {
        this.discountedItemName = discountedItemName;
    }

    public Integer getPurchasedItemId() {
        return purchasedItemId;
    }

    public void setPurchasedItemId(Integer purchasedItemId) {
        this.purchasedItemId = purchasedItemId;
    }

    public String getPurchasedItemName() {
        return purchasedItemName;
    }

    public void setPurchasedItemName(String purchasedItemName) {
        this.purchasedItemName = purchasedItemName;
    }

    public Float getOldTotalItemPrice() {
        return oldTotalItemPrice;
    }

    public void setOldTotalItemPrice(Float oldTotalItemPrice) {
        this.oldTotalItemPrice = oldTotalItemPrice;
    }

    public Float getNewTotalItemPrice() {
        return newTotalItemPrice;
    }

    public void setNewTotalItemPrice(Float newTotalItemPrice) {
        this.newTotalItemPrice = newTotalItemPrice;
    }

    public Double getDiscountedAmount() {
        return discountedAmount;
    }

    public void setDiscountedAmount(Double discountedAmount) {
        this.discountedAmount = discountedAmount;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }
}
