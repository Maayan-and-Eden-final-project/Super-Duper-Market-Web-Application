package sdm.sdmElements;

import sdm.enums.PurchaseCategory;
import java.io.Serializable;
import java.util.Objects;

public class Item implements Cloneable, Serializable {
    private Integer id;
    private String name;
    private PurchaseCategory purchaseCategory;

    @Override
    public Item clone() throws CloneNotSupportedException {
        Item item = new Item(this.id, this.name, this.purchaseCategory);
        return item;
    }

    public Item(int id, String name, PurchaseCategory purchaseCategory) {
        this.id = id;
        this.name = name;
        this.purchaseCategory = purchaseCategory;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PurchaseCategory getPurchaseCategory() {
        return purchaseCategory;
    }

    public void setPurchaseCategory(PurchaseCategory purchaseCategory) {
        this.purchaseCategory = purchaseCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        StringBuilder itemInformationString = new StringBuilder();
        itemInformationString.append(String.format("Item id: " + id + "%n"));
        itemInformationString.append(String.format("Name: " + name + "%n"));
        itemInformationString.append(String.format("Purchase Category: " + purchaseCategory.toString().toLowerCase() + "%n"));

        return itemInformationString.toString();
    }

    public boolean isPurchaseCategoryWeight() {
        return this.getPurchaseCategory().compareTo(PurchaseCategory.WEIGHT) == 0;
    }
}

