package exceptions;

public class NoTxtExtensionException extends Exception {
    public final String message = "Invalid Path! Please enter a .txt file path";

    public String getMessage() {
        return message;
    }
}
