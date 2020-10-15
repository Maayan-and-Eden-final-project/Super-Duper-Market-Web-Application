package sdm.sdmElements;


public class Offer implements Cloneable {
    private int itemId;
    private double quantity;
    private int forAdditional;

    @Override
    protected Offer clone() throws CloneNotSupportedException {
        return (Offer) super.clone();
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getForAdditional() {
        return forAdditional;
    }

    public void setForAdditional(int forAdditional) {
        this.forAdditional = forAdditional;
    }
}
