package exceptions;

public class StoreLocationAlreadyExistException extends Exception {
    public final String message = "A store's location already exist";

    public String getMessage() {
        return message;
    }
}
