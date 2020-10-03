package sdm.sdmElements;

import sdm.enums.PurchaseCategory;

public class OrderedItem implements Cloneable {
    private Integer itemId;
    private String itemName;
    private PurchaseCategory purchaseCategory;
    private Float amount;
    private Float pricePerPiece;
    private Float totalPrice;
    private boolean isFromDiscount;

    @Override
    public OrderedItem clone() throws CloneNotSupportedException {
        return (OrderedItem)super.clone();
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

    public Float getPricePerPiece() {
        return pricePerPiece;
    }

    public void setPricePerPiece(Float pricePerPiece) {
        this.pricePerPiece = pricePerPiece;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isFromDiscount() {
        return isFromDiscount;
    }

    public void setFromDiscount(boolean fromDiscount) {
        isFromDiscount = fromDiscount;
    }
}
