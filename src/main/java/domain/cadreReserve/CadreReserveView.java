package domain.cadreReserve;

import domain.cadre.CadreAdminLevel;
import domain.cadre.CadrePost;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadre;
import domain.sys.SysUserView;
import domain.unit.Unit;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class CadreReserveView implements Serializable {
    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }
    public Unit getUnit(){
        return CmTag.getUnit(unitId);
    }
    // 主职
    public CadrePost getMainCadrePost(){
        return CmTag.getCadreMainCadrePostById(mainCadrePostId);
    }
    // 现任职务
    public CadreAdminLevel getPresentAdminLevel() {
        return CmTag.getPresentByCadreId(id);
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
    private Integer reserveId;

    private Integer reserveType;

    private Byte reserveStatus;

    private String reserveRemark;

    private Integer reserveSortOrder;

    private String username;

    private Integer id;

    private Integer userId;

    private Integer adminLevel;

    private Integer postType;

    private Integer unitId;

    private Byte type;

    private Boolean state;

    private String title;

    private Integer dispatchCadreId;

    private String post;

    private String remark;

    private Integer sortOrder;

    private Byte status;

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

    private Integer mainCadrePostId;

    private Boolean isDouble;

    private String doubleUnitIds;

    private Boolean isPrincipalPost;

    private Long cadrePostYear;

    private Long adminLevelYear;

    private Integer npRelateId;

    private Integer npId;

    private String npFileName;

    private String npFile;

    private Date npWorkTime;

    private Integer lpRelateId;

    private Integer lpId;

    private String lpFileName;

    private String lpFile;

    private Date lpWorkTime;

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

    private String unitTypeAttr;

    private Date verifyBirth;

    private Date verifyWorkTime;

    private String talentTitle;

    private static final long serialVersionUID = 1L;

    public Integer getReserveId() {
        return reserveId;
    }

    public void setReserveId(Integer reserveId) {
        this.reserveId = reserveId;
    }

    public Integer getReserveType() {
        return reserveType;
    }

    public void setReserveType(Integer reserveType) {
        this.reserveType = reserveType;
    }

    public Byte getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(Byte reserveStatus) {
        this.reserveStatus = reserveStatus;
    }

    public String getReserveRemark() {
        return reserveRemark;
    }

    public void setReserveRemark(String reserveRemark) {
        this.reserveRemark = reserveRemark == null ? null : reserveRemark.trim();
    }

    public Integer getReserveSortOrder() {
        return reserveSortOrder;
    }

    public void setReserveSortOrder(Integer reserveSortOrder) {
        this.reserveSortOrder = reserveSortOrder;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
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

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
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

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
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

    public Integer getMainCadrePostId() {
        return mainCadrePostId;
    }

    public void setMainCadrePostId(Integer mainCadrePostId) {
        this.mainCadrePostId = mainCadrePostId;
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

    public Boolean getIsPrincipalPost() {
        return isPrincipalPost;
    }

    public void setIsPrincipalPost(Boolean isPrincipalPost) {
        this.isPrincipalPost = isPrincipalPost;
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

    public Integer getNpRelateId() {
        return npRelateId;
    }

    public void setNpRelateId(Integer npRelateId) {
        this.npRelateId = npRelateId;
    }

    public Integer getNpId() {
        return npId;
    }

    public void setNpId(Integer npId) {
        this.npId = npId;
    }

    public String getNpFileName() {
        return npFileName;
    }

    public void setNpFileName(String npFileName) {
        this.npFileName = npFileName == null ? null : npFileName.trim();
    }

    public String getNpFile() {
        return npFile;
    }

    public void setNpFile(String npFile) {
        this.npFile = npFile == null ? null : npFile.trim();
    }

    public Date getNpWorkTime() {
        return npWorkTime;
    }

    public void setNpWorkTime(Date npWorkTime) {
        this.npWorkTime = npWorkTime;
    }

    public Integer getLpRelateId() {
        return lpRelateId;
    }

    public void setLpRelateId(Integer lpRelateId) {
        this.lpRelateId = lpRelateId;
    }

    public Integer getLpId() {
        return lpId;
    }

    public void setLpId(Integer lpId) {
        this.lpId = lpId;
    }

    public String getLpFileName() {
        return lpFileName;
    }

    public void setLpFileName(String lpFileName) {
        this.lpFileName = lpFileName == null ? null : lpFileName.trim();
    }

    public String getLpFile() {
        return lpFile;
    }

    public void setLpFile(String lpFile) {
        this.lpFile = lpFile == null ? null : lpFile.trim();
    }

    public Date getLpWorkTime() {
        return lpWorkTime;
    }

    public void setLpWorkTime(Date lpWorkTime) {
        this.lpWorkTime = lpWorkTime;
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

    public String getUnitTypeAttr() {
        return unitTypeAttr;
    }

    public void setUnitTypeAttr(String unitTypeAttr) {
        this.unitTypeAttr = unitTypeAttr == null ? null : unitTypeAttr.trim();
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

    public String getTalentTitle() {
        return talentTitle;
    }

    public void setTalentTitle(String talentTitle) {
        this.talentTitle = talentTitle == null ? null : talentTitle.trim();
    }
}