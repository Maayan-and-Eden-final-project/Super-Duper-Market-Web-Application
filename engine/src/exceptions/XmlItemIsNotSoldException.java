package exceptions;

public class XmlItemIsNotSoldException extends Exception {
    public final String message = "An item is not sold in any of the stores (3.5)";

    public String getMessage() {
        return message;
    }
}
