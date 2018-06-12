package domain.member;

import java.io.Serializable;
import java.util.Date;

public class MemberTeacher implements Serializable {
    private Integer userId;

    private String extPhone;

    private String education;

    private String degree;

    private Date degreeTime;

    private String major;

    private String school;

    private String schoolType;

    private String degreeSchool;

    private Date arriveTime;

    private Date workTime;

    private Date workStartTime;

    private String workBreak;

    private Date regularTime;

    private String authorizedType;

    private String staffType;

    private String staffStatus;

    private String postClass;

    private String subPostClass;

    private String mainPostLevel;

    private String fromType;

    private String onJob;

    private String proPost;

    private Date proPostTime;

    private String proPostLevel;

    private Date proPostLevelTime;

    private String titleLevel;

    private String manageLevel;

    private Date manageLevelTime;

    private String officeLevel;

    private String post;

    private String postLevel;

    private String talentType;

    private String talentTitle;

    private String address;

    private String maritalStatus;

    private Boolean isRetire;

    private Date retireTime;

    private Boolean isHonorRetire;

    private Date createTime;

    private Date applyTime;

    private Byte memberSource;

    private Byte source;

    private Date positiveTime;

    private Date activeTime;

    private Byte politicalStatus;

    private Date transferTime;

    private Integer branchId;

    private Date candidateTime;

    private Integer partyId;

    private Date growTime;

    private Byte status;

    private String partyPost;

    private String partyReward;

    private String otherReward;

    private String code;

    private Byte gender;

    private String nation;

    private String email;

    private String mobile;

    private Date birth;

    private String realname;

    private String nativePlace;

    private String phone;

    private String idcard;

    private Integer unitId;

    private Byte outStatus;

    private Date outHandleTime;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getExtPhone() {
        return extPhone;
    }

    public void setExtPhone(String extPhone) {
        this.extPhone = extPhone == null ? null : extPhone.trim();
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree == null ? null : degree.trim();
    }

    public Date getDegreeTime() {
        return degreeTime;
    }

    public void setDegreeTime(Date degreeTime) {
        this.degreeTime = degreeTime;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType == null ? null : schoolType.trim();
    }

    public String getDegreeSchool() {
        return degreeSchool;
    }

    public void setDegreeSchool(String degreeSchool) {
        this.degreeSchool = degreeSchool == null ? null : degreeSchool.trim();
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    public Date getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(Date workStartTime) {
        this.workStartTime = workStartTime;
    }

    public String getWorkBreak() {
        return workBreak;
    }

    public void setWorkBreak(String workBreak) {
        this.workBreak = workBreak == null ? null : workBreak.trim();
    }

    public Date getRegularTime() {
        return regularTime;
    }

    public void setRegularTime(Date regularTime) {
        this.regularTime = regularTime;
    }

    public String getAuthorizedType() {
        return authorizedType;
    }

    public void setAuthorizedType(String authorizedType) {
        this.authorizedType = authorizedType == null ? null : authorizedType.trim();
    }

    public String getStaffType() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType == null ? null : staffType.trim();
    }

    public String getStaffStatus() {
        return staffStatus;
    }

    public void setStaffStatus(String staffStatus) {
        this.staffStatus = staffStatus == null ? null : staffStatus.trim();
    }

    public String getPostClass() {
        return postClass;
    }

    public void setPostClass(String postClass) {
        this.postClass = postClass == null ? null : postClass.trim();
    }

    public String getSubPostClass() {
        return subPostClass;
    }

    public void setSubPostClass(String subPostClass) {
        this.subPostClass = subPostClass == null ? null : subPostClass.trim();
    }

    public String getMainPostLevel() {
        return mainPostLevel;
    }

    public void setMainPostLevel(String mainPostLevel) {
        this.mainPostLevel = mainPostLevel == null ? null : mainPostLevel.trim();
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType == null ? null : fromType.trim();
    }

    public String getOnJob() {
        return onJob;
    }

    public void setOnJob(String onJob) {
        this.onJob = onJob == null ? null : onJob.trim();
    }

    public String getProPost() {
        return proPost;
    }

    public void setProPost(String proPost) {
        this.proPost = proPost == null ? null : proPost.trim();
    }

    public Date getProPostTime() {
        return proPostTime;
    }

    public void setProPostTime(Date proPostTime) {
        this.proPostTime = proPostTime;
    }

    public String getProPostLevel() {
        return proPostLevel;
    }

    public void setProPostLevel(String proPostLevel) {
        this.proPostLevel = proPostLevel == null ? null : proPostLevel.trim();
    }

    public Date getProPostLevelTime() {
        return proPostLevelTime;
    }

    public void setProPostLevelTime(Date proPostLevelTime) {
        this.proPostLevelTime = proPostLevelTime;
    }

    public String getTitleLevel() {
        return titleLevel;
    }

    public void setTitleLevel(String titleLevel) {
        this.titleLevel = titleLevel == null ? null : titleLevel.trim();
    }

    public String getManageLevel() {
        return manageLevel;
    }

    public void setManageLevel(String manageLevel) {
        this.manageLevel = manageLevel == null ? null : manageLevel.trim();
    }

    public Date getManageLevelTime() {
        return manageLevelTime;
    }

    public void setManageLevelTime(Date manageLevelTime) {
        this.manageLevelTime = manageLevelTime;
    }

    public String getOfficeLevel() {
        return officeLevel;
    }

    public void setOfficeLevel(String officeLevel) {
        this.officeLevel = officeLevel == null ? null : officeLevel.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public String getPostLevel() {
        return postLevel;
    }

    public void setPostLevel(String postLevel) {
        this.postLevel = postLevel == null ? null : postLevel.trim();
    }

    public String getTalentType() {
        return talentType;
    }

    public void setTalentType(String talentType) {
        this.talentType = talentType == null ? null : talentType.trim();
    }

    public String getTalentTitle() {
        return talentTitle;
    }

    public void setTalentTitle(String talentTitle) {
        this.talentTitle = talentTitle == null ? null : talentTitle.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus == null ? null : maritalStatus.trim();
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace == null ? null : nativePlace.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
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