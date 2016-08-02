package domain.member;

import domain.sys.SysUser;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class MemberIn implements Serializable {
    public SysUser getUser(){
        return CmTag.getUserById(userId);
    }
    private Integer id;

    private Integer userId;

    private Byte politicalStatus;

    private Byte type;

    private Integer partyId;

    private Integer branchId;

    private String fromUnit;

    private String fromTitle;

    private String fromAddress;

    private String fromPhone;

    private String fromFax;

    private String fromPostCode;

    private Date payTime;

    private Integer validDays;

    private Date fromHandleTime;

    private Date handleTime;

    private Date applyTime;

    private Date activeTime;

    private Date candidateTime;

    private Date growTime;

    private Date positiveTime;

    private Boolean hasReceipt;

    private Byte status;

    private Boolean isBack;

    private String reason;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(Byte politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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

    public String getFromUnit() {
        return fromUnit;
    }

    public void setFromUnit(String fromUnit) {
        this.fromUnit = fromUnit == null ? null : fromUnit.trim();
    }

    public String getFromTitle() {
        return fromTitle;
    }

    public void setFromTitle(String fromTitle) {
        this.fromTitle = fromTitle == null ? null : fromTitle.trim();
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress == null ? null : fromAddress.trim();
    }

    public String getFromPhone() {
        return fromPhone;
    }

    public void setFromPhone(String fromPhone) {
        this.fromPhone = fromPhone == null ? null : fromPhone.trim();
    }

    public String getFromFax() {
        return fromFax;
    }

    public void setFromFax(String fromFax) {
        this.fromFax = fromFax == null ? null : fromFax.trim();
    }

    public String getFromPostCode() {
        return fromPostCode;
    }

    public void setFromPostCode(String fromPostCode) {
        this.fromPostCode = fromPostCode == null ? null : fromPostCode.trim();
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getValidDays() {
        return validDays;
    }

    public void setValidDays(Integer validDays) {
        this.validDays = validDays;
    }

    public Date getFromHandleTime() {
        return fromHandleTime;
    }

    public void setFromHandleTime(Date fromHandleTime) {
        this.fromHandleTime = fromHandleTime;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Date getCandidateTime() {
        return candidateTime;
    }

    public void setCandidateTime(Date candidateTime) {
        this.candidateTime = candidateTime;
    }

    public Date getGrowTime() {
        return growTime;
    }

    public void setGrowTime(Date growTime) {
        this.growTime = growTime;
    }

    public Date getPositiveTime() {
        return positiveTime;
    }

    public void setPositiveTime(Date positiveTime) {
        this.positiveTime = positiveTime;
    }

    public Boolean getHasReceipt() {
        return hasReceipt;
    }

    public void setHasReceipt(Boolean hasReceipt) {
        this.hasReceipt = hasReceipt;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Boolean getIsBack() {
        return isBack;
    }

    public void setIsBack(Boolean isBack) {
        this.isBack = isBack;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}