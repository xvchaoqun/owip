package domain;

import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class MemberOutflow implements Serializable {
    public SysUser getUser(){
        return CmTag.getUserById(userId);
    }
    private Integer id;

    private Integer userId;

    private Byte type;

    private String partyName;

    private String branchName;

    private Integer partyId;

    private Integer branchId;

    private Integer originalJob;

    private Integer direction;

    private Date flowTime;

    private Integer province;

    private String reason;

    private Boolean hasPapers;

    private Byte orStatus;

    private Byte status;

    private Boolean isBack;

    private String remark;

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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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

    public Integer getOriginalJob() {
        return originalJob;
    }

    public void setOriginalJob(Integer originalJob) {
        this.originalJob = originalJob;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Date getFlowTime() {
        return flowTime;
    }

    public void setFlowTime(Date flowTime) {
        this.flowTime = flowTime;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Boolean getHasPapers() {
        return hasPapers;
    }

    public void setHasPapers(Boolean hasPapers) {
        this.hasPapers = hasPapers;
    }

    public Byte getOrStatus() {
        return orStatus;
    }

    public void setOrStatus(Byte orStatus) {
        this.orStatus = orStatus;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}