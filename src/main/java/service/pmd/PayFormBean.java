package service.pmd;

/**
 * Created by lm on 2018/1/11.
 */
public class PayFormBean {

    private String orderDate;
    private String orderNo;
    private String xmpch;
    private String amount;
    private String return_url;
    private String notify_url;
    private String sign;

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getXmpch() {
        return xmpch;
    }

    public void setXmpch(String xmpch) {
        this.xmpch = xmpch;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
