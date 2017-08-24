package controller.global;

import java.text.MessageFormat;

/**
 * Created by lm on 2017/7/29.
 */
public class OpException extends RuntimeException {

    public OpException(String message) {
        super(message);
    }

    public OpException(String message, String... params) {

        super(MessageFormat.format(message, params));
    }
}
