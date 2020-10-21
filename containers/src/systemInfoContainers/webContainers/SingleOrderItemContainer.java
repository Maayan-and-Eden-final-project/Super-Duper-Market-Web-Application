package systemInfoContainers.webContainers;

import sdm.enums.PurchaseCategory;

public class SingleOrderItemContainer {
    private Integer itemId;
    private String itemName;
    private PurchaseCategory purchaseCategory;
    private Integer storeId;
    private String storeName;
    private Float amount;
    private Float pricePerPiece;
    private Float totalItemCost;
    private boolean isFromDiscount;

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

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public Float getTotalItemCost() {
        return totalItemCost;
    }

    public void setTotalItemCost(Float totalItemCost) {
        this.totalItemCost = totalItemCost;
    }

    public boolean isFromDiscount() {
        return isFromDiscount;
    }

    public void setFromDiscount(boolean fromDiscount) {
        isFromDiscount = fromDiscount;
    }
}
