package exceptions;

public class XmlSimilarCustomersIdException extends Exception{
    public final String message = "A customer with the same id already exist in the system ";

    public String getMessage() {
        return message;
    }
}
