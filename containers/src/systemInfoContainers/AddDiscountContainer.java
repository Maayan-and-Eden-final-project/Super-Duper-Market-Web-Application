package systemInfoContainers;

import sdm.sdmElements.IfYouBuy;
import sdm.sdmElements.ThenYouGet;

public class AddDiscountContainer {
    private Integer storeId;
    private String discountName;
    private IfYouBuy ifYouBuy;
    private ThenYouGet thenYouGet;

    public AddDiscountContainer() {
        this.ifYouBuy = new IfYouBuy();
        this.thenYouGet = new ThenYouGet();
    }


    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public IfYouBuy getIfYouBuy() {
        return ifYouBuy;
    }

    public void setIfYouBuy(IfYouBuy ifYouBuy) {
        this.ifYouBuy = ifYouBuy;
    }

    public ThenYouGet getThenYouGet() {
        return thenYouGet;
    }

    public void setThenYouGet(ThenYouGet thenYouGet) {
        this.thenYouGet = thenYouGet;
    }
}
