package domain;

import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class MemberOut implements Serializable {

    public SysUser getUser(){
        return CmTag.getUserById(userId);
    }
    public SysUser getLastPrintUser(){

        return CmTag.getUserById(lastPrintUserId);
    }
    private Integer id;

    private Integer userId;

    private Integer partyId;

    private Integer branchId;

    private String phone;

    private Byte type;

    private String toTitle;

    private String toUnit;

    private String fromUnit;

    private String fromAddress;

    private String fromPhone;

    private String fromFax;

    private String fromPostCode;

    private Date payTime;

    private Integer validDays;

    private Date handleTime;

    private Boolean hasReceipt;

    private Byte status;

    private Boolean isModify;

    private Boolean isBack;

    private String reason;

    private Date applyTime;

    private Integer printCount;

    private Date lastPrintTime;

    private Integer lastPrintUserId;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getToTitle() {
        return toTitle;
    }

    public void setToTitle(String toTitle) {
        this.toTitle = toTitle == null ? null : toTitle.trim();
    }

    public String getToUnit() {
        return toUnit;
    }

    public void setToUnit(String toUnit) {
        this.toUnit = toUnit == null ? null : toUnit.trim();
    }

    public String getFromUnit() {
        return fromUnit;
    }

    public void setFromUnit(String fromUnit) {
        this.fromUnit = fromUnit == null ? null : fromUnit.trim();
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

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
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

    public Boolean getIsModify() {
        return isModify;
    }

    public void setIsModify(Boolean isModify) {
        this.isModify = isModify;
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

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getPrintCount() {
        return printCount;
    }

    public void setPrintCount(Integer printCount) {
        this.printCount = printCount;
    }

    public Date getLastPrintTime() {
        return lastPrintTime;
    }

    public void setLastPrintTime(Date lastPrintTime) {
        this.lastPrintTime = lastPrintTime;
    }

    public Integer getLastPrintUserId() {
        return lastPrintUserId;
    }

    public void setLastPrintUserId(Integer lastPrintUserId) {
        this.lastPrintUserId = lastPrintUserId;
    }
}