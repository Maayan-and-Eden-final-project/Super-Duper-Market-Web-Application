package sdm.sdmElements;

import sdm.sdmElements.IfYouBuy;
import sdm.sdmElements.ThenYouGet;

import javax.xml.bind.annotation.XmlElement;

public class Discount implements Cloneable{
    private String name;
    private IfYouBuy ifYouBuy;
    private ThenYouGet thenYouGet;

    @Override
    protected Discount clone() {
        Discount newDiscount = null;
        try {
            newDiscount = (Discount) super.clone();
            IfYouBuy ifYouBuy = this.ifYouBuy.clone();
            ThenYouGet thenYouGet = this.thenYouGet.clone();
            newDiscount.setIfYouBuy(ifYouBuy);
            newDiscount.setThenYouGet(thenYouGet);
        } catch (CloneNotSupportedException ex) {
            newDiscount = null;
        }
        return newDiscount;
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
