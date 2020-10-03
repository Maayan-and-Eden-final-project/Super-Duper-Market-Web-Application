package exceptions;

public class XmlSimilarStoresIdException extends Exception {
    public final String message = "A store with the same id already exist in the system (3.3)";

    public String getMessage() {
        return message;
    }
}
