package exceptions;

public class XmlItemAlreadyExistException extends Exception {
    public final String message = "An item is already defined in the store (3.6)";

    public String getMessage() {
        return message;
    }
}
