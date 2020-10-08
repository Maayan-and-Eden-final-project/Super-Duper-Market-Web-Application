package systemInfoContainers.webContainers;

import sdm.enums.PurchaseCategory;

public class SingleStoreItemContainer {
    private Integer itemId;
    private String itemName;
    private PurchaseCategory purchaseCategory;
    private Integer itemPrice;
    private float amountSold=0;


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

    public Integer getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Integer itemPrice) {
        this.itemPrice = itemPrice;
    }

    public float getAmountSold() {
        return amountSold;
    }

    public void setAmountSold(float amountSold) {
        this.amountSold = amountSold;
    }
}
