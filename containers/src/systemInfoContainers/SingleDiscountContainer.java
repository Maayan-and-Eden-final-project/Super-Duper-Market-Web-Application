package systemInfoContainers;

import sdm.sdmElements.IfYouBuy;
import sdm.sdmElements.ThenYouGet;

public class SingleDiscountContainer implements Containable {
    private String name;
    private IfYouBuy ifYouBuy;
    private ThenYouGet thenYouGet;

    public SingleDiscountContainer() {
        this.ifYouBuy = new IfYouBuy();
        this.thenYouGet = new ThenYouGet();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
