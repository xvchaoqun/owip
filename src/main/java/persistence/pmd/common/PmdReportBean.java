package persistence.pmd.common;

import java.math.BigDecimal;

/**
 * Created by lm on 2018/1/10.
 */
public class PmdReportBean {

    private Integer branchCount;

    private Integer hasReportCount; // 已报送党支部数 或 已报送分党委数

    private Integer memberCount;

    private BigDecimal duePay;

    private Integer finishMemberCount;

    private Integer onlineFinishMemberCount;

    private BigDecimal realPay;

    private BigDecimal cashRealPay;

    private BigDecimal onlineRealPay;

    private BigDecimal delayPay;

    private Integer delayMemberCount;

    private Integer realDelayMemberCount;

    private BigDecimal realDelayPay;

    private BigDecimal onlineRealDelayPay;

    private BigDecimal cashRealDelayPay;

    public Integer getBranchCount() {
        return branchCount;
    }

    public void setBranchCount(Integer branchCount) {
        this.branchCount = branchCount;
    }

    public Integer getHasReportCount() {
        return hasReportCount == null ? 0 : hasReportCount;
    }

    public void setHasReportCount(Integer hasReportCount) {
        this.hasReportCount = hasReportCount;
    }

    public Integer getMemberCount() {
        return memberCount == null ? 0 : memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public BigDecimal getDuePay() {
        return duePay == null ? new BigDecimal(0) : duePay;
    }

    public void setDuePay(BigDecimal duePay) {
        this.duePay = duePay;
    }

    public Integer getFinishMemberCount() {
        return finishMemberCount == null ? 0 : finishMemberCount;
    }

    public void setFinishMemberCount(Integer finishMemberCount) {
        this.finishMemberCount = finishMemberCount;
    }

    public Integer getOnlineFinishMemberCount() {
        return onlineFinishMemberCount;
    }

    public void setOnlineFinishMemberCount(Integer onlineFinishMemberCount) {
        this.onlineFinishMemberCount = onlineFinishMemberCount;
    }

    public BigDecimal getRealPay() {
        return realPay == null ? new BigDecimal(0) : realPay;
    }

    public void setRealPay(BigDecimal realPay) {
        this.realPay = realPay;
    }

    public BigDecimal getCashRealPay() {
        return cashRealPay == null ? new BigDecimal(0) : cashRealPay;
    }

    public void setCashRealPay(BigDecimal cashRealPay) {
        this.cashRealPay = cashRealPay;
    }

    public BigDecimal getOnlineRealPay() {
        return onlineRealPay == null ? new BigDecimal(0) : onlineRealPay;
    }

    public void setOnlineRealPay(BigDecimal onlineRealPay) {
        this.onlineRealPay = onlineRealPay;
    }

    public BigDecimal getDelayPay() {
        return delayPay == null ? new BigDecimal(0) : delayPay;
    }

    public void setDelayPay(BigDecimal delayPay) {
        this.delayPay = delayPay;
    }

    public Integer getDelayMemberCount() {
        return delayMemberCount == null ? 0 : delayMemberCount;
    }

    public void setDelayMemberCount(Integer delayMemberCount) {
        this.delayMemberCount = delayMemberCount;
    }

    public Integer getRealDelayMemberCount() {
        return realDelayMemberCount == null ? 0 : realDelayMemberCount;
    }

    public void setRealDelayMemberCount(Integer realDelayMemberCount) {
        this.realDelayMemberCount = realDelayMemberCount;
    }

    public BigDecimal getRealDelayPay() {
        return realDelayPay == null ? new BigDecimal(0) : realDelayPay;
    }

    public void setRealDelayPay(BigDecimal realDelayPay) {
        this.realDelayPay = realDelayPay;
    }

    public BigDecimal getOnlineRealDelayPay() {
        return onlineRealDelayPay == null ? new BigDecimal(0) : onlineRealDelayPay;
    }

    public void setOnlineRealDelayPay(BigDecimal onlineRealDelayPay) {
        this.onlineRealDelayPay = onlineRealDelayPay;
    }

    public BigDecimal getCashRealDelayPay() {
        return cashRealDelayPay == null ? new BigDecimal(0) : cashRealDelayPay;
    }

    public void setCashRealDelayPay(BigDecimal cashRealDelayPay) {
        this.cashRealDelayPay = cashRealDelayPay;
    }
}
