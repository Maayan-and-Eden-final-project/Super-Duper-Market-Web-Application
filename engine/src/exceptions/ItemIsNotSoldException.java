package exceptions;

public class ItemIsNotSoldException extends Exception {
    public final String message = "This item can't be updated because it is not sold in the chosen store";

    public String getMessage() {
        return message;
    }
}
