package ext.pay;

public class OrderQueryResult {

    private int httpStatus;
    private String exception;
    
    private boolean hasPay; // 是否已支付
    private String payer;
    private boolean isAbolish; // 是否已经取消
    
    private String status; // 订单状态
    private String ret;
    
    public int getHttpStatus() {
        return httpStatus;
    }
    
    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }
    
    public String getException() {
        return exception;
    }
    
    public void setException(String exception) {
        this.exception = exception;
    }
    
    public boolean isHasPay() {
        return hasPay;
    }
    
    public void setHasPay(boolean hasPay) {
        this.hasPay = hasPay;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public boolean isAbolish() {
        return isAbolish;
    }
    
    public void setAbolish(boolean abolish) {
        isAbolish = abolish;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getRet() {
        return ret;
    }
    
    public void setRet(String ret) {
        this.ret = ret;
    }
}
