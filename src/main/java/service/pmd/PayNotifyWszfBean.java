package service.pmd;

/**
 * Created by lm on 2017/11/7.
 */
public class PayNotifyWszfBean {

    public String orderDate;
    public String orderNo;
    public String amount;
    public String jylsh;
    public String tranStat;
    public String return_type;
    public String sign;

    @Override
    public String toString() {
        return "PayNotifyBean{" +
                "orderDate='" + orderDate + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", amount='" + amount + '\'' +
                ", jylsh='" + jylsh + '\'' +
                ", tranStat='" + tranStat + '\'' +
                ", return_type='" + return_type + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getJylsh() {
        return jylsh;
    }

    public void setJylsh(String jylsh) {
        this.jylsh = jylsh;
    }

    public String getTranStat() {
        return tranStat;
    }

    public void setTranStat(String tranStat) {
        this.tranStat = tranStat;
    }

    public String getReturn_type() {
        return return_type;
    }

    public void setReturn_type(String return_type) {
        this.return_type = return_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
