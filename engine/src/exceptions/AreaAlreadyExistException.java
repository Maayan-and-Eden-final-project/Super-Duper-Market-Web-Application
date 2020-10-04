package exceptions;

public class AreaAlreadyExistException extends Exception{
    public final String message = "Area already exist";

    public String getMessage() {
        return message;
    }
}
