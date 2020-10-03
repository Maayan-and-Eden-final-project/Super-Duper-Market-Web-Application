package systemInfoContainers;

import java.util.HashMap;
import java.util.Map;

public class CustomersContainer implements Containable {
    private Map<Integer,CustomerInformation> customerIdToCustomer;

    public CustomersContainer() {
        this.customerIdToCustomer = new HashMap<>();
    }

    public Map<Integer, CustomerInformation> getCustomerIdToCustomer() {
        return customerIdToCustomer;
    }

    public void setCustomerIdToCustomer(Map<Integer, CustomerInformation> customerIdToCustomer) {
        this.customerIdToCustomer = customerIdToCustomer;
    }
}
