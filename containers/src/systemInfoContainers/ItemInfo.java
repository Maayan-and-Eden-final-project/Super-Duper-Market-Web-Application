package systemInfoContainers;

import sdm.enums.PurchaseCategory;

import java.util.Map;

public class ItemInfo implements Containable {
    private Float avgPrice;
    private Integer numOfSellingStores;
    private Float totalPurchases;
    private Integer itemId;
    private String itemName;
    private PurchaseCategory purchaseCategory;

    public Float getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Float avgPrice) {
        this.avgPrice = avgPrice;
    }

    public Integer getNumOfSellingStores() {
        return numOfSellingStores;
    }

    public void setNumOfSellingStores(Integer numOfSellingStores) {
        this.numOfSellingStores = numOfSellingStores;
    }

    public Float getTotalPurchases() {
        return totalPurchases;
    }

    public void setTotalPurchases(Float totalPurchases) {
        this.totalPurchases = totalPurchases;
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
}
