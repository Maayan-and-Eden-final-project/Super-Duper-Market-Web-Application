package systemInfoContainers;

import java.awt.*;

public class CustomerMapContainer implements Mappable {
    private Integer customerId;
    private String customerName;
    private Integer numOfOrders;
    private Point location;

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getNumOfOrders() {
        return numOfOrders;
    }

    public void setNumOfOrders(Integer numOfOrders) {
        this.numOfOrders = numOfOrders;
    }
}
