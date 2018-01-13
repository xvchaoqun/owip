package domain.pmd;

import java.io.Serializable;
import java.util.Date;

public class PmdNotifyCampuscard implements Serializable {
    private Integer id;

    private String paycode;

    private String payitem;

    private String payer;

    private String payertype;

    private String sn;

    private String amt;

    private String paid;

    private String paidtime;

    private String sign;

    private Boolean verifySign;

    private Date retTime;

    private String ip;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPaycode() {
        return paycode;
    }

    public void setPaycode(String paycode) {
        this.paycode = paycode == null ? null : paycode.trim();
    }

    public String getPayitem() {
        return payitem;
    }

    public void setPayitem(String payitem) {
        this.payitem = payitem == null ? null : payitem.trim();
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer == null ? null : payer.trim();
    }

    public String getPayertype() {
        return payertype;
    }

    public void setPayertype(String payertype) {
        this.payertype = payertype == null ? null : payertype.trim();
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt == null ? null : amt.trim();
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid == null ? null : paid.trim();
    }

    public String getPaidtime() {
        return paidtime;
    }

    public void setPaidtime(String paidtime) {
        this.paidtime = paidtime == null ? null : paidtime.trim();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    public Boolean getVerifySign() {
        return verifySign;
    }

    public void setVerifySign(Boolean verifySign) {
        this.verifySign = verifySign;
    }

    public Date getRetTime() {
        return retTime;
    }

    public void setRetTime(Date retTime) {
        this.retTime = retTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }
}