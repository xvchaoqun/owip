package domain.dp;

import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import service.dp.dpCommon.DpCommon;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class DpMemberView implements Serializable {
    public SysUserView getUser(){

        return CmTag.getUserById(userId);
    }
    public TeacherInfo getTeacherInfo(){

        return DpCommon.getTeacherById(userId);
    }
    private Integer userId;

    private Integer partyId;

    private Byte politicalStatus;

    private Byte type;

    private Byte status;

    private Byte source;

    private Integer addType;

    private Date transferTime;

    private Date applyTime;

    private Date activeTime;

    private Date candidateTime;

    private String sponsor;

    private Date growTime;

    private Date positiveTime;

    private Date createTime;

    private Date updateTime;

    private String partyPost;

    private String partyReward;

    private String otherReward;

    private Integer unitId;

    private Byte outStatus;

    private Date outHandleTime;

    private String education;

    private String authorizedType;

    private String proPost;

    private Boolean isRetire;

    private Date retireTime;

    private Boolean isHonorRetire;

    private String eduLevel;

    private String eduType;

    private Date actualEnrolTime;

    private Boolean isFullTime;

    private Date expectGraduateTime;

    private Float delayYear;

    private Date actualGraduateTime;

    private Byte syncSource;

    private String grade;

    private String studentType;

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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }

    public Integer getAddType() {
        return addType;
    }

    public void setAddType(Integer addType) {
        this.addType = addType;
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
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

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor == null ? null : sponsor.trim();
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPartyPost() {
        return partyPost;
    }

    public void setPartyPost(String partyPost) {
        this.partyPost = partyPost == null ? null : partyPost.trim();
    }

    public String getPartyReward() {
        return partyReward;
    }

    public void setPartyReward(String partyReward) {
        this.partyReward = partyReward == null ? null : partyReward.trim();
    }

    public String getOtherReward() {
        return otherReward;
    }

    public void setOtherReward(String otherReward) {
        this.otherReward = otherReward == null ? null : otherReward.trim();
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Byte getOutStatus() {
        return outStatus;
    }

    public void setOutStatus(Byte outStatus) {
        this.outStatus = outStatus;
    }

    public Date getOutHandleTime() {
        return outHandleTime;
    }

    public void setOutHandleTime(Date outHandleTime) {
        this.outHandleTime = outHandleTime;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    public String getAuthorizedType() {
        return authorizedType;
    }

    public void setAuthorizedType(String authorizedType) {
        this.authorizedType = authorizedType == null ? null : authorizedType.trim();
    }

    public String getProPost() {
        return proPost;
    }

    public void setProPost(String proPost) {
        this.proPost = proPost == null ? null : proPost.trim();
    }

    public Boolean getIsRetire() {
        return isRetire;
    }

    public void setIsRetire(Boolean isRetire) {
        this.isRetire = isRetire;
    }

    public Date getRetireTime() {
        return retireTime;
    }

    public void setRetireTime(Date retireTime) {
        this.retireTime = retireTime;
    }

    public Boolean getIsHonorRetire() {
        return isHonorRetire;
    }

    public void setIsHonorRetire(Boolean isHonorRetire) {
        this.isHonorRetire = isHonorRetire;
    }

    public String getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(String eduLevel) {
        this.eduLevel = eduLevel == null ? null : eduLevel.trim();
    }

    public String getEduType() {
        return eduType;
    }

    public void setEduType(String eduType) {
        this.eduType = eduType == null ? null : eduType.trim();
    }

    public Date getActualEnrolTime() {
        return actualEnrolTime;
    }

    public void setActualEnrolTime(Date actualEnrolTime) {
        this.actualEnrolTime = actualEnrolTime;
    }

    public Boolean getIsFullTime() {
        return isFullTime;
    }

    public void setIsFullTime(Boolean isFullTime) {
        this.isFullTime = isFullTime;
    }

    public Date getExpectGraduateTime() {
        return expectGraduateTime;
    }

    public void setExpectGraduateTime(Date expectGraduateTime) {
        this.expectGraduateTime = expectGraduateTime;
    }

    public Float getDelayYear() {
        return delayYear;
    }

    public void setDelayYear(Float delayYear) {
        this.delayYear = delayYear;
    }

    public Date getActualGraduateTime() {
        return actualGraduateTime;
    }

    public void setActualGraduateTime(Date actualGraduateTime) {
        this.actualGraduateTime = actualGraduateTime;
    }

    public Byte getSyncSource() {
        return syncSource;
    }

    public void setSyncSource(Byte syncSource) {
        this.syncSource = syncSource;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public String getStudentType() {
        return studentType;
    }

    public void setStudentType(String studentType) {
        this.studentType = studentType == null ? null : studentType.trim();
    }
}