package ext.msg;

/**
 * Created by lm on 2017/12/11.
 */
public class OneSendResult {

    private String type;
    private boolean success;
    private String ret;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }
}
