package exceptions;

public class XmlStoreLocationOutOfRangeException extends Exception {
    public final String message = "A store's location is out of range (must be between x,y: [1,50]) (3.7)";

    public String getMessage() {
        return message;
    }
}
