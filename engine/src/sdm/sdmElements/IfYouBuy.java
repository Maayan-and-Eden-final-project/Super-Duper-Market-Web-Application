package sdm.sdmElements;



public class IfYouBuy implements Cloneable{
    private double quantity;
    private int itemId;

    @Override
    public IfYouBuy clone() throws CloneNotSupportedException {
        return (IfYouBuy) super.clone();
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
}
