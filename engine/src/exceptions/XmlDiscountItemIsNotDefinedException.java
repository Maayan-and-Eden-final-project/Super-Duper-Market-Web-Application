package exceptions;

public class XmlDiscountItemIsNotDefinedException extends Exception {
    public final String message = "A discount item is not defined in the system";

    public String getMessage() {
        return message;
    }
}
