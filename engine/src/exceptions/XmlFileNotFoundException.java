package exceptions;

public class XmlFileNotFoundException extends Exception {
   public final String message = "Xml file in current path is not found (3.1)";

   public String getMessage() {
      return message;
   }
}
