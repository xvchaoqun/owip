package domain.cadreInspect;

import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadre;
import domain.sc.scRecord.ScRecordView;
import domain.sys.SysUserView;
import domain.unit.Unit;
import domain.unit.UnitPost;
import persistence.sc.IScMapper;
import persistence.unit.UnitPostMapper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class CadreInspectView implements Serializable {

    public SysUserView getRecordUser(){ return CmTag.getUserById(recordUserId);}

    public UnitPost getAssignUnitPost(){
        if(assignUnitPostId==null) return null;
        return CmTag.getBean(UnitPostMapper.class).selectByPrimaryKey(assignUnitPostId);
    }

    public ScRecordView getScRecord(){

        if(recordId==null) return null;
        IScMapper iScMapper = CmTag.getBean(IScMapper.class);
        if(iScMapper==null) return null;
        return iScMapper.getScRecordView(recordId);
    }

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }
    public Unit getUnit(){
        return CmTag.getUnit(unitId);
    }

    // 离任文件
    public Dispatch getDispatch(){
        if(dispatchCadreId!=null){
            DispatchCadre dispatchCadre = CmTag.getDispatchCadre(dispatchCadreId);
            if(dispatchCadre!=null)
                return CmTag.getDispatch(dispatchCadre.getDispatchId());
        }
        return null;
    }
    private Integer inspectId;

    private Integer recordId;

    private Integer assignUnitPostId;

    private Byte inspectType;

    private Byte inspectStatus;

    private String inspectRemark;

    private Integer recordUserId;

    private Integer inspectSortOrder;

    private Integer id;

    private Integer userId;

    private Boolean isDep;

    private Boolean hasCrp;

    private Boolean isDouble;

    private String doubleUnitIds;

    private Integer state;

    private String title;

    private Boolean isOutside;

    private Integer dispatchCadreId;

    private String label;

    private String profile;

    private String remark;

    private String originalPost;

    private Date appointDate;

    private Date deposeDate;

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

    private Date owPositiveTime;

    private String owRemark;

    private Integer eduId;

    private Date finishTime;

    private Integer learnStyle;

    private String school;

    private String dep;

    private Byte schoolType;

    private String major;

    private Byte degreeType;

    private String degree;

    private String authorizedType;

    private String staffType;

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

    private String staffStatus;

    private String isTemp;

    private Date workTime;

    private Date workStartTime;

    private String talentTitle;

    private Integer mainCadrePostId;

    private Integer unitPostId;

    private Boolean isPrincipal;

    private Integer lpDispatchId;

    private Date lpWorkTime;

    private Integer npDispatchId;

    private Date npWorkTime;

    private Byte leaderType;

    private Integer cadrePostYear;

    private Integer adminLevelYear;

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

    public Integer getInspectId() {
        return inspectId;
    }

    public void setInspectId(Integer inspectId) {
        this.inspectId = inspectId;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getAssignUnitPostId() {
        return assignUnitPostId;
    }

    public void setAssignUnitPostId(Integer assignUnitPostId) {
        this.assignUnitPostId = assignUnitPostId;
    }

    public Byte getInspectType() {
        return inspectType;
    }

    public void setInspectType(Byte inspectType) {
        this.inspectType = inspectType;
    }

    public Byte getInspectStatus() {
        return inspectStatus;
    }

    public void setInspectStatus(Byte inspectStatus) {
        this.inspectStatus = inspectStatus;
    }

    public String getInspectRemark() {
        return inspectRemark;
    }

    public void setInspectRemark(String inspectRemark) {
        this.inspectRemark = inspectRemark == null ? null : inspectRemark.trim();
    }

    public Integer getRecordUserId() {
        return recordUserId;
    }

    public void setRecordUserId(Integer recordUserId) {
        this.recordUserId = recordUserId;
    }

    public Integer getInspectSortOrder() {
        return inspectSortOrder;
    }

    public void setInspectSortOrder(Integer inspectSortOrder) {
        this.inspectSortOrder = inspectSortOrder;
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

    public Boolean getIsOutside() {
        return isOutside;
    }

    public void setIsOutside(Boolean isOutside) {
        this.isOutside = isOutside;
    }

    public Integer getDispatchCadreId() {
        return dispatchCadreId;
    }

    public void setDispatchCadreId(Integer dispatchCadreId) {
        this.dispatchCadreId = dispatchCadreId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile == null ? null : profile.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOriginalPost() {
        return originalPost;
    }

    public void setOriginalPost(String originalPost) {
        this.originalPost = originalPost == null ? null : originalPost.trim();
    }

    public Date getAppointDate() {
        return appointDate;
    }

    public void setAppointDate(Date appointDate) {
        this.appointDate = appointDate;
    }

    public Date getDeposeDate() {
        return deposeDate;
    }

    public void setDeposeDate(Date deposeDate) {
        this.deposeDate = deposeDate;
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

    public Date getOwPositiveTime() {
        return owPositiveTime;
    }

    public void setOwPositiveTime(Date owPositiveTime) {
        this.owPositiveTime = owPositiveTime;
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

    public Byte getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(Byte degreeType) {
        this.degreeType = degreeType;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree == null ? null : degree.trim();
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

    public String getStaffStatus() {
        return staffStatus;
    }

    public void setStaffStatus(String staffStatus) {
        this.staffStatus = staffStatus == null ? null : staffStatus.trim();
    }

    public String getIsTemp() {
        return isTemp;
    }

    public void setIsTemp(String isTemp) {
        this.isTemp = isTemp == null ? null : isTemp.trim();
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

    public Integer getUnitPostId() {
        return unitPostId;
    }

    public void setUnitPostId(Integer unitPostId) {
        this.unitPostId = unitPostId;
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

    public Integer getCadrePostYear() {
        return cadrePostYear;
    }

    public void setCadrePostYear(Integer cadrePostYear) {
        this.cadrePostYear = cadrePostYear;
    }

    public Integer getAdminLevelYear() {
        return adminLevelYear;
    }

    public void setAdminLevelYear(Integer adminLevelYear) {
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