package domain.pmd;

import domain.sys.SysUserView;
import service.pmd.PmdMemberPayService;
import sys.tags.CmTag;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PmdMemberPayView implements Serializable {

    public SysUserView getUser() {

        return CmTag.getUserById(userId);
    }

    public SysUserView getOrderUser() {

        return CmTag.getUserById(orderUserId);
    }

    public SysUserView getChargeUser() {

        return CmTag.getUserById(chargeUserId);
    }

    public int getPayStatus() {

        PmdMemberPayService pmdMemberPayService = CmTag.getBean(PmdMemberPayService.class);
        return pmdMemberPayService.getPayStatus(memberId);
    }

    private Integer id;

    private Integer memberId;

    private String orderNo;

    private Integer orderUserId;

    private BigDecimal realPay;

    private Boolean hasPay;

    private Boolean isSelfPay;

    private Integer payMonthId;

    private Date payTime;

    private Integer chargeUserId;

    private Integer chargePartyId;

    private Integer chargeBranchId;

    private Date payMonth;

    private Integer monthId;

    private Boolean isDelay;

    private String delayReason;

    private Integer userId;

    private Integer partyId;

    private Integer branchId;

    private Byte type;

    private BigDecimal duePay;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(Integer orderUserId) {
        this.orderUserId = orderUserId;
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

    public Boolean getIsSelfPay() {
        return isSelfPay;
    }

    public void setIsSelfPay(Boolean isSelfPay) {
        this.isSelfPay = isSelfPay;
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

    public Date getPayMonth() {
        return payMonth;
    }

    public void setPayMonth(Date payMonth) {
        this.payMonth = payMonth;
    }

    public Integer getMonthId() {
        return monthId;
    }

    public void setMonthId(Integer monthId) {
        this.monthId = monthId;
    }

    public Boolean getIsDelay() {
        return isDelay;
    }

    public void setIsDelay(Boolean isDelay) {
        this.isDelay = isDelay;
    }

    public String getDelayReason() {
        return delayReason;
    }

    public void setDelayReason(String delayReason) {
        this.delayReason = delayReason == null ? null : delayReason.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public BigDecimal getDuePay() {
        return duePay;
    }

    public void setDuePay(BigDecimal duePay) {
        this.duePay = duePay;
    }
}