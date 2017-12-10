package service.pmd;

public class PayNotifyCampusCardBean {

    public String paycode;
    public String payitem;
    public String payer;
    public String payertype;
    public String sn;
    public String amt;
    public String paid;
    public String paidtime;
    public String sign;

    @Override
    public String toString() {
        return "PayCallbackBean{" +
                "paycode='" + paycode + '\'' +
                ", payitem='" + payitem + '\'' +
                ", payer='" + payer + '\'' +
                ", payertype='" + payertype + '\'' +
                ", sn='" + sn + '\'' +
                ", amt='" + amt + '\'' +
                ", paid='" + paid + '\'' +
                ", paidtime='" + paidtime + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    public String getPaycode() {
        return paycode;
    }

    public void setPaycode(String paycode) {
        this.paycode = paycode;
    }

    public String getPayitem() {
        return payitem;
    }

    public void setPayitem(String payitem) {
        this.payitem = payitem;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayertype() {
        return payertype;
    }

    public void setPayertype(String payertype) {
        this.payertype = payertype;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getPaidtime() {
        return paidtime;
    }

    public void setPaidtime(String paidtime) {
        this.paidtime = paidtime;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
