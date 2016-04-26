package domain;

import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class MemberInflow implements Serializable {
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

    private Integer province;

    private Boolean hasPapers;

    private Date flowTime;

    private String reason;

    private Date growTime;

    private String orLocation;

    private Byte inflowStatus;

    private Boolean isBack;

    private String outflowUnit;

    private Integer outflowLocation;

    private Date outflowTime;

    private Byte outflowStatus;

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

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Boolean getHasPapers() {
        return hasPapers;
    }

    public void setHasPapers(Boolean hasPapers) {
        this.hasPapers = hasPapers;
    }

    public Date getFlowTime() {
        return flowTime;
    }

    public void setFlowTime(Date flowTime) {
        this.flowTime = flowTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Date getGrowTime() {
        return growTime;
    }

    public void setGrowTime(Date growTime) {
        this.growTime = growTime;
    }

    public String getOrLocation() {
        return orLocation;
    }

    public void setOrLocation(String orLocation) {
        this.orLocation = orLocation == null ? null : orLocation.trim();
    }

    public Byte getInflowStatus() {
        return inflowStatus;
    }

    public void setInflowStatus(Byte inflowStatus) {
        this.inflowStatus = inflowStatus;
    }

    public Boolean getIsBack() {
        return isBack;
    }

    public void setIsBack(Boolean isBack) {
        this.isBack = isBack;
    }

    public String getOutflowUnit() {
        return outflowUnit;
    }

    public void setOutflowUnit(String outflowUnit) {
        this.outflowUnit = outflowUnit == null ? null : outflowUnit.trim();
    }

    public Integer getOutflowLocation() {
        return outflowLocation;
    }

    public void setOutflowLocation(Integer outflowLocation) {
        this.outflowLocation = outflowLocation;
    }

    public Date getOutflowTime() {
        return outflowTime;
    }

    public void setOutflowTime(Date outflowTime) {
        this.outflowTime = outflowTime;
    }

    public Byte getOutflowStatus() {
        return outflowStatus;
    }

    public void setOutflowStatus(Byte outflowStatus) {
        this.outflowStatus = outflowStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}