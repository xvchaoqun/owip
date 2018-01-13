package domain.pmd;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PmdNotifyWszf implements Serializable {
    private Integer id;

    private Date orderDate;

    private String orderNo;

    private BigDecimal amount;

    private String jylsh;

    private Byte tranStat;

    private Byte returnType;

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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getJylsh() {
        return jylsh;
    }

    public void setJylsh(String jylsh) {
        this.jylsh = jylsh == null ? null : jylsh.trim();
    }

    public Byte getTranStat() {
        return tranStat;
    }

    public void setTranStat(Byte tranStat) {
        this.tranStat = tranStat;
    }

    public Byte getReturnType() {
        return returnType;
    }

    public void setReturnType(Byte returnType) {
        this.returnType = returnType;
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