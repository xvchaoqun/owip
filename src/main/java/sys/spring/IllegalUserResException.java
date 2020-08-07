package sys.spring;

import java.text.MessageFormat;


public class IllegalUserResException extends RuntimeException {

    public IllegalUserResException() {
        super();
    }

    public IllegalUserResException(String message) {
        super(message);
    }

    public IllegalUserResException(String message, Object... params) {

        super(MessageFormat.format(message, params));
    }
}
