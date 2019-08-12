package domain.crs;

import java.io.Serializable;
import java.util.Date;

public class CrsCandidateView implements Serializable {
    private Integer candidateId;

    private Boolean isFirst;

    private Long expertCount;

    private Integer crsPostId;

    private Byte crsPostType;

    private Integer crsPostYear;

    private Integer crsPostSeq;

    private String crsPostName;

    private String crsPostJob;

    private Byte crsPostStatus;

    private Integer applicantId;

    private String recommendOw;

    private String recommendCadre;

    private String recommendCrowd;

    private String recommendPdf;

    private Integer recommendFirstCount;

    private Integer recommendSecondCount;

    private Boolean isRecommend;

    private String pptName;

    private String ppt;

    private Integer id;

    private Integer userId;

    private Byte type;

    private Boolean isDep;

    private Boolean hasCrp;

    private Boolean isDouble;

    private String doubleUnitIds;

    private Integer state;

    private String title;

    private Integer dispatchCadreId;

    private String remark;

    private Integer sortOrder;

    private Byte status;

    private Integer unitId;

    private Integer adminLevel;

    private Integer postType;

    private String post;

    private Boolean isCommitteeMember;

    private String msgTitle;

    private String mobile;

    private String phone;

    private String homePhone;

    private String email;

    private String code;

    private String realname;

    private Byte gender;

    private String nation;

    private String nativePlace;

    private String idcard;

    private Date birth;

    private Integer partyId;

    private Integer branchId;

    private Byte memberStatus;

    private Integer dpId;

    private Integer dpTypeId;

    private String dpPost;

    private Date dpGrowTime;

    private String dpRemark;

    private Integer owId;

    private Boolean isOw;

    private Date owGrowTime;

    private String owRemark;

    private Integer eduId;

    private Date finishTime;

    private Integer learnStyle;

    private String school;

    private String dep;

    private Byte schoolType;

    private String major;

    private String degree;

    private String postClass;

    private String subPostClass;

    private String mainPostLevel;

    private Date proPostTime;

    private String proPostLevel;

    private Date proPostLevelTime;

    private String proPost;

    private String manageLevel;

    private Date manageLevelTime;

    private Date arriveTime;

    private Date workTime;

    private Date workStartTime;

    private String talentTitle;

    private Integer mainCadrePostId;

    private Boolean isPrincipal;

    private Integer lpDispatchId;

    private Date lpWorkTime;

    private Integer npDispatchId;

    private Date npWorkTime;

    private Byte leaderType;

    private Long cadrePostYear;

    private Long adminLevelYear;

    private Integer sDispatchId;

    private Date sWorkTime;

    private Integer eDispatchId;

    private Date eWorkTime;

    private String adminLevelCode;

    private String adminLevelName;

    private String maxCeEduAttr;

    private String maxCeEduCode;

    private String maxCeEduName;

    private String unitName;

    private Integer unitTypeId;

    private String unitTypeCode;

    private String unitTypeName;

    private String unitTypeGroup;

    private Date verifyBirth;

    private Date verifyWorkTime;

    private static final long serialVersionUID = 1L;

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Boolean getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Boolean isFirst) {
        this.isFirst = isFirst;
    }

    public Long getExpertCount() {
        return expertCount;
    }

    public void setExpertCount(Long expertCount) {
        this.expertCount = expertCount;
    }

    public Integer getCrsPostId() {
        return crsPostId;
    }

    public void setCrsPostId(Integer crsPostId) {
        this.crsPostId = crsPostId;
    }

    public Byte getCrsPostType() {
        return crsPostType;
    }

    public void setCrsPostType(Byte crsPostType) {
        this.crsPostType = crsPostType;
    }

    public Integer getCrsPostYear() {
        return crsPostYear;
    }

    public void setCrsPostYear(Integer crsPostYear) {
        this.crsPostYear = crsPostYear;
    }

    public Integer getCrsPostSeq() {
        return crsPostSeq;
    }

    public void setCrsPostSeq(Integer crsPostSeq) {
        this.crsPostSeq = crsPostSeq;
    }

    public String getCrsPostName() {
        return crsPostName;
    }

    public void setCrsPostName(String crsPostName) {
        this.crsPostName = crsPostName == null ? null : crsPostName.trim();
    }

    public String getCrsPostJob() {
        return crsPostJob;
    }

    public void setCrsPostJob(String crsPostJob) {
        this.crsPostJob = crsPostJob == null ? null : crsPostJob.trim();
    }

    public Byte getCrsPostStatus() {
        return crsPostStatus;
    }

    public void setCrsPostStatus(Byte crsPostStatus) {
        this.crsPostStatus = crsPostStatus;
    }

    public Integer getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Integer applicantId) {
        this.applicantId = applicantId;
    }

    public String getRecommendOw() {
        return recommendOw;
    }

    public void setRecommendOw(String recommendOw) {
        this.recommendOw = recommendOw == null ? null : recommendOw.trim();
    }

    public String getRecommendCadre() {
        return recommendCadre;
    }

    public void setRecommendCadre(String recommendCadre) {
        this.recommendCadre = recommendCadre == null ? null : recommendCadre.trim();
    }

    public String getRecommendCrowd() {
        return recommendCrowd;
    }

    public void setRecommendCrowd(String recommendCrowd) {
        this.recommendCrowd = recommendCrowd == null ? null : recommendCrowd.trim();
    }

    public String getRecommendPdf() {
        return recommendPdf;
    }

    public void setRecommendPdf(String recommendPdf) {
        this.recommendPdf = recommendPdf == null ? null : recommendPdf.trim();
    }

    public Integer getRecommendFirstCount() {
        return recommendFirstCount;
    }

    public void setRecommendFirstCount(Integer recommendFirstCount) {
        this.recommendFirstCount = recommendFirstCount;
    }

    public Integer getRecommendSecondCount() {
        return recommendSecondCount;
    }

    public void setRecommendSecondCount(Integer recommendSecondCount) {
        this.recommendSecondCount = recommendSecondCount;
    }

    public Boolean getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Boolean isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getPptName() {
        return pptName;
    }

    public void setPptName(String pptName) {
        this.pptName = pptName == null ? null : pptName.trim();
    }

    public String getPpt() {
        return ppt;
    }

    public void setPpt(String ppt) {
        this.ppt = ppt == null ? null : ppt.trim();
    }

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

    public Boolean getIsDep() {
        return isDep;
    }

    public void setIsDep(Boolean isDep) {
        this.isDep = isDep;
    }

    public Boolean getHasCrp() {
        return hasCrp;
    }

    public void setHasCrp(Boolean hasCrp) {
        this.hasCrp = hasCrp;
    }

    public Boolean getIsDouble() {
        return isDouble;
    }

    public void setIsDouble(Boolean isDouble) {
        this.isDouble = isDouble;
    }

    public String getDoubleUnitIds() {
        return doubleUnitIds;
    }

    public void setDoubleUnitIds(String doubleUnitIds) {
        this.doubleUnitIds = doubleUnitIds == null ? null : doubleUnitIds.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getDispatchCadreId() {
        return dispatchCadreId;
    }

    public void setDispatchCadreId(Integer dispatchCadreId) {
        this.dispatchCadreId = dispatchCadreId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(Integer adminLevel) {
        this.adminLevel = adminLevel;
    }

    public Integer getPostType() {
        return postType;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public Boolean getIsCommitteeMember() {
        return isCommitteeMember;
    }

    public void setIsCommitteeMember(Boolean isCommitteeMember) {
        this.isCommitteeMember = isCommitteeMember;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle == null ? null : msgTitle.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone == null ? null : homePhone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
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

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace == null ? null : nativePlace.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
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

    public Byte getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(Byte memberStatus) {
        this.memberStatus = memberStatus;
    }

    public Integer getDpId() {
        return dpId;
    }

    public void setDpId(Integer dpId) {
        this.dpId = dpId;
    }

    public Integer getDpTypeId() {
        return dpTypeId;
    }

    public void setDpTypeId(Integer dpTypeId) {
        this.dpTypeId = dpTypeId;
    }

    public String getDpPost() {
        return dpPost;
    }

    public void setDpPost(String dpPost) {
        this.dpPost = dpPost == null ? null : dpPost.trim();
    }

    public Date getDpGrowTime() {
        return dpGrowTime;
    }

    public void setDpGrowTime(Date dpGrowTime) {
        this.dpGrowTime = dpGrowTime;
    }

    public String getDpRemark() {
        return dpRemark;
    }

    public void setDpRemark(String dpRemark) {
        this.dpRemark = dpRemark == null ? null : dpRemark.trim();
    }

    public Integer getOwId() {
        return owId;
    }

    public void setOwId(Integer owId) {
        this.owId = owId;
    }

    public Boolean getIsOw() {
        return isOw;
    }

    public void setIsOw(Boolean isOw) {
        this.isOw = isOw;
    }

    public Date getOwGrowTime() {
        return owGrowTime;
    }

    public void setOwGrowTime(Date owGrowTime) {
        this.owGrowTime = owGrowTime;
    }

    public String getOwRemark() {
        return owRemark;
    }

    public void setOwRemark(String owRemark) {
        this.owRemark = owRemark == null ? null : owRemark.trim();
    }

    public Integer getEduId() {
        return eduId;
    }

    public void setEduId(Integer eduId) {
        this.eduId = eduId;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getLearnStyle() {
        return learnStyle;
    }

    public void setLearnStyle(Integer learnStyle) {
        this.learnStyle = learnStyle;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep == null ? null : dep.trim();
    }

    public Byte getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(Byte schoolType) {
        this.schoolType = schoolType;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree == null ? null : degree.trim();
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

    public String getProPost() {
        return proPost;
    }

    public void setProPost(String proPost) {
        this.proPost = proPost == null ? null : proPost.trim();
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

    public String getTalentTitle() {
        return talentTitle;
    }

    public void setTalentTitle(String talentTitle) {
        this.talentTitle = talentTitle == null ? null : talentTitle.trim();
    }

    public Integer getMainCadrePostId() {
        return mainCadrePostId;
    }

    public void setMainCadrePostId(Integer mainCadrePostId) {
        this.mainCadrePostId = mainCadrePostId;
    }

    public Boolean getIsPrincipal() {
        return isPrincipal;
    }

    public void setIsPrincipal(Boolean isPrincipal) {
        this.isPrincipal = isPrincipal;
    }

    public Integer getLpDispatchId() {
        return lpDispatchId;
    }

    public void setLpDispatchId(Integer lpDispatchId) {
        this.lpDispatchId = lpDispatchId;
    }

    public Date getLpWorkTime() {
        return lpWorkTime;
    }

    public void setLpWorkTime(Date lpWorkTime) {
        this.lpWorkTime = lpWorkTime;
    }

    public Integer getNpDispatchId() {
        return npDispatchId;
    }

    public void setNpDispatchId(Integer npDispatchId) {
        this.npDispatchId = npDispatchId;
    }

    public Date getNpWorkTime() {
        return npWorkTime;
    }

    public void setNpWorkTime(Date npWorkTime) {
        this.npWorkTime = npWorkTime;
    }

    public Byte getLeaderType() {
        return leaderType;
    }

    public void setLeaderType(Byte leaderType) {
        this.leaderType = leaderType;
    }

    public Long getCadrePostYear() {
        return cadrePostYear;
    }

    public void setCadrePostYear(Long cadrePostYear) {
        this.cadrePostYear = cadrePostYear;
    }

    public Long getAdminLevelYear() {
        return adminLevelYear;
    }

    public void setAdminLevelYear(Long adminLevelYear) {
        this.adminLevelYear = adminLevelYear;
    }

    public Integer getsDispatchId() {
        return sDispatchId;
    }

    public void setsDispatchId(Integer sDispatchId) {
        this.sDispatchId = sDispatchId;
    }

    public Date getsWorkTime() {
        return sWorkTime;
    }

    public void setsWorkTime(Date sWorkTime) {
        this.sWorkTime = sWorkTime;
    }

    public Integer geteDispatchId() {
        return eDispatchId;
    }

    public void seteDispatchId(Integer eDispatchId) {
        this.eDispatchId = eDispatchId;
    }

    public Date geteWorkTime() {
        return eWorkTime;
    }

    public void seteWorkTime(Date eWorkTime) {
        this.eWorkTime = eWorkTime;
    }

    public String getAdminLevelCode() {
        return adminLevelCode;
    }

    public void setAdminLevelCode(String adminLevelCode) {
        this.adminLevelCode = adminLevelCode == null ? null : adminLevelCode.trim();
    }

    public String getAdminLevelName() {
        return adminLevelName;
    }

    public void setAdminLevelName(String adminLevelName) {
        this.adminLevelName = adminLevelName == null ? null : adminLevelName.trim();
    }

    public String getMaxCeEduAttr() {
        return maxCeEduAttr;
    }

    public void setMaxCeEduAttr(String maxCeEduAttr) {
        this.maxCeEduAttr = maxCeEduAttr == null ? null : maxCeEduAttr.trim();
    }

    public String getMaxCeEduCode() {
        return maxCeEduCode;
    }

    public void setMaxCeEduCode(String maxCeEduCode) {
        this.maxCeEduCode = maxCeEduCode == null ? null : maxCeEduCode.trim();
    }

    public String getMaxCeEduName() {
        return maxCeEduName;
    }

    public void setMaxCeEduName(String maxCeEduName) {
        this.maxCeEduName = maxCeEduName == null ? null : maxCeEduName.trim();
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public Integer getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(Integer unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public String getUnitTypeCode() {
        return unitTypeCode;
    }

    public void setUnitTypeCode(String unitTypeCode) {
        this.unitTypeCode = unitTypeCode == null ? null : unitTypeCode.trim();
    }

    public String getUnitTypeName() {
        return unitTypeName;
    }

    public void setUnitTypeName(String unitTypeName) {
        this.unitTypeName = unitTypeName == null ? null : unitTypeName.trim();
    }

    public String getUnitTypeGroup() {
        return unitTypeGroup;
    }

    public void setUnitTypeGroup(String unitTypeGroup) {
        this.unitTypeGroup = unitTypeGroup == null ? null : unitTypeGroup.trim();
    }

    public Date getVerifyBirth() {
        return verifyBirth;
    }

    public void setVerifyBirth(Date verifyBirth) {
        this.verifyBirth = verifyBirth;
    }

    public Date getVerifyWorkTime() {
        return verifyWorkTime;
    }

    public void setVerifyWorkTime(Date verifyWorkTime) {
        this.verifyWorkTime = verifyWorkTime;
    }
}