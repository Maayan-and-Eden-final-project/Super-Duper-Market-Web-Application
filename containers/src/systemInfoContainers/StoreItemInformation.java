package systemInfoContainers;

import sdm.sdmElements.Item;

public class StoreItemInformation {
    private Item item;
    private Integer itemPrice;
    private Float amount;
    private Float totalItemPrice;
    private Float discountAmount = null;

    public Float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Float discountAmount) {
        if(this.discountAmount != null) {
            this.discountAmount += discountAmount;
        } else {
            this.discountAmount = discountAmount;
        }
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Integer itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmountAndTotalPrice(Float amount) {
        if(this.amount != null) {
            this.amount += amount;
            this.totalItemPrice += amount * itemPrice;
        } else {
            this.amount = amount;
            this.totalItemPrice = amount * itemPrice;
        }
    }

    public Float getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(Float totalItemPrice) {
        if(this.totalItemPrice != null) {
            this.totalItemPrice += totalItemPrice;
        } else {
            this.totalItemPrice = totalItemPrice;
        }
    }

    public void setOnlyAmount(Float amount) {
        this.amount = amount;
    }

    public String getItemsOption() {
        StringBuilder orderInfo = new StringBuilder();
        orderInfo.append(String.format(item.toString()));
        if(getItemPrice() == -1) {
            orderInfo.append(String.format("Price: Unavailable in this store%n%n"));
        } else{
            orderInfo.append(String.format("Price: "+ getItemPrice() + "%n%n"));
        }
        return orderInfo.toString();
    }
}
