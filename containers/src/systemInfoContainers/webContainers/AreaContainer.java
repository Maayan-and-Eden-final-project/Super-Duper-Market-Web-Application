package systemInfoContainers.webContainers;

public class AreaContainer {
    private String areaOwner;
    private String areaName;
    private Integer itemsInArea;
    private Integer storesInArea;
    private Integer ordersInArea;
    private Float avgOrdersCostsInArea; //without shipping

    public String getAreaOwner() {
        return areaOwner;
    }

    public void setAreaOwner(String areaOwner) {
        this.areaOwner = areaOwner;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getItemsInArea() {
        return itemsInArea;
    }

    public void setItemsInArea(Integer itemsInArea) {
        this.itemsInArea = itemsInArea;
    }

    public Integer getStoresInArea() {
        return storesInArea;
    }

    public void setStoresInArea(Integer storesInArea) {
        this.storesInArea = storesInArea;
    }

    public Integer getOrdersInArea() {
        return ordersInArea;
    }

    public void setOrdersInArea(Integer ordersInArea) {
        this.ordersInArea = ordersInArea;
    }

    public Float getAvgOrdersCostsInArea() {
        return avgOrdersCostsInArea;
    }

    public void setAvgOrdersCostsInArea(Float avgOrdersCostsInArea) {
        this.avgOrdersCostsInArea = avgOrdersCostsInArea;
    }
}
