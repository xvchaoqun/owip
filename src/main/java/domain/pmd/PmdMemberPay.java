package domain.pmd;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PmdMemberPay implements Serializable {
    private Integer memberId;

    private String orderNo;

    private BigDecimal realPay;

    private Boolean hasPay;

    private Boolean isOnlinePay;

    private Integer payMonthId;

    private Date payTime;

    private Integer chargeUserId;

    private Integer chargePartyId;

    private Integer chargeBranchId;

    private static final long serialVersionUID = 1L;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public BigDecimal getRealPay() {
        return realPay;
    }

    public void setRealPay(BigDecimal realPay) {
        this.realPay = realPay;
    }

    public Boolean getHasPay() {
        return hasPay;
    }

    public void setHasPay(Boolean hasPay) {
        this.hasPay = hasPay;
    }

    public Boolean getIsOnlinePay() {
        return isOnlinePay;
    }

    public void setIsOnlinePay(Boolean isOnlinePay) {
        this.isOnlinePay = isOnlinePay;
    }

    public Integer getPayMonthId() {
        return payMonthId;
    }

    public void setPayMonthId(Integer payMonthId) {
        this.payMonthId = payMonthId;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getChargeUserId() {
        return chargeUserId;
    }

    public void setChargeUserId(Integer chargeUserId) {
        this.chargeUserId = chargeUserId;
    }

    public Integer getChargePartyId() {
        return chargePartyId;
    }

    public void setChargePartyId(Integer chargePartyId) {
        this.chargePartyId = chargePartyId;
    }

    public Integer getChargeBranchId() {
        return chargeBranchId;
    }

    public void setChargeBranchId(Integer chargeBranchId) {
        this.chargeBranchId = chargeBranchId;
    }
}