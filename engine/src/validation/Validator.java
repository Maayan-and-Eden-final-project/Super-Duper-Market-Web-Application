package validation;

import java.io.InputStream;

public interface Validator {
    public void validate(String toValidate) throws Exception;
}
