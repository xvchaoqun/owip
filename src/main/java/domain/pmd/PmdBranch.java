package domain.pmd;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PmdBranch implements Serializable {
    private Integer id;

    private Integer monthId;

    private Integer partyId;

    private Integer branchId;

    private String partyName;

    private String branchName;

    private Integer sortOrder;

    private Integer historyDelayMemberCount;

    private BigDecimal historyDelayPay;

    private Boolean hasReport;

    private Integer memberCount;

    private BigDecimal duePay;

    private Integer finishMemberCount;

    private BigDecimal realPay;

    private BigDecimal onlineRealPay;

    private BigDecimal cashRealPay;

    private BigDecimal delayPay;

    private Integer delayMemberCount;

    private Integer realDelayMemberCount;

    private BigDecimal realDelayPay;

    private BigDecimal onlineRealDelayPay;

    private BigDecimal cashRealDelayPay;

    private Integer reportUserId;

    private Date reportTime;

    private String reportIp;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMonthId() {
        return monthId;
    }

    public void setMonthId(Integer monthId) {
        this.monthId = monthId;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName == null ? null : partyName.trim();
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName == null ? null : branchName.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getHistoryDelayMemberCount() {
        return historyDelayMemberCount;
    }

    public void setHistoryDelayMemberCount(Integer historyDelayMemberCount) {
        this.historyDelayMemberCount = historyDelayMemberCount;
    }

    public BigDecimal getHistoryDelayPay() {
        return historyDelayPay;
    }

    public void setHistoryDelayPay(BigDecimal historyDelayPay) {
        this.historyDelayPay = historyDelayPay;
    }

    public Boolean getHasReport() {
        return hasReport;
    }

    public void setHasReport(Boolean hasReport) {
        this.hasReport = hasReport;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public BigDecimal getDuePay() {
        return duePay;
    }

    public void setDuePay(BigDecimal duePay) {
        this.duePay = duePay;
    }

    public Integer getFinishMemberCount() {
        return finishMemberCount;
    }

    public void setFinishMemberCount(Integer finishMemberCount) {
        this.finishMemberCount = finishMemberCount;
    }

    public BigDecimal getRealPay() {
        return realPay;
    }

    public void setRealPay(BigDecimal realPay) {
        this.realPay = realPay;
    }

    public BigDecimal getOnlineRealPay() {
        return onlineRealPay;
    }

    public void setOnlineRealPay(BigDecimal onlineRealPay) {
        this.onlineRealPay = onlineRealPay;
    }

    public BigDecimal getCashRealPay() {
        return cashRealPay;
    }

    public void setCashRealPay(BigDecimal cashRealPay) {
        this.cashRealPay = cashRealPay;
    }

    public BigDecimal getDelayPay() {
        return delayPay;
    }

    public void setDelayPay(BigDecimal delayPay) {
        this.delayPay = delayPay;
    }

    public Integer getDelayMemberCount() {
        return delayMemberCount;
    }

    public void setDelayMemberCount(Integer delayMemberCount) {
        this.delayMemberCount = delayMemberCount;
    }

    public Integer getRealDelayMemberCount() {
        return realDelayMemberCount;
    }

    public void setRealDelayMemberCount(Integer realDelayMemberCount) {
        this.realDelayMemberCount = realDelayMemberCount;
    }

    public BigDecimal getRealDelayPay() {
        return realDelayPay;
    }

    public void setRealDelayPay(BigDecimal realDelayPay) {
        this.realDelayPay = realDelayPay;
    }

    public BigDecimal getOnlineRealDelayPay() {
        return onlineRealDelayPay;
    }

    public void setOnlineRealDelayPay(BigDecimal onlineRealDelayPay) {
        this.onlineRealDelayPay = onlineRealDelayPay;
    }

    public BigDecimal getCashRealDelayPay() {
        return cashRealDelayPay;
    }

    public void setCashRealDelayPay(BigDecimal cashRealDelayPay) {
        this.cashRealDelayPay = cashRealDelayPay;
    }

    public Integer getReportUserId() {
        return reportUserId;
    }

    public void setReportUserId(Integer reportUserId) {
        this.reportUserId = reportUserId;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public String getReportIp() {
        return reportIp;
    }

    public void setReportIp(String reportIp) {
        this.reportIp = reportIp == null ? null : reportIp.trim();
    }
}