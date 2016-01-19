package domain;

import java.io.Serializable;
import java.util.Date;

public class MemberTransfer implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer partyId;

    private Integer branchId;

    private Integer toPartyId;

    private Integer toBranchId;

    private String fromPhone;

    private String fromFax;

    private Date payTime;

    private Integer validDays;

    private Date fromHandleTime;

    private Byte status;

    private String reason;

    private Date applyTime;

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

    public Integer getToPartyId() {
        return toPartyId;
    }

    public void setToPartyId(Integer toPartyId) {
        this.toPartyId = toPartyId;
    }

    public Integer getToBranchId() {
        return toBranchId;
    }

    public void setToBranchId(Integer toBranchId) {
        this.toBranchId = toBranchId;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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
}