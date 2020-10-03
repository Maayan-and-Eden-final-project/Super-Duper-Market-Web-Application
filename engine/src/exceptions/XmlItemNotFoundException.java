package exceptions;

public class XmlItemNotFoundException extends Exception {
    public final String message = "An item in store does not exist in the system (3.4)";

    public String getMessage() {
        return message;
    }
}
