package exceptions;

public class XmlDiscountItemIsNotSoldException extends Exception {
    public final String message = "A discount item is not sold in the defined store";

    public String getMessage() {
        return message;
    }
}
