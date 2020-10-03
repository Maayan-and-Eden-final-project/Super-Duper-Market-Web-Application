package exceptions;

public class XmlCustomerLocationOutOfRangeException extends Exception {
    public final String message = "A customer's location is out of range (must be between x,y: [1,50])";

    public String getMessage() {
        return message;
    }
}
