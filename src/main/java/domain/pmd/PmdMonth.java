package domain.pmd;

import persistence.pmd.common.IPmdMapper;
import persistence.pmd.common.PmdReportBean;
import service.pmd.PmdMonthService;
import sys.constants.PmdConstants;
import sys.tags.CmTag;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PmdMonth implements Serializable {

    public PmdReportBean getR() {

        if (status != PmdConstants.PMD_MONTH_STATUS_START) return null;

        IPmdMapper iPmdMapper = CmTag.getBean(IPmdMapper.class);
        return iPmdMapper.getOwPmdReportBean(id);
    }

    public boolean getCanEnd() {

        if (status == PmdConstants.PMD_MONTH_STATUS_END) return false;

        PmdMonthService pmdMonthService = CmTag.getBean(PmdMonthService.class);
        return pmdMonthService.canEnd(id);
    }

    private Integer id;

    private Date payMonth;

    private Date startTime;

    private Integer startUserId;

    private Date endTime;

    private Integer endUserId;

    private Integer partyCount;

    private Integer historyDelayMemberCount;

    private BigDecimal historyDelayPay;

    private Integer hasReportCount;

    private Integer memberCount;

    private BigDecimal duePay;

    private Integer finishMemberCount;

    private Integer onlineFinishMemberCount;

    private BigDecimal realPay;

    private BigDecimal onlineRealPay;

    private BigDecimal cashRealPay;

    private BigDecimal delayPay;

    private Integer delayMemberCount;

    private Integer realDelayMemberCount;

    private BigDecimal realDelayPay;

    private BigDecimal onlineRealDelayPay;

    private BigDecimal cashRealDelayPay;

    private Byte status;

    private Boolean payStatus;

    private String payTip;

    private Integer createUserId;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getPayMonth() {
        return payMonth;
    }

    public void setPayMonth(Date payMonth) {
        this.payMonth = payMonth;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(Integer startUserId) {
        this.startUserId = startUserId;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getEndUserId() {
        return endUserId;
    }

    public void setEndUserId(Integer endUserId) {
        this.endUserId = endUserId;
    }

    public Integer getPartyCount() {
        return partyCount;
    }

    public void setPartyCount(Integer partyCount) {
        this.partyCount = partyCount;
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

    public Integer getHasReportCount() {
        return hasReportCount;
    }

    public void setHasReportCount(Integer hasReportCount) {
        this.hasReportCount = hasReportCount;
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

    public Integer getOnlineFinishMemberCount() {
        return onlineFinishMemberCount;
    }

    public void setOnlineFinishMemberCount(Integer onlineFinishMemberCount) {
        this.onlineFinishMemberCount = onlineFinishMemberCount;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Boolean getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Boolean payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayTip() {
        return payTip;
    }

    public void setPayTip(String payTip) {
        this.payTip = payTip == null ? null : payTip.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}