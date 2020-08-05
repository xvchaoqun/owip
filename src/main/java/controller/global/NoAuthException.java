package controller.global;

import java.text.MessageFormat;


public class NoAuthException extends RuntimeException {

    public NoAuthException() {
        super();
    }

    public NoAuthException(String message) {
        super(message);
    }

    public NoAuthException(String message, Object... params) {

        super(MessageFormat.format(message, params));
    }
}
