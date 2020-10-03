package systemInfoContainers;

import java.awt.*;

public class CustomerInformation {
    private Integer id;
    private String name;
    private Point location;
    private Integer numberOfOrders;
    private Float averageOrdersCost;
    private Float averageShipping;

    public CustomerInformation(Integer id, String name, Point location, Integer numberOfOrders, Float totalOrdersCost, Float totalShipping) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.numberOfOrders = numberOfOrders;
        this.averageOrdersCost = totalOrdersCost;
        this.averageShipping = totalShipping;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(Integer numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public Float getAverageOrdersCost() {
        return averageOrdersCost;
    }

    public void setAverageOrdersCost(Float averageOrdersCost) {
        this.averageOrdersCost = averageOrdersCost;
    }

    public Float getAverageShipping() {
        return averageShipping;
    }

    public void setAverageShipping(Float averageShipping) {
        this.averageShipping = averageShipping;
    }
}
