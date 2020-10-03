package exceptions;

public class XmlSameCustomerAndStoreLocationException extends Exception {
    public final String message = "A store's location is the same as a customer's location";

    public String getMessage() {
        return message;
    }
}
