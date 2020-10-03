package exceptions;

public class SingleSellingStoreException extends Exception {
    public final String message = "This item can't be deleted because it is sold only in the chosen store";

    public String getMessage() {
        return message;
    }
}
