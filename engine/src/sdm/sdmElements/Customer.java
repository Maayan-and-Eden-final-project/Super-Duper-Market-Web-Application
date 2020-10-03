package sdm.sdmElements;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import sdm.generated2.Location;

import java.awt.*;


public class Customer implements Cloneable {
    private SimpleIntegerProperty numberOfOrders;
    private SimpleFloatProperty averageOrdersCost;
    private SimpleFloatProperty averageShipping;
    private String name;
    private Point location;
    private int id;

    public Customer(String name, Point location, int id) {
        this.name = name;
        this.location = location;
        this.id = id;
        this.numberOfOrders = new SimpleIntegerProperty(0);
        this.averageOrdersCost = new SimpleFloatProperty(0);
        this.averageShipping = new SimpleFloatProperty(0);
    }

    @Override
    public Customer clone() throws CloneNotSupportedException {
         Customer customer = (Customer) super.clone();
         customer.setLocation(new Point(this.location.x, this.location.y));
         return customer;
    }

    public float getAverageOrdersCost() {
        return averageOrdersCost.get();
    }

    public SimpleFloatProperty averageOrdersCostProperty() {
        return averageOrdersCost;
    }

    public void setAverageOrdersCost(float averageOrdersCost) {
        this.averageOrdersCost.set(averageOrdersCost);
    }

    public float getAverageShipping() {
        return averageShipping.get();
    }

    public SimpleFloatProperty averageShippingProperty() {
        return averageShipping;
    }

    public void setAverageShipping(float averageShipping) {
        this.averageShipping.set(averageShipping);
    }

    public int getNumberOfOrders() {
        return numberOfOrders.get();
    }

    public SimpleIntegerProperty numberOfOrdersProperty() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders.set(numberOfOrders);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
