package interceptor;

/**
 * Created by fafa on 2015/9/9.
 */
public class SignParamsException extends RuntimeException {

    private int ret;

    public int getRet() {
        return ret;
    }

    public SignParamsException(int ret, String message) {

        super(message);
        this.ret = ret;
    }
}
