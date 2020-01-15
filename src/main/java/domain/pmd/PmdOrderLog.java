package domain.pmd;

import java.io.Serializable;
import java.util.Date;

public class PmdOrderLog implements Serializable {
    private Integer id;

    private Integer dateId;

    private Integer account;

    private String thirdOrderId;

    private Integer toAccount;

    private Integer tranamt;

    private String orderId;

    private String reforderId;

    private Integer operType;

    private String orderDesc;

    private String praram1;

    private String sno;

    private Integer actuaLamt;

    private Boolean state;

    private String payName;

    private Date rzDate;

    private Date jyDate;

    private String thirdSystem;

    private String sign;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDateId() {
        return dateId;
    }

    public void setDateId(Integer dateId) {
        this.dateId = dateId;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public String getThirdOrderId() {
        return thirdOrderId;
    }

    public void setThirdOrderId(String thirdOrderId) {
        this.thirdOrderId = thirdOrderId == null ? null : thirdOrderId.trim();
    }

    public Integer getToAccount() {
        return toAccount;
    }

    public void setToAccount(Integer toAccount) {
        this.toAccount = toAccount;
    }

    public Integer getTranamt() {
        return tranamt;
    }

    public void setTranamt(Integer tranamt) {
        this.tranamt = tranamt;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getReforderId() {
        return reforderId;
    }

    public void setReforderId(String reforderId) {
        this.reforderId = reforderId == null ? null : reforderId.trim();
    }

    public Integer getOperType() {
        return operType;
    }

    public void setOperType(Integer operType) {
        this.operType = operType;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc == null ? null : orderDesc.trim();
    }

    public String getPraram1() {
        return praram1;
    }

    public void setPraram1(String praram1) {
        this.praram1 = praram1 == null ? null : praram1.trim();
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno == null ? null : sno.trim();
    }

    public Integer getActuaLamt() {
        return actuaLamt;
    }

    public void setActuaLamt(Integer actuaLamt) {
        this.actuaLamt = actuaLamt;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName == null ? null : payName.trim();
    }

    public Date getRzDate() {
        return rzDate;
    }

    public void setRzDate(Date rzDate) {
        this.rzDate = rzDate;
    }

    public Date getJyDate() {
        return jyDate;
    }

    public void setJyDate(Date jyDate) {
        this.jyDate = jyDate;
    }

    public String getThirdSystem() {
        return thirdSystem;
    }

    public void setThirdSystem(String thirdSystem) {
        this.thirdSystem = thirdSystem == null ? null : thirdSystem.trim();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}