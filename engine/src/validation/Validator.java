package validation;

import java.io.InputStream;

public interface Validator {
    public void validate(InputStream toValidate, String userName) throws Exception;
}
