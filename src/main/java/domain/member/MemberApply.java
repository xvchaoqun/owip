package domain.member;

import domain.sys.SysUserView;
import sys.helper.PartyHelper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class MemberApply implements Serializable {
    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }
    public String getApplyStatus(){
        return PartyHelper.getApplyStatus(this);
    }
    private Integer userId;

    private Integer partyId;

    private Integer branchId;

    private Byte type;

    private Date applyTime;

    private Date fillTime;

    private String remark;

    private Byte stage;

    private String reason;

    private Date passTime;

    private Date activeTime;

    private Date activeTrainStartTime;

    private Date activeTrainEndTime;

    private String activeGrade;

    private Date candidateTime;

    private Date candidateTrainStartTime;

    private Date candidateTrainEndTime;

    private String candidateGrade;

    private Byte candidateStatus;

    private Date planTime;

    private Byte planStatus;

    private Date drawTime;

    private Byte drawStatus;

    private Integer applySnId;

    private String applySn;

    private Integer growPublicId;

    private Date growTime;

    private Byte growStatus;

    private Integer positivePublicId;

    private Date positiveTime;

    private Byte positiveStatus;

    private Boolean isRemove;

    private Date createTime;

    private static final long serialVersionUID = 1L;

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

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getFillTime() {
        return fillTime;
    }

    public void setFillTime(Date fillTime) {
        this.fillTime = fillTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getStage() {
        return stage;
    }

    public void setStage(Byte stage) {
        this.stage = stage;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Date getPassTime() {
        return passTime;
    }

    public void setPassTime(Date passTime) {
        this.passTime = passTime;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Date getActiveTrainStartTime() {
        return activeTrainStartTime;
    }

    public void setActiveTrainStartTime(Date activeTrainStartTime) {
        this.activeTrainStartTime = activeTrainStartTime;
    }

    public Date getActiveTrainEndTime() {
        return activeTrainEndTime;
    }

    public void setActiveTrainEndTime(Date activeTrainEndTime) {
        this.activeTrainEndTime = activeTrainEndTime;
    }

    public String getActiveGrade() {
        return activeGrade;
    }

    public void setActiveGrade(String activeGrade) {
        this.activeGrade = activeGrade == null ? null : activeGrade.trim();
    }

    public Date getCandidateTime() {
        return candidateTime;
    }

    public void setCandidateTime(Date candidateTime) {
        this.candidateTime = candidateTime;
    }

    public Date getCandidateTrainStartTime() {
        return candidateTrainStartTime;
    }

    public void setCandidateTrainStartTime(Date candidateTrainStartTime) {
        this.candidateTrainStartTime = candidateTrainStartTime;
    }

    public Date getCandidateTrainEndTime() {
        return candidateTrainEndTime;
    }

    public void setCandidateTrainEndTime(Date candidateTrainEndTime) {
        this.candidateTrainEndTime = candidateTrainEndTime;
    }

    public String getCandidateGrade() {
        return candidateGrade;
    }

    public void setCandidateGrade(String candidateGrade) {
        this.candidateGrade = candidateGrade == null ? null : candidateGrade.trim();
    }

    public Byte getCandidateStatus() {
        return candidateStatus;
    }

    public void setCandidateStatus(Byte candidateStatus) {
        this.candidateStatus = candidateStatus;
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public Byte getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(Byte planStatus) {
        this.planStatus = planStatus;
    }

    public Date getDrawTime() {
        return drawTime;
    }

    public void setDrawTime(Date drawTime) {
        this.drawTime = drawTime;
    }

    public Byte getDrawStatus() {
        return drawStatus;
    }

    public void setDrawStatus(Byte drawStatus) {
        this.drawStatus = drawStatus;
    }

    public Integer getApplySnId() {
        return applySnId;
    }

    public void setApplySnId(Integer applySnId) {
        this.applySnId = applySnId;
    }

    public String getApplySn() {
        return applySn;
    }

    public void setApplySn(String applySn) {
        this.applySn = applySn == null ? null : applySn.trim();
    }

    public Integer getGrowPublicId() {
        return growPublicId;
    }

    public void setGrowPublicId(Integer growPublicId) {
        this.growPublicId = growPublicId;
    }

    public Date getGrowTime() {
        return growTime;
    }

    public void setGrowTime(Date growTime) {
        this.growTime = growTime;
    }

    public Byte getGrowStatus() {
        return growStatus;
    }

    public void setGrowStatus(Byte growStatus) {
        this.growStatus = growStatus;
    }

    public Integer getPositivePublicId() {
        return positivePublicId;
    }

    public void setPositivePublicId(Integer positivePublicId) {
        this.positivePublicId = positivePublicId;
    }

    public Date getPositiveTime() {
        return positiveTime;
    }

    public void setPositiveTime(Date positiveTime) {
        this.positiveTime = positiveTime;
    }

    public Byte getPositiveStatus() {
        return positiveStatus;
    }

    public void setPositiveStatus(Byte positiveStatus) {
        this.positiveStatus = positiveStatus;
    }

    public Boolean getIsRemove() {
        return isRemove;
    }

    public void setIsRemove(Boolean isRemove) {
        this.isRemove = isRemove;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}