package domain.member;

import domain.sys.SysUser;
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

    private String flowReason;

    private Date growTime;

    private String orLocation;

    private Byte inflowStatus;

    private Boolean isBack;

    private String reason;

    private String outUnit;

    private Integer outLocation;

    private Date outTime;

    private Byte outStatus;

    private Boolean outIsBack;

    private String outReason;

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

    public String getFlowReason() {
        return flowReason;
    }

    public void setFlowReason(String flowReason) {
        this.flowReason = flowReason == null ? null : flowReason.trim();
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getOutUnit() {
        return outUnit;
    }

    public void setOutUnit(String outUnit) {
        this.outUnit = outUnit == null ? null : outUnit.trim();
    }

    public Integer getOutLocation() {
        return outLocation;
    }

    public void setOutLocation(Integer outLocation) {
        this.outLocation = outLocation;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public Byte getOutStatus() {
        return outStatus;
    }

    public void setOutStatus(Byte outStatus) {
        this.outStatus = outStatus;
    }

    public Boolean getOutIsBack() {
        return outIsBack;
    }

    public void setOutIsBack(Boolean outIsBack) {
        this.outIsBack = outIsBack;
    }

    public String getOutReason() {
        return outReason;
    }

    public void setOutReason(String outReason) {
        this.outReason = outReason == null ? null : outReason.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}