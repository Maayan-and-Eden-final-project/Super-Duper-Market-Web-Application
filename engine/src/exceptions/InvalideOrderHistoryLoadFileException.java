package exceptions;

public class InvalideOrderHistoryLoadFileException extends Exception {
    public final String message = "This order history file is invalid";

    public String getMessage() {
        return message;
    }
}
