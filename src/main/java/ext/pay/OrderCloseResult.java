package ext.pay;

public class OrderCloseResult {
    
    private int httpStatus;
    private String exception;
    
    // 是否成功
    private boolean success;
    // 原始反馈
    private String ret;
    
    
    public int getHttpStatus() {
        return httpStatus;
    }
    
    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
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
    
    public String getException() {
        return exception;
    }
    
    public void setException(String exception) {
        this.exception = exception;
    }
}
