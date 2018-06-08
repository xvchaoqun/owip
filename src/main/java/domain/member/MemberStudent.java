package domain.member;

import domain.party.Party;
import sys.helper.PartyHelper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class MemberStudent implements Serializable {
    public String getParty(){
        Party party = PartyHelper.getParty(partyId);
        return party!=null?party.getName():null;
    }
    private Date createTime;

    private Date applyTime;

    private Byte memberSource;

    private Byte source;

    private Date positiveTime;

    private Date activeTime;

    private Byte politicalStatus;

    private Date transferTime;

    private Integer userId;

    private Integer branchId;

    private Date candidateTime;

    private Integer partyId;

    private Date growTime;

    private Byte status;

    private String partyPost;

    private String partyReward;

    private String otherReward;

    private Float delayYear;

    private String period;

    private String code;

    private String eduCategory;

    private Byte gender;

    private Date birth;

    private String nation;

    private Date actualGraduateTime;

    private Date expectGraduateTime;

    private Date actualEnrolTime;

    private Byte syncSource;

    private String type;

    private Boolean isFullTime;

    private String realname;

    private String enrolYear;

    private String nativePlace;

    private String eduWay;

    private String idcard;

    private String eduLevel;

    private String grade;

    private String eduType;

    private String xjStatus;

    private Integer unitId;

    private Byte outStatus;

    private Date outHandleTime;

    private static final long serialVersionUID = 1L;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Byte getMemberSource() {
        return memberSource;
    }

    public void setMemberSource(Byte memberSource) {
        this.memberSource = memberSource;
    }

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }

    public Date getPositiveTime() {
        return positiveTime;
    }

    public void setPositiveTime(Date positiveTime) {
        this.positiveTime = positiveTime;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Byte getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(Byte politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Date getCandidateTime() {
        return candidateTime;
    }

    public void setCandidateTime(Date candidateTime) {
        this.candidateTime = candidateTime;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Date getGrowTime() {
        return growTime;
    }

    public void setGrowTime(Date growTime) {
        this.growTime = growTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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

    public Float getDelayYear() {
        return delayYear;
    }

    public void setDelayYear(Float delayYear) {
        this.delayYear = delayYear;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getEduCategory() {
        return eduCategory;
    }

    public void setEduCategory(String eduCategory) {
        this.eduCategory = eduCategory == null ? null : eduCategory.trim();
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public Date getActualGraduateTime() {
        return actualGraduateTime;
    }

    public void setActualGraduateTime(Date actualGraduateTime) {
        this.actualGraduateTime = actualGraduateTime;
    }

    public Date getExpectGraduateTime() {
        return expectGraduateTime;
    }

    public void setExpectGraduateTime(Date expectGraduateTime) {
        this.expectGraduateTime = expectGraduateTime;
    }

    public Date getActualEnrolTime() {
        return actualEnrolTime;
    }

    public void setActualEnrolTime(Date actualEnrolTime) {
        this.actualEnrolTime = actualEnrolTime;
    }

    public Byte getSyncSource() {
        return syncSource;
    }

    public void setSyncSource(Byte syncSource) {
        this.syncSource = syncSource;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Boolean getIsFullTime() {
        return isFullTime;
    }

    public void setIsFullTime(Boolean isFullTime) {
        this.isFullTime = isFullTime;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getEnrolYear() {
        return enrolYear;
    }

    public void setEnrolYear(String enrolYear) {
        this.enrolYear = enrolYear == null ? null : enrolYear.trim();
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace == null ? null : nativePlace.trim();
    }

    public String getEduWay() {
        return eduWay;
    }

    public void setEduWay(String eduWay) {
        this.eduWay = eduWay == null ? null : eduWay.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public String getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(String eduLevel) {
        this.eduLevel = eduLevel == null ? null : eduLevel.trim();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public String getEduType() {
        return eduType;
    }

    public void setEduType(String eduType) {
        this.eduType = eduType == null ? null : eduType.trim();
    }

    public String getXjStatus() {
        return xjStatus;
    }

    public void setXjStatus(String xjStatus) {
        this.xjStatus = xjStatus == null ? null : xjStatus.trim();
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
}