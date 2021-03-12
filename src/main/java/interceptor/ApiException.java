package interceptor;

import sys.constants.SystemConstants;

/**
 * Created by fafa on 2015/9/9.
 */
public class ApiException extends RuntimeException {

    private int ret;

    public int getRet() {
        return ret;
    }

    public ApiException(int ret) {

        super(SystemConstants.API_RETURN_MAP.get(ret));
        this.ret = ret;
    }

    public ApiException(int ret, String msg){
        super(msg);
        this.ret = ret;
    }
}
