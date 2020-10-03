package exceptions;

public class XmlSimilarItemsIdException extends Exception {
    public final String message = "An item with the same id already exist in the system (3.2)";

    public String getMessage() {
        return message;
    }
}
